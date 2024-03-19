pub mod lib_module;

use crate::lib_module::say_hello;

pub fn say() {
    say_hello();
}

mod front_of_house {
    pub mod hosting {
        pub fn add_to_waitlist() {}
    }
}

pub fn eat_at_restaurant() {
    // we use an absolute path
    crate::front_of_house::hosting::add_to_waitlist();

    // we use a relative path
    front_of_house::hosting::add_to_waitlist();
}

mod outer_mod {
    fn foo() {}

    mod inner_mod {
        #[allow(dead_code)]
        fn bar() {
            // items in child modules can use the items in their ancestor modules.
            super::foo();
        }
    }
}
