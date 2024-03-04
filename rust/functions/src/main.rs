fn main() {
    println!("Hello, world!");

    another_function();
    with_parameter(1); // 1 is the argument
    print_labeled_measurement(5, 'h');

    let ans = ending_expression(11);
    println!("The ending expression result is {ans}");

    let y = {
        let x = 3;
        // expressions do not include ending semicolons
        x * x
    };
    println!("The value of expression result y is {y}");
}

fn another_function() {
    println!("Another function.");
}

fn with_parameter(x: i32) { // x is a parameter
    println!("With parameter: the value of x is: {x}");
}

fn print_labeled_measurement(value: i32, unit_label: char) {
    println!("The measurement is: {value}{unit_label}");
}

fn ending_expression(v: i32) -> i32 {
    v * v
}

