use package_and_crates::say_hello;

// A package can have multiple binary crates by placing files
// in the src/bin directory: each file will be a separate binary crate.
fn main() {
    say_hello("foo");
}
