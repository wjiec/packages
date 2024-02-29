use crate::parser::expr;

mod token;
mod lexer;
mod parser;

fn main() {
    println!("result = {}", expr("1 + 2 * 3 / 4").unwrap())
}
