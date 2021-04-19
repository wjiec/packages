package person

type Person struct {
	age  int
	name string
}

func (p *Person) Age() int {
	return p.age
}

func (p *Person) Name() string {
	return p.name
}

func (p *Person) SetAge(age int) {
	p.age = age
}

func (p *Person) SetName(name string) {
	p.name = name
}

func New(name string, age int) *Person {
	return &Person{name: name, age: age}
}
