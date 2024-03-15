// enums give you a way of saying a value is one
// of a possible set of values.

#[derive(Debug)]
enum IpAddrKind {
    // we can put data directly into each enum variant.
    V4(u8, u8, u8, u8),

    // each variant can have different types and amount
    // of associated data.
    V6(String),
}

#[derive(Debug)]
enum Message {
    Quit,
    Move{ x: u32, y: u32 },
    Write(String),
    ChangeColor(u8, u8, u8),
}

impl Message {
    fn print(&self) {
        println!("the value of message is {self:?}");
    }
}

fn main() {
    {
        let four = IpAddrKind::V4(127, 0, 0, 1);
        println!("the value of four is {four:?}");

        let six = IpAddrKind::V6(String::from("::1"));
        println!("the value of six is {six:?}");
    }

    {
        let q = Message::Quit;
        println!("the value of q is {q:?}");

        let m = Message::Move{x: 1, y : 10};
        m.print();

        Message::Write(String::from("hello world")).print();
        Message::ChangeColor(255, 0, 255).print();
    }
}

