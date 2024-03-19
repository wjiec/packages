pub mod main_module;

// for code from the current crate, it starts with the literal `crate`
use crate::main_module::say_hello;

// for code from an external crate, the absolute path begins with the
// crate name. (for binary crate, the library crate is external
// crate even in the same package)
use module_paths::lib_module::say_hello as lib_say_hello;

pub mod foo {
    // we use `pub` before a struct definition, we make the
    // struct public, but the struct's fields will still be
    // private.
    pub struct Foo {
        count: i32,
        // we can make each field public or not on case-by-case basis.
        pub name: String
    }

    mod bar {
        // if we make an enum public, all of its variants are then public.
        pub enum Appetizer {
            Soup,
            Salad,
        }

        fn change_name(foo: &mut super::Foo) {
            foo.name = String::from("hello");
        }
    }

    mod baz {
        pub struct Point(pub i32, i32);
    }
}

fn main() {
    say_hello();
    lib_say_hello();
}

