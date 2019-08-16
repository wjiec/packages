package main

import (
	"fmt"
)

type sender interface {
	Send(message string)
}

type user struct {
	name string
	email string
}

func (u *user) Send(message string) {
	fmt.Printf("<%+v>: ", u)
	fmt.Printf("send message %s to user %s <%s>\n", message, u.name, u.email)
}

type admin struct {
	user
	level int
}

type boss struct {
	admin
	name int
	level string
}

func (b *boss) Send(message string) {
	fmt.Printf("Boss<%+v>: ", b)
	fmt.Printf("send message %s to boss %d <%s>\n", message, b.name, b.email)
}

func notify(s sender) {
	s.Send("notify from system")
}

func main() {
	a := admin{
		user: user{
			name: "Administrator",
			email: "administrator@localhost",
		},
		level: 10,
	}

	fmt.Println(a.level, a.name, a.email)
	fmt.Printf("<%+v>\n", a)

	a.Send("hello, I'm golang.")
	a.user.Send("nice to meet you")

	notify(&a)
	notify(&a.user)

	b := boss{
		admin: admin{
			user: user{
				name: "boss",
				email: "boss@localhost",
			},
			level: 100,
		},
		name: 100,
		level: "BossLevel",
	}

	fmt.Println(b.level, b.name, b.email)
	fmt.Printf("<%+v>\n", b)

	b.Send("hello, I'm golang.")
	b.user.Send("nice to meet you")

	notify(&b)
	notify(&b.user)
}
