package main

import (
	"context"
	"errors"
	"fmt"
	"io"
	"iter"
	"log/slog"
	"math"
	"math/rand"
	"net"
	"os"
	"os/signal"
	"strconv"
	"strings"
	"sync"
	"syscall"
	"time"

	"github.com/segmentio/kafka-go"
	"github.com/sourcegraph/conc"
)

type Printer interface {
	Printf(format string, args ...any)
	Println(args ...any)
}

type stdout struct{}

func (stdout) Printf(format string, args ...any) { fmt.Printf(format, args...) }
func (stdout) Println(args ...any)               { fmt.Println(args...) }

func group(name string, f func(Printer)) {
	divider := strings.Repeat("=", (120-len(name))/2)
	fmt.Println(divider, name, divider)
	f(stdout{})
	fmt.Println()
}

func str(v any) string {
	switch v := v.(type) {
	case string:
		return v
	case float32, float64:
		return fmt.Sprintf("%.3f", v)
	default:
		return fmt.Sprintf("%v", v)
	}
}

func table(headers []string, generator iter.Seq[[]any]) {
	maxColumn := len(headers)
	columnMaxWidth := make([]int, len(headers))
	for i, header := range headers {
		columnMaxWidth[i] = len(header)
	}

	var data [][]string
	for elem := range generator {
		maxColumn = max(maxColumn, len(elem))

		currRow := make([]string, 0, maxColumn)
		for i := range len(elem) {
			currRow = append(currRow, str(elem[i]))
			if i >= len(columnMaxWidth) {
				columnMaxWidth = append(columnMaxWidth, 0)
			}
			columnMaxWidth[i] = max(columnMaxWidth[i], len(currRow[len(currRow)-1]))
		}
		data = append(data, currRow)
	}

	indent := "    "
	tablePrint := func(line []string, divider ...bool) {
		for i, v := range line {
			fmt.Printf("%-*s%s", columnMaxWidth[i], v, indent)
		}
		fmt.Printf("\n")

		if len(divider) != 0 && divider[0] {
			sum := (maxColumn - 1) * len(indent)
			for _, v := range columnMaxWidth {
				sum += v
			}
			fmt.Println(strings.Repeat("-", sum))
		}
	}

	tablePrint(headers, true)
	for _, row := range data {
		tablePrint(row)
	}
}

func generate(gen func(yield func(...any) bool)) iter.Seq[[]any] {
	return func(yield func([]any) bool) {
		gen(func(args ...any) bool {
			return yield(args)
		})
	}
}

func infinite(prefix string) iter.Seq[string] {
	return func(yield func(string) bool) {
		for i := 0; i < math.MaxInt64; i++ {
			if !yield(prefix + strconv.Itoa(i)) {
				return
			}
		}
	}
}

const (
	BootstrapServers = "kafka.hasakk.com:9096"
	TopicName        = "playground"
)

func listTopics(ctx context.Context, bootstrapServer string) map[string]bool {
	conn, err := kafka.DialContext(ctx, "tcp", bootstrapServer)
	if err != nil {
		slog.Error("failed to connect to broker", "error", err, "bootstrap", bootstrapServer)
		return nil
	}
	defer func() { _ = conn.Close() }()

	partitions, err := conn.ReadPartitions()
	if err != nil {
		slog.Error("failed to read partitions", "error", err)
		return nil
	}

	topics := make(map[string]bool)
	table([]string{"ID", "TOPIC", "LEADER"}, generate(func(yield func(...any) bool) {
		for _, partition := range partitions {
			topics[partition.Topic] = true
			if !yield(partition.ID, partition.Topic, partition.Leader.ID) {
				return
			}
		}
	}))

	return topics
}

func listBrokers(ctx context.Context, bootstrapServer string) []string {
	conn, err := kafka.DialContext(ctx, "tcp", bootstrapServer)
	if err != nil {
		slog.Error("failed to connect to broker", "error", err, "bootstrap", bootstrapServer)
		return nil
	}
	defer func() { _ = conn.Close() }()

	brokers, err := conn.Brokers()
	if err != nil {
		slog.Error("failed to list available brokers", "error", err)
		return nil
	}

	res := make([]string, 0, len(brokers))
	table([]string{"ID", "ADDRESS", "RACK"}, generate(func(yield func(...any) bool) {
		for _, broker := range brokers {
			addr := net.JoinHostPort(broker.Host, strconv.Itoa(broker.Port))
			if !yield(broker.ID, addr, broker.Rack) {
				return
			}
			res = append(res, addr)
		}
	}))

	return res
}

func createIfAbsent(ctx context.Context, bootstrapServer string, topic string, partitions, replications int) error {
	conn, err := kafka.DialContext(ctx, "tcp", bootstrapServer)
	if err != nil {
		slog.Error("failed to connect to broker", "error", err, "bootstrap", bootstrapServer)
		return nil
	}
	defer func() { _ = conn.Close() }()

	return conn.CreateTopics(kafka.TopicConfig{
		Topic:             topic,
		NumPartitions:     partitions,
		ReplicationFactor: replications,
	})
}

func readMessages(ctx context.Context, brokers []string, topic, groupId string, count int) {
	var wg conc.WaitGroup
	for i := 0; i < count; i++ {
		logger := slog.With("topic", topic, "groupId", groupId, "consumerId", i)
		wg.Go(func() {
			r := kafka.NewReader(kafka.ReaderConfig{
				Brokers: brokers,
				GroupID: groupId,
				Topic:   topic,
				Logger: kafka.LoggerFunc(func(format string, args ...interface{}) {
					logger.Debug("kafka reader", "content", fmt.Sprintf(format, args...))
				}),
			})
			// The kafka server needs a graceful disconnect to stop it from continuing
			// to attempt to send messages to the connected clients.
			defer func() { _ = r.Close() }()

			logger.Debug("start reading messages")
			for {
				message, err := r.FetchMessage(ctx)
				if err != nil {
					if errors.Is(err, io.EOF) || errors.Is(err, context.Canceled) {
						logger.Info("exit")
						return
					}

					logger.Warn("failed to fetch message from kafka", "error", err)
					time.Sleep(time.Millisecond * time.Duration(500+rand.Intn(1000)))
					continue
				}

				logger.Info("fetched message", "content", string(message.Value), "partition", message.Partition)
				if err = r.CommitMessages(ctx, message); err != nil {
					logger.Warn("failed to commit messages", "error", err, "message", message)
				}
			}
		})
	}

	wg.Wait()
}

func writeMessages(ctx context.Context, brokers []string, topic string, messages iter.Seq[string]) {
	w := kafka.Writer{
		Addr:     kafka.TCP(brokers...),
		Topic:    topic,
		Balancer: &kafka.RoundRobin{},
	}

	for message := range messages {
		select {
		case <-ctx.Done():
			return
		default:
			err := w.WriteMessages(ctx, kafka.Message{Value: []byte(message)})
			if err != nil {
				slog.Error("failed to write message to kafka", "error", err)
				continue
			}

			slog.Info("successfully wrote message to kafka", "message", message)
			time.Sleep(time.Millisecond * time.Duration(500+rand.Intn(1000)))
		}
	}
}

func main() {
	if _, ok := os.LookupEnv("DEBUG"); ok {
		slog.SetLogLoggerLevel(slog.LevelDebug)
	}

	ctx, cancel := signal.NotifyContext(context.Background(), os.Interrupt, syscall.SIGTERM)
	defer cancel()

	var topics map[string]bool
	group("TOPICS", func(p Printer) {
		topics = listTopics(ctx, BootstrapServers)
	})

	var brokers []string
	group("BROKERS", func(p Printer) {
		brokers = listBrokers(ctx, BootstrapServers)
		if len(brokers) == 0 {
			os.Exit(1)
		}
	})

	if _, found := topics[TopicName]; !found {
		group("CREATE TOPIC", func(p Printer) {
			err := createIfAbsent(ctx, BootstrapServers, TopicName, len(brokers)*2, len(brokers)-1)
			if err != nil {
				panic(err)
			}

			table([]string{"CREATE_TOPIC"}, generate(func(yield func(...any) bool) {
				if !yield("OK") {
					return
				}
			}))
		})
	}

	stopCtx, stopCancel := context.WithCancel(context.Background())
	go func() {
		defer stopCancel()

		var wg sync.WaitGroup
		wg.Go(func() { writeMessages(ctx, brokers, TopicName, infinite("pa-")) })
		wg.Go(func() { writeMessages(ctx, brokers, TopicName, infinite("pb-")) })
		wg.Wait()
	}()

	var wg conc.WaitGroup
	wg.Go(func() { readMessages(stopCtx, brokers, TopicName, "c-a", 3) })
	wg.Go(func() { readMessages(stopCtx, brokers, TopicName, "c-b", 2) })
	wg.Wait()
}
