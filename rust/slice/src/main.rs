fn main() {
    let s = String::from("hello world!");
    println!("the first space in '{s}' is {}", first_space_index(&s));

    {
        let slice1 = &s[..first_space_index(&s)];
        println!("the first word in '{s}' is '{slice1}'");
    }

    {
        // String slice range indices must occur at valid UTF-8 character boundaries.
        let s = String::from("😊 🥰 🦀");
        let slice1 = &s[..1]; // invalid UTF-8 charachter boundary
        println!("the first word in '{s}' is '{slice1}'"); // panic: byte index 1 is not a char boundary
    }

    {
        let word = first_word(&s);
        // s.clear(); // mutable borrow occurs here
        println!("the first word in '{s}' is '{word}'");
    }

    {
        let a = [1, 2, 3, 4, 5, 6];
        let slice: &[i32] = &a[1..3];
        println!("the value of slice of i32 array is {}", slice[0]);
    }
}

fn first_space_index(s: &String) -> usize {
    let bytes = s.as_bytes();

    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return i;
        }
    }
    
    s.len()
}

fn first_word(s: &String) -> &str {
    let bytes = s.as_bytes();

    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return &s[..i];
        }
    }

    &s[..]
}

