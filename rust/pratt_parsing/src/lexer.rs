use std::iter::Peekable;
use std::str::Chars;

use crate::token::Token;

pub struct Lexer<'a> {
    expr: Peekable<Chars<'a>>,
}

impl<'a> Lexer<'a> {
    pub fn new(input: &'a str) -> Self {
        Self {
            expr: input.chars().peekable(),
        }
    }
}

impl<'a> Iterator for Lexer<'a> {
    type Item = Token;

    fn next(&mut self) -> Option<Self::Item> {
        match self.expr.next()? {
            c @ '0'..='9' => {
                let mut has_dot = false;
                let mut number = c.to_string();
                while let Some(c) = self.expr.peek() {
                    if c.is_numeric() || (!has_dot && c == &'.') {
                        has_dot = c == &'.';
                        number.push(self.expr.next()?);
                    } else {
                        break;
                    }
                }

                Some(Token::Number(number.parse().unwrap()))
            }
            '+' => Some(Token::Plus),
            '-' => Some(Token::Minus),
            '*' => Some(Token::Multiply),
            '/' => Some(Token::Divide),
            '(' => Some(Token::LeftParen),
            ')' => Some(Token::RightParen),
            c => {
                if !c.is_ascii_whitespace() {
                    panic!("unknown character {c}")
                }
                None
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn integer() {
        let mut l = Lexer::new("114514");
        assert_eq!(l.next(), Some(Token::Number(114514.)))
    }

    #[test]
    fn float() {
        let mut l = Lexer::new("1145.14");
        assert_eq!(l.next(), Some(Token::Number(1145.14)))
    }

    #[test]
    fn symbol() {
        let mut l = Lexer::new("+-*/()");
        assert_eq!(l.next(), Some(Token::Plus));
        assert_eq!(l.next(), Some(Token::Minus));
        assert_eq!(l.next(), Some(Token::Multiply));
        assert_eq!(l.next(), Some(Token::Divide));
        assert_eq!(l.next(), Some(Token::LeftParen));
        assert_eq!(l.next(), Some(Token::RightParen));
    }

    #[test]
    #[should_panic]
    fn bad() {
        let mut l = Lexer::new("@");
        l.next();
    }
}
