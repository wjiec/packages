fn main() {
    {
        let s = String::from("hello, world!");
        println!("the length of '{s}' is {}", calculate_length(&s));
    }

    // mutable references 
    {
        let mut s = String::from("hello, ");
        add_name(&mut s, "rust");
        println!("the value of s is {s}");

        // restriction: if we have a mutable reference to a value, we can
        // have no other reference to that value.
        let r1 = &mut s;
        // let r2 = &s; // cannot borrow `s` as immutable because it is also borrowed as mutable
        println!("the value of r1 and r2 is '{r1}' '{{r2}}'");

        // a reference's scope starts from where it is intrduced and continues
        // through the last time that reference is used.
        //
        // aka: there scope don't overlap.i
        {
            let mut s = String::from("hello");

            let r1 = &mut s;
            println!("the value of r1 is '{r1}'");

            let r2 = &s;
            let r3 = &s;
            println!("the value of r2 and r3 is '{r2}' '{r3}'");
        }

        {
            let mut a = vec![1, 2, 3];
            let r: &mut i32 = &mut a[2]; // mutable borrow occurs here

            // println!("the value of a is {:?}", a); // immutable borrow occurs here
            *r += 1;
            println!("the value of r is {}", r);
        }
    }

    {
        let mut s = String::from("hello world!");
        let r1 = &s;

        // s = String::from("changed string"); // cannot assign to `s` because it is borrowed
        println!("the value of r1 is {r1}");
    }
}

// these ampersands(&) represents references, and allow
// you to refer to some value without taking ownership of it.
//
// we call the action of creating a reference borrowing.
fn calculate_length(s: &String) -> usize {
    s.len()
}

fn add_name(s: &mut String, name: &str) {
    s.push_str(name);
}
