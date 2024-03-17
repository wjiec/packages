#[derive(Debug)]
enum Direction {
    Left(i32),
    Right(i32),
}

fn main() {
    {
        let thread_count = Some(32u8);
        if let Some(n) = thread_count {
            println!("the value of n is {n}");
        }
    }

    {
        let s = Some(String::from("hello, world!"));
        if let Some(s) = &s {
            println!("the value of s in scope is {s:?}");
        }
        println!("the value of s out of scope is {s:?}");
    }

    {
        let s = Direction::Left(123);
        if let Direction::Right(n) = s {
            println!("the value of n is {n:?}");
        }
    }
}

