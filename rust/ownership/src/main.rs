fn main() {
    // each value in rust has an owner.
    // there can only be one owner at a time.
    // when the owner goes out of scope, the value will be dropped.
    
    // vaiable scope
    { // s is not valid here, it's not yet declared
        let s = "hello, world!"; // s is valid from this point forward
        println!("s in current scope is {s}");
    } // this scope is now over, and s is no longer valid
    
    // variables and data interacting with move
    {
        let s1 = String::from("hello, move!");
        let s2 = s1; // value move here, and s1 is no longer valid
        println!("the value of s2 is {s2}");
    }

    // variables and data interacting with clone
    {
        let s1 = String::from("hello, clone!");
        let s2 = s1.clone();
        println!("the value of s1 is {s1}, and s2 is {s2}");
    }

    {
        let s1 = gives_ownership();
        println!("the value of s1 is {s1}");
    }
}

fn gives_ownership() -> String {
    let s = String::from("ownership from function");

    s
}
