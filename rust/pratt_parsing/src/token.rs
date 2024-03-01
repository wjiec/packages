#[derive(Debug, Copy, Clone, PartialEq)]
pub enum Token {
    Number(f64),
    Add,
    Subtract,
    Multiply,
    Divide,
    Caret,
    LeftParen,
    RightParen,
    Unknown(char),
    Invalid(char),
}

impl Token {
    pub fn infix_binding_power(self) -> Option<(u8, u8)> {
        match self {
            Token::Add | Token::Subtract => Some((1, 2)),
            Token::Multiply | Token::Divide => Some((3, 4)),
            Token::Caret => Some((5, 6)),
            _ => None,
        }
    }

    pub fn prefix_binding_power(self) -> Option<((), u8)> {
        match self {
            Token::Add | Token::Subtract => Some(((), 9)),
            _ => None,
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn infix_binding_power() {
        let tokens = [
            Token::Add,
            Token::Subtract,
            Token::Multiply,
            Token::Divide,
            Token::Caret,
        ];
        for token in tokens {
            if let Some((l, r)) = token.infix_binding_power() {
                assert!(l < r)
            }
        }
    }

    #[test]
    fn prefix_binding_power() {
        let tokens = [Token::Add, Token::Subtract];
        for token in tokens {
            if let Some((_, r)) = token.prefix_binding_power() {
                assert!(r > 0)
            }
        }
    }
}
