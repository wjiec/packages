use package_and_crates::say_hello;

// cargo follows a convention that `src/main.rs` is the
// crate root of a binary crate with same name as the package.
// 
// cargo knowns that if the package directory contains `src/lib.rs`,
// the package contains a library crate with the same name as the package,
// and `src/lib.rs` is its crate root.
fn main() {
    say_hello("rust");
}
