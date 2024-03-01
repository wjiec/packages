use std::io;
use std::io::Write;

use crate::parser::{ParseError, Parser};

mod ast;
mod lexer;
mod parser;
mod token;

fn main() {
    println!("Welcome to Arithmetic expression evaluator.");
    println!("  * Allowed numbers: positive, negative and decimals.");
    println!("  * Supported operations: Add, Subtract, Multiply, Divide and Power.");
    println!("Enter your arithmetic expression below:\n");

    loop {
        print!("Expression > ");
        io::stdout().flush().unwrap();

        let mut expr = String::new();
        match io::stdin().read_line(&mut expr) {
            Ok(_) => match evaluate(expr) {
                Ok(ans) => println!("The computed number is {ans}\n"),
                Err(error) => eprintln!("Error in evaluating expression: {error}\n"),
            },
            Err(error) => eprintln!("Error in reading input: {error}\n"),
        }
    }
}

fn evaluate<'a>(expr: String) -> Result<f64, ParseError> {
    let mut parser = Parser::new(&expr);
    let root = parser.parse()?;

    Ok(ast::eval(root)?)
}
