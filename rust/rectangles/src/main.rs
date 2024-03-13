fn main() {
    {
        let (width, height) = (30u32, 50u32);
        println!("the area of the rectangle is {}.", area(width, height));
    }

    {
        let rect = (30u32, 30u32);
        println!("the area of the rectangle is {}.", area_tuple(rect));
    }

    {
        let rect = Rectangle { width: 20, height: 10 };
        println!("the area of the rectangle is {}.", area_struct(&rect));
    }
}

fn area(width: u32, height: u32) -> u32 {
    width * height
}

fn area_tuple(rect: (u32, u32)) -> u32 {
    rect.0 * rect.1
}

struct Rectangle {
    width: u32,
    height: u32,
}

fn area_struct(rect: &Rectangle) -> u32 {
    rect.width * rect.height
}

