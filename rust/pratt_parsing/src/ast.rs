use std::error::Error;

pub enum Node {
    Number(f64),
    Add(Box<Node>, Box<Node>),
    Subtract(Box<Node>, Box<Node>),
    Multiply(Box<Node>, Box<Node>),
    Divide(Box<Node>, Box<Node>),
    Caret(Box<Node>, Box<Node>),
    Negative(Box<Node>),
}

pub fn eval(node: Node) -> Result<f64, Box<dyn Error>> {
    match node {
        Node::Number(f) => Ok(f),
        Node::Add(a, b) => Ok(eval(*a)? + eval(*b)?),
        Node::Subtract(a, b) => Ok(eval(*a)? - eval(*b)?),
        Node::Multiply(a, b) => Ok(eval(*a)? * eval(*b)?),
        Node::Divide(a, b) => Ok(eval(*a)? / eval(*b)?),
        Node::Caret(a, b) => Ok(eval(*a)?.powf(eval(*b)?)),
        Node::Negative(a) => Ok(-eval(*a)?),
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn number() {
        let n = Node::Number(1.0);
        assert_eq!(eval(n).unwrap(), 1.0)
    }

    #[test]
    fn add() {
        let n = Node::Add(Box::new(Node::Number(1.0)), Box::new(Node::Number(2.0)));
        assert_eq!(eval(n).unwrap(), 3.0)
    }

    #[test]
    fn subtract() {
        let n = Node::Subtract(Box::new(Node::Number(1.0)), Box::new(Node::Number(2.0)));
        assert_eq!(eval(n).unwrap(), -1.0)
    }

    #[test]
    fn multiply() {
        let n = Node::Multiply(Box::new(Node::Number(1.0)), Box::new(Node::Number(2.0)));
        assert_eq!(eval(n).unwrap(), 2.0)
    }

    #[test]
    fn divide() {
        let n = Node::Divide(Box::new(Node::Number(1.0)), Box::new(Node::Number(2.0)));
        assert_eq!(eval(n).unwrap(), 0.5)
    }

    #[test]
    fn caret() {
        let n = Node::Caret(Box::new(Node::Number(2.0)), Box::new(Node::Number(3.0)));
        assert_eq!(eval(n).unwrap(), 8.0)
    }

    #[test]
    fn negative() {
        let n = Node::Negative(Box::new(Node::Number(1.0)));
        assert_eq!(eval(n).unwrap(), -1.0)
    }
}
