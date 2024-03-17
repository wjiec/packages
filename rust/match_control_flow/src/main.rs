#[derive(Debug)]
enum State {
    Alabama,
    Alaska,
}

#[derive(Debug)]
enum Coin {
    Penny,
    Nickel,
    Dime,
    Quarter(State),
}

enum Location {
    Point(i32),
    Range(i32, i32),
}

fn main() {
    println!("value in cents of {:?} is {}.", Coin::Nickel, value_in_cents(Coin::Nickel));
    println!("value in cents of {:?} is {}.", Coin::Quarter(State::Alabama), value_in_cents(Coin::Quarter(State::Alabama)));

    println!("the value of Some(5) + 1 is {:?}", plus_one(Some(5)));
    println!("the value of None + 1 is {:?}", plus_one(None));

    {
        let s = Some(String::from("hello, world!"));

        match s {
            Some(_) => println!("Some!"),
            None => println!("None"),
        }
        println!("the value of s is {s:?}");
    }

    {
        let s = Some(String::from("hello, world!"));

        match s {
            Some(s) => println!("Some!"), // value partially moved here
            None => println!("None"),
        }
        //println!("the value of s is {s:?}"); // value borrowed here after partial move
    }

    {
        let s = Some(String::from("hello, world!"));

        match &s { // match on a reference
            Some(s) => println!("Some!"),
            None => println!("None"),
        }
        println!("the value of s is {s:?}");
    }

    {
        let l = Location::Range(0, 5);

        // each match is tried from top to bottom. Both the second
        // and third pattern are applicable, so the second one is used.
        let n = match l {
            Location::Point(_) => -1,
            Location::Range(_, n) => n,
            Location::Range(0, _) => 0,
            _ => -2
        };
        println!("the value of n is {n}");
    }
}

fn value_in_cents(coin: Coin) -> u8 {
    match coin {
        // if you want to run multiple lines of code in a match
        // arm, you must use curly brackets, and the comma of following
        // the arm is then optional.
        Coin::Penny => {
            println!("lucky penny!");

            1
        } // optional comma
        Coin::Nickel => {
            5
        },
        Coin::Dime => 10,
        Coin::Quarter(state) => {
            println!("  State querter from {state:?}");

            25
        },
    }
}

// we can also handle `Option<T>` using `match`
fn plus_one(v: Option<i32>) -> Option<i32> {
    match v {
        Some(v) => Some(v + 1),
        None => None,
    }
}

