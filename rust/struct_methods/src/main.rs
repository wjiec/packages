struct Rectangle {
    width: u32,
    height: u32,
}

impl Rectangle {
    // within an impl block, the type `Self` is an alias
    // for the type that the `impl` block is for.
    fn new(width: u32, height: u32) -> Self {
        Self { width, height }
    }

    // all functions defined within an `impl` block are called
    // associated functions, because they're associated with
    // the type named after the `impl`.
    fn square(size: u32) -> Self {
        Self { width: size, height: size }
    }
}

// each struct is allowed to have multiple `impl` blocks.
impl Rectangle {
    // the `&self` is actually short for `self: &Self`.
    fn area(&self) -> u32 {
        self.width * self.height
    }

    fn can_hold(&self, other: &Self) -> bool {
        self.width > other.width && self.height > other.height
    }

    fn max(self, other: Self) -> Self {
        Self {
            width: self.width.max(other.width),
            height: self.height.max(other.height),
        }
    }

    fn set_to_max(&mut self, other: Self) {
        //let max = self.max(other);

        // when we overwrite `*self`, rust will implicitly drop
        // the data previously in `*self`. (aka calling `drop(*self)`)
        //*self = max;
    }
}

fn main() {
    {
        let rect = Rectangle { width: 30, height: 50 };
        println!("The area of the rectangle is {}.", rect.area());
    }

    {
        let sq1 = Rectangle::square(10);
        let sq2 = Rectangle::square(20);
        println!("can sq2 hold sq1? => {}", sq2.can_hold(&sq1));
    }
}

