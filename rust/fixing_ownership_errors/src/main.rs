fn main() {
    println!("Hello, world!");
}

// aliasing and mutation a data structure
//fn add_big_strings(dst: &mut vec<string>, src: &[string]) {
//    let longest = dst.iter().max_by_key(|s| s.len()).unwrap(); // immutable borrow occurs here
//
//    for to_add in src {
//        if to_add.len() > longest.len() {
//            dst.push(to_add.clone()); // mutable borrow occurs here
//        }
//    }
//}

#[allow(dead_code)]
fn add_big_strings(dst: &mut Vec<String>, src: &[String]) {
    let longest = dst.iter().max_by_key(|s| s.len()).unwrap().len();

    for to_add in src {
        if to_add.len() > longest {
            dst.push(to_add.clone());
        }
    }
}

// copying vs moving
//  in sum, if a value does not own heap data, then it can be copied without a move.
//fn take_ownership_from_ref(r: &String) -> usize {
//    let s = *r; // move occurs because `*r` has type `String`, which does not implement the `Copy` trait
//
//    s.len()
//}

// mutating different tuple fields
#[allow(dead_code)]
fn tuple_ownership() {
    let mut tup = (String::from("Apple"), String::from("Banana"));

    let r0 = &tup.0;
    let r1 = &mut tup.1;
    r1.push_str("!");
    println!("the value of r1 is '{r1}'");
    println!("the value of r0 is '{r0}'");
}

// Rust can lose track of exactly which paths are borrowed.
fn take_tuple_elem_ownership(tup: &(String, String, i32)) -> &String {
    &tup.0
}

fn tuple_elem_ownership() {
    let mut tup = (String::from("Apple"), String::from("Banana"), 1);

    let r1 = take_tuple_elem_ownership(&tup);
    //let r2 = &mut tup.1;
    let r3 = &mut tup.2;
    *r3 += 1;
    println!("{r1} {{r2}} {r3}");
}


