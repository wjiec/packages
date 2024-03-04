use std::io::Write;

fn main() {
    print!("Generate the nth fibonacci number: ");
    std::io::stdout().flush().unwrap();

    let mut number = String::new();
    std::io::stdin().read_line(&mut number).unwrap();
    let number: u32 = number.trim().parse().unwrap();

    let (mut a, mut b) = (0, 1);
    for idx in 1..(number + 1) {
        let c = a + b;
        println!("{idx}: {b}");
        a = b;
        b = c;
    }
}
