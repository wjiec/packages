
#[derive(Debug, Copy, Clone, PartialEq)]
pub enum Token {
    Number(f64),
    Plus,
    Minus,
    Multiply,
    Divide,
    LeftParen,
    RightParen,
}

impl Token {
    pub fn infix_binding_power(self) -> Option<(u8, u8)> {
        match self {
            Token::Plus | Token::Minus => Some((1, 2)),
            Token::Multiply | Token::Divide => Some((3, 4)),
            _ => None,
        }
    }

    pub fn prefix_binding_power(self) -> Option<((), u8)> {
        match self {
            Token::Plus | Token::Minus => Some(((), 9)),
            _ => None,
        }
    }
}
