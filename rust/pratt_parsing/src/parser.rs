use crate::lexer::Lexer;

#[macro_export]
macro_rules! eval {
    ($a: ident $op: tt $b: ident) => {
        {
            eval(*$a).and_then(|a| {
                eval(*$b).and_then(|b| Ok(a $op b))
            })
        }
    };
}

pub fn expr(input: &str) -> Result<f64, String> {
    let mut lexer = Lexer::new(input);
    expr_bp(&mut lexer, 0).and_then(|n| eval(n))
}

fn expr_bp(lexer: &mut Lexer, min_bp: u8) -> Result<Node, String> {
    let mut lhs = match lexer.next() {
        Some(t) => Node::Number(0.),
        None => Node::Number(0.),
    };

    Ok(lhs)
}

pub enum Node {
    Number(f64),
    Add(Box<Node>, Box<Node>),
    Minus(Box<Node>, Box<Node>),
    Multiply(Box<Node>, Box<Node>),
    Divide(Box<Node>, Box<Node>),
    Negative(Box<Node>),
}

pub fn eval(node: Node) -> Result<f64, String> {
    match node {
        Node::Number(f) => Ok(f),
        Node::Add(a, b) => eval!(a + b),
        Node::Minus(a, b) => eval!(a - b),
        Node::Multiply(a, b) => eval!(a * b),
        Node::Divide(a, b) => eval!(a / b),
        Node::Negative(a) => eval(*a).and_then(|x| Ok(-x)),
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn number() {
        let n = Node::Number(1.0);
        assert_eq!(eval(n), Ok(1.0))
    }

    #[test]
    fn add() {
        let n = Node::Add(Box::new(Node::Number(1.0)), Box::new(Node::Number(2.0)));
        assert_eq!(eval(n), Ok(3.0))
    }

    #[test]
    fn minus() {
        let n = Node::Minus(Box::new(Node::Number(1.0)), Box::new(Node::Number(2.0)));
        assert_eq!(eval(n), Ok(-1.0))
    }

    #[test]
    fn multiply() {
        let n = Node::Multiply(Box::new(Node::Number(1.0)), Box::new(Node::Number(2.0)));
        assert_eq!(eval(n), Ok(2.0))
    }

    #[test]
    fn divide() {
        let n = Node::Divide(Box::new(Node::Number(1.0)), Box::new(Node::Number(2.0)));
        assert_eq!(eval(n), Ok(0.5))
    }

    #[test]
    fn negative() {
        let n = Node::Negative(Box::new(Node::Number(1.0)));
        assert_eq!(eval(n), Ok(-1.0))
    }
}
