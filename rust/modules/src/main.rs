use crate::garden::vegetables::Asparagus;

// when compiling a crate, the compiler first looks in the
// crate root file(`src/lib.rs` or `src/main.rs`).

// the compile will look for module's code in there places.

// 1. inline: whcih curly brackets that replace the semicolon following `mod ...`
mod inline_module {
    pub fn say_hello() {
        println!("hello");
    }
}

// 2. in the file `src/file_module.rs`
mod file_module;

// 3. in the file `src/group_module/mod.rs`
mod group_module;

pub mod garden;

fn main() {
    println!("Hello, {:?}", Asparagus{});
}
