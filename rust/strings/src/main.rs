#[derive(Debug)]
struct User {
    username: String,
    age: u32
}

impl std::fmt::Display for User {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> Result<(), std::fmt::Error> {
        write!(f, "{self:?}")
    }
}

fn main() {
    {
        let s = String::new();
        println!("the value of empty string is {s:?}");
    }

    {
        let data = "hello, world!";

        let s = data.to_string();
        println!("the value of s is {s:?}");
    }

    {
        let u = User{username: String::from("foo"), age: 16};
        // we can use `to_string` method which is available on any type
        // that implements the `Display` trait.
        let s = u.to_string();

        println!("the value of s is {s:?}");
    }

    {
        let mut s = "hello, ".to_string();
        // appending to a string with `push_str` and `push`.
        s.push_str("world"); // the `push_str` method takes a string slice.
        s.push('!'); // the `push` method takes a single character as a parameter.

        println!("the value of s is {s:?}");
    }

    // concatenation with the `+` operator or the `format!` macro
    {
        let s1 = String::from("hello, ");
        let mut s2 = String::from("world!");

        // the compiler can *coerce* the `&String` argument into a `&str`.
        // equals calling s1.add(&s2).
        let s3 = s1 + &s2; // note s1 has been moved here and can no longer be used.
        println!("the value of s2 is {s2:?}");
        println!("the value of s3 is {s3:?}");

        // 1. `add` takes ownership of `s1`,
        // 2. it appends a copy of the contents of `s2` to `s1`,
        // 3. and then it returns back ownership of `s1`.
        s2 = String::from("🤭");
        println!("the value of s2 is {s2:?}");

        // `format!` macro uses references so that this call doesn't take
        // ownership of any of its parameters.
        let s4 = format!("{s2} {s3}");
        println!("the value of s4 is {s4:?}");
    }

    // indexing ans slicing into Strings
    {
        let s = "🤭😊".to_string();
        // let c = s[0]; // `String` cannot be indexed by `{integer}`

        //let face = &s[..2]; // byte index 2 is not a char boundary; it is inside '🤭' (bytes 0..4) of `🤭😊`
        let face = &s[..4];
        println!("the value of face is {face:?}")
    }

    {
        // the best way to operate on pieces of strings is to be explicit
        // about whether you want character or bytes.
        let s = "🤭😊".to_string();
        for c in s.chars() {
            println!("{c:?}");
        }
        for b in s.bytes() {
            println!("{b:?}");
        }
    }
}

