package channel

import "fmt"

type pipe struct {
	in  chan int
	out chan int
}
type workerFunction func(p pipe)

func createWorker(f workerFunction) pipe {
	p := pipe{
		in:  make(chan int),
		out: make(chan int),
	}

	go f(p)
	return p
}

func doWork(p pipe) {
	for {
		fmt.Println(<-p.in)
		go func() {
			p.out <- 1
		}()
	}
}

func main() {
	var workers []pipe
	for i := 0; i < 1000; i++ {
		workers = append(workers, createWorker(doWork))
	}

	for index, worker := range workers {
		worker.in <- index
	}

	for index, worker := range workers {
		fmt.Printf("worker %d done, value = %d\n", index, <-worker.out)
	}
}
