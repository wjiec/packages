fn main() {
    println!("Hello, world!");

    let mut number = 5;
    if number < 10 {
        println!("the number {number} is < 10");
    } else {
        println!("the number {number} is > 10");
    }

    if number % 2 == 0 {
        println!("number is divisible by 2");
    } else if number % 3 == 0 {
        println!("number is divisible by 3");
    } else if number % 5 == 0 {
        println!("number is divisible by 5");
    }

    // because `if` is an expression, so we can use it on the
    // right side of a `let` statement to assign the variable
    let x = if number % 2 == 0 { 1 } else { 0 };
    println!("the value of x is {x}");

    // kinds of loops: loop, while and for
    
    // loop to execute a block of code over and over again FOREVER
    // or until you explicitly tell it to stop.
    loop {
        println!("the value of number is {number}");
        if number > 10 {
            break;
        }
        number += 1;
    }

    // returning values from loops
    let loop_res = loop {
        number *= 2;
        if number > 30 {
            break number // the semicolon after `break` is technically optional
        }
    };
    println!("the value of loop_res is {loop_res}");

    // loop labels to disambiguate between multiple loops
    let mut count = 0;
    'counting_up: loop {
        println!("count = {count}");
        let mut remaining = 10;

        loop {
            println!("remaining = {remaining}");
            if remaining == 9 {
                break;
            }
            if count == 2 {
                break 'counting_up;
            }

            remaining -= 1;
        }

        count += 1;
    }

    // conditional loops with while
    let mut number = 3;
    while number != 0 {
        println!("conditional number is {number}");
        number -= 1;
    }

    let a = [1, 2, 3, 4, 5, 6];
    for elem in a {
        println!("the value of element in a is {elem}");
    }

    // for range
    for number in (1..4) {
        println!("the value of number in range is {number}");
    }
}

