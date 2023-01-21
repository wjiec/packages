use std::cmp::Ordering;
use std::io::{stdin, stdout, Write};

use rand::Rng;

fn main() {
    println!("~~ I have a secret number, if you can guess it, I will give you a surprise ~~\n");
    let secret_number: i32 = rand::thread_rng().gen_range(1..101);

    loop {
        print!("Guessing a number: ");
        stdout().flush().unwrap();

        let mut guessing: String = String::new();
        stdin().read_line(&mut guessing).expect("Unable to reads user input");

        let guessing: i32 = match guessing.trim().parse() {
            Ok(number) => number,
            Err(_) => {
                println!("Please a number with range from 1 to 100\n");
                continue
            }
        };

        match guessing.cmp(&secret_number) {
            Ordering::Greater => {
                println!("~~ Just a little bit smaller ~~\n")
            }
            Ordering::Less => {
                println!("~~ Just a little bit bigger ~~\n")
            }
            Ordering::Equal => {
                println!("~~ You win, give you a lovely hug ~~\n");
                break
            }
        }
    }
}
