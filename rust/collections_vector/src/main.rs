fn print_type_of<T: std::fmt::Debug>(v: &T) {
    println!("type({v:?}) is {}", std::any::type_name::<&T>());
}

fn main() {
    {
        let mut v: Vec<i32> = Vec::new();
        println!("the value of v is {v:?}");

        v.push(1);
        v.push(2);
        println!("the value of v is {v:?}");

        let first: &i32 = &v[0];
        println!("the first element is {first}");

        let second: Option<&i32> = v.get(1);
        println!("the second element is {second:?}");

        let third: Option<&i32> = v.get(2);
        println!("the third element is {third:?}");
    }

    {
        let mut v = Vec::new();
        v.push(String::from("foo"));
        v.push(String::from("bar"));

        // let s = v[0]; // move occurs because value has type `String`, which does not implement the `Copy` trait
        let s: &String = &v[0];
        println!("the first element is {s:?}");
    }

    {
        let v = vec![1, 2, 3, 4, 5];
        for n_ref in &v {
            println!("{n_ref:?}");
        }
    }

    {
        #[allow(unused_variables)]
        let v = vec![String::from("hello")];
        //let &(mut s) = &v[0];
        //s.push_str("world");

        let mut v = vec![String::from("hello")];
        let s = &mut v[0];
        s.push_str("world");
        print_type_of(s);
    }
}

