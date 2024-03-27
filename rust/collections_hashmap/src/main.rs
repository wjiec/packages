use std::collections::HashMap;

fn main() {
    let mut scores = HashMap::new();

    {
        scores.insert("Blue", 10);
        scores.insert("Yellow", 20);
        println!("the value of scores is {scores:?}");
    }

    {
        // accessing values in a HashMap
        let score: Option<&i32> = scores.get("Blue");
        println!("the score of blue team is {score:?}");

        let score: Option<i32> = scores.get("Yellow").copied();
        println!("the score of yellow team is {score:?}");
    }

    {
        scores.insert("Blue", 33);
        for (k, v) in &scores {
            println!("in scores: {k:?} => {v:?}");
        }
    }

    {
        // adding a key and value only if a key isn't present.
        scores.entry("Blue").or_insert(55);
        scores.entry("Red").or_insert(77);
        println!("the value of scores is {scores:?}");
    }

    {
        // updateing a value based on the old value.
        let text = "hello world hello rust";
        let mut words = HashMap::new();
        for word in text.split_whitespace() {
            let count = words.entry(word).or_insert(0);
            *count += 1;
        }
       println!("the value of words is {words:?}"); 
    }
}

