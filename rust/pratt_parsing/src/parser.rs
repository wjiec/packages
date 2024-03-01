use std::error::Error;
use std::fmt::{Display, Formatter};

use crate::ast::Node;
use crate::lexer::Lexer;
use crate::parser::ParseError::{InvalidExpression, UnknownOperator};
use crate::token::Token;

pub struct Parser<'a> {
    lexer: Lexer<'a>,
}

impl<'a> Parser<'a> {
    pub fn new(expr: &'a str) -> Self {
        Self {
            lexer: Lexer::new(expr),
        }
    }

    pub fn parse(&mut self) -> Result<Node, ParseError> {
        self.expr_bp(0)
    }

    fn expr_bp(&mut self, min_bp: u8) -> Result<Node, ParseError> {
        let mut lhs = match self.lexer.next() {
            Some(Token::Number(f)) => Node::Number(f),
            Some(token @ Token::Add) => {
                let ((), r_bp) = token.prefix_binding_power().unwrap();

                self.expr_bp(r_bp)?
            }
            Some(token @ Token::Subtract) => {
                let ((), r_bp) = token.prefix_binding_power().unwrap();

                Node::Negative(Box::new(self.expr_bp(r_bp)?))
            }
            Some(Token::LeftParen) => {
                let lhs = self.expr_bp(0);
                match self.lexer.next() {
                    Some(next_token) => {
                        if next_token != Token::RightParen {
                            return Err(InvalidExpression);
                        }
                    }
                    None => return Err(InvalidExpression),
                }

                lhs?
            }
            Some(Token::Unknown(c)) => return Err(UnknownOperator(c)),
            _ => return Err(InvalidExpression),
        };

        loop {
            let operator = match self.lexer.peek() {
                Some(t @ Token::Add) => t,
                Some(t @ Token::Subtract) => t,
                Some(t @ Token::Multiply) => t,
                Some(t @ Token::Divide) => t,
                Some(t @ Token::Caret) => t,
                _ => break,
            };

            if let Some((l_bp, r_bp)) = operator.infix_binding_power() {
                if l_bp < min_bp {
                    break;
                }

                self.lexer.next();
                let rhs = self.expr_bp(r_bp)?;
                lhs = match operator {
                    Token::Add => Node::Add(Box::new(lhs), Box::new(rhs)),
                    Token::Subtract => Node::Subtract(Box::new(lhs), Box::new(rhs)),
                    Token::Multiply => Node::Multiply(Box::new(lhs), Box::new(rhs)),
                    Token::Divide => Node::Divide(Box::new(lhs), Box::new(rhs)),
                    Token::Caret => Node::Caret(Box::new(lhs), Box::new(rhs)),
                    _ => return Err(InvalidExpression),
                }
            } else {
                return Err(InvalidExpression);
            }
        }

        Ok(lhs)
    }
}

#[derive(Debug)]
pub enum ParseError {
    InvalidExpression,
    UnknownOperator(char),
}

impl<'a> Display for ParseError {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        match &self {
            InvalidExpression => write!(f, "invalid expression"),
            UnknownOperator(c) => write!(f, "unknown operator: {c}"),
        }
    }
}

impl Error for ParseError {}

impl From<Box<dyn Error>> for ParseError {
    fn from(_error: Box<dyn Error>) -> Self {
        InvalidExpression
    }
}

#[cfg(test)]
mod tests {
    use crate::ast;

    use super::*;

    #[test]
    fn number() {
        let mut parser = Parser::new("1");
        assert_eq!(ast::eval(parser.parse().unwrap()).unwrap(), 1.0);

        let mut parser = Parser::new("1.23");
        assert_eq!(ast::eval(parser.parse().unwrap()).unwrap(), 1.23);
    }

    #[test]
    fn negative() {
        let mut parser = Parser::new("-123");
        assert_eq!(ast::eval(parser.parse().unwrap()).unwrap(), -123.0);

        let mut parser = Parser::new("-123.45");
        assert_eq!(ast::eval(parser.parse().unwrap()).unwrap(), -123.45);
    }

    #[test]
    fn add() {
        let mut parser = Parser::new("1 + 1");
        assert_eq!(ast::eval(parser.parse().unwrap()).unwrap(), 2.0)
    }

    #[test]
    fn add_negative() {
        let mut parser = Parser::new("-1 + -2");
        assert_eq!(ast::eval(parser.parse().unwrap()).unwrap(), -3.0)
    }

    #[test]
    fn grouping() {
        let mut parser = Parser::new("(1 + 2) * (3 + 4 * 5)");
        assert_eq!(ast::eval(parser.parse().unwrap()).unwrap(), 69.0)
    }

    #[test]
    fn complex() {
        let mut parser = Parser::new("-(1 ^ 2) * (3 + 4 * 5) - (-2 ^ 3)");
        assert_eq!(ast::eval(parser.parse().unwrap()).unwrap(), -15.0)
    }

    #[test]
    fn bad_grouping() {
        let mut parser = Parser::new("(1 + 1");
        assert!(parser.parse().is_err())
    }
}
