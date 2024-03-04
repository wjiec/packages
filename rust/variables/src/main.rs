const HOUR_IN_SECONDS: u32 = 60 * 60;
const THREE_HOURS_IN_SECONDS: u32 = 3 * HOUR_IN_SECONDS;

fn main() {
    let x = 5;
    println!("x = {x}");
    // x = 6; // cannot assign twice to immutable variable
    
    let mut y = 5;
    println!("y = {y}");
    y = 6;
    println!("now y = {y}");

    let day_in_seconds = 8 * THREE_HOURS_IN_SECONDS;
    println!("day_in_seconds = {:?}", day_in_seconds);

    // shadowing
    {
        const COUNT: u32 = 3;
        let z = COUNT * 10;
        println!("z = {z} and constant COUNT = {COUNT}");

        {
            const COUNT: i32 = 4;
            let z = COUNT * 20;
            println!("shadowed z = {z} and shadowed constant COUNT = {COUNT}");
        }

        println!("after shadowed z = {z} and COUNT = {COUNT}");
    }
}
