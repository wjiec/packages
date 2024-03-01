use std::iter::Peekable;
use std::str::Chars;

use crate::token::Token;

pub struct Lexer<'a> {
    expr: Peekable<Chars<'a>>,
    curr: Option<Token>,
}

impl<'a> Lexer<'a> {
    pub fn new(expr: &'a str) -> Self {
        Self {
            expr: expr.chars().peekable(),
            curr: None,
        }
    }

    pub fn peek(&mut self) -> Option<Token> {
        if self.curr == None {
            self.curr = self.next();
        }

        self.curr
    }
}

impl<'a> Iterator for Lexer<'a> {
    type Item = Token;

    fn next(&mut self) -> Option<Self::Item> {
        if self.curr != None {
            let ans = self.curr;
            self.curr = None;

            return ans;
        }

        loop {
            match self.expr.peek() {
                Some(c) => {
                    if c.is_whitespace() {
                        self.expr.next();
                    } else {
                        break;
                    }
                }
                None => break,
            }
        }

        match self.expr.next() {
            Some(c @ '0'..='9') => {
                let mut has_dot = false;
                let mut number = c.to_string();
                while let Some(c) = self.expr.peek() {
                    if c.is_numeric() {
                        number.push(self.expr.next()?);
                    } else if c == &'.' {
                        if has_dot {
                            return Some(Token::Invalid(*c));
                        }

                        has_dot = true;
                        number.push(self.expr.next()?);
                    } else {
                        break;
                    }
                }

                Some(Token::Number(number.parse().unwrap()))
            }
            Some('+') => Some(Token::Add),
            Some('-') => Some(Token::Subtract),
            Some('*') => Some(Token::Multiply),
            Some('/') => Some(Token::Divide),
            Some('^') => Some(Token::Caret),
            Some('(') => Some(Token::LeftParen),
            Some(')') => Some(Token::RightParen),
            Some(c) => Some(Token::Unknown(c)),
            None => None,
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
    fn bad_float() {
        let mut l = Lexer::new("1145.14.00");
        assert_eq!(l.next(), Some(Token::Invalid('.')));
    }

    #[test]
    fn symbol() {
        let mut l = Lexer::new("+-*/^()");
        assert_eq!(l.next(), Some(Token::Add));
        assert_eq!(l.next(), Some(Token::Subtract));
        assert_eq!(l.next(), Some(Token::Multiply));
        assert_eq!(l.next(), Some(Token::Divide));
        assert_eq!(l.next(), Some(Token::Caret));
        assert_eq!(l.next(), Some(Token::LeftParen));
        assert_eq!(l.next(), Some(Token::RightParen));
    }

    #[test]
    fn unknown() {
        let mut l = Lexer::new("@");
        assert_eq!(l.next(), Some(Token::Unknown('@')));
    }

    #[test]
    fn peek() {
        let mut l = Lexer::new("+-");
        assert_eq!(l.peek(), Some(Token::Add));
        assert_eq!(l.next(), Some(Token::Add));
        assert_eq!(l.peek(), Some(Token::Subtract));
        assert_eq!(l.peek(), Some(Token::Subtract));
        assert_eq!(l.next(), Some(Token::Subtract));
    }

    #[test]
    fn whitespace() {
        let mut l = Lexer::new("1 + 1");
        assert_eq!(l.next(), Some(Token::Number(1.)));
        assert_eq!(l.next(), Some(Token::Add));
        assert_eq!(l.next(), Some(Token::Number(1.)));
    }
}
