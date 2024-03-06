fn main() {
    let s = String::from("hello world!");
    println!("the first space in '{s}' is {}", first_space_index(&s));
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

