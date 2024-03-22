mod front_of_house {
    pub mod hosting {
        pub fn add_to_waitlist() {}
    }
}

// Note that `ise` only creates the shortcut for the
// particular scope in which the `use` occurs.
use crate::front_of_house::hosting;
// re-exporting bringing an ite into scope and making
// that item available for other to bring into their scope.
pub use hosting::add_to_waitlist;

mod customer {
    pub fn eat_at_restaurant() {
        // use of undeclared crate or module `hosting`
        // hosting::add_to_waitlist();
    }
}

fn main() {
    println!("Hello, world!");
}

