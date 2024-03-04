fn main() {
    // type suffix
    let a = 57u8;
    println!("type suffix: a = {a}");

    // use _ as a visual separator to make number easier to read
    let b = 1_000_000_007;
    println!("visual separator: b = {b}");

    // integer overflow
    let c = 254u8;
    let numbers = [1, 2, 3, 4];
    let d = c + numbers[select_index()];
    println!("integer overflow: c = {c}, d = {d}");
    println!("wrapping_add: c + 1 = {}", c.wrapping_add(2));

    let e = 2.71828f32; // type suffix supported
    println!("float: e = {e}");
    
    let f = '😻'; // 4-bytes
    println!("emoji char: f = {f}, size = {}", std::mem::size_of::<char>());
    
    // tuple
    let tup: (i32, f64, char) = (42, 3.14159, 'π');
    println!("tuple {:?}", tup);
    let (answer, pi, pi_char) = tup;
    println!("pattern matching to destructure: {answer} {pi} {pi_char}");

    let tup1 = ("hello".to_string(), "world".to_string());
    // take_owner(tup1.1); // value borrowed here after partial move
    // println!("after take_owner: tup1 = {:?}", tup1);

    let empty_tup = (); // special name: unit
    println!("empty tuple: {:?}", empty_tup);

    let arr: [i32; 5] = [1, 2, 3, 4, 5];
    println!("array: {:?}", arr);

    let arr_init = [255u8; 12];
    println!("initial value array: {:?}", arr_init);
}

fn select_index() -> usize {
    0
}

fn take_owner(s: String) {
    println!("  take owner for {s}");
}
