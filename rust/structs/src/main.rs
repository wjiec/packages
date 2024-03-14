#[derive(Debug)]
struct User {
    active: bool,
    username: String,
    email: String,
    login_count: u32,
}

// rust also supports structs that look like similar
// to tuples, called tuple structs
struct Color(u8, u8, u8);

// unit-like structs without any fields
//
//  unit-like structs can be useful when you need to implement
//  a trait on same type but don't have any data that you want
//  to store in the type itself.
struct AlwaysEqual;


fn main() {
    let user = User {
        active: true,
        // we don't have to specify the fields in the same order
        // in which we declared them in struct.
        email: String::from("user@example.com"),
        username: String::from("user"),
        login_count: 1u32,
    };
    println!("the value of user is {user:?} and email is {}", user.email);

    {
        let user = build_user(String::from("foo"), String::from("foo@example.com"));
        println!("the value of built user is {user:?}");

        let user1 = from_user(String::from("bar@example.com"), user);
        println!("the value of user1 is {user1:?}");
    }

    {
        let mut user = build_user(String::from("foo"), String::from("foo@example.com"));
        let r = take_ownership_from_user(&user); // immutable borrow occurs here
        
        user.username.push_str("."); // mutable borrow occurs here
        //println!("the value of r is '{r}'");
    }
}

fn build_user(username: String, email: String) -> User {
    User {
        // using the field init shorthand
        username,
        email,
        active: true,
        login_count: 1,
    }
}

fn from_user(email: String, user: User) -> User {
    User {
        email,
        // the remaining fields not explicitly set should have
        // same value as the fields in the given user instance.
        //
        // ..syntax must come last
        ..user/*,*/ // error: cannot use a comma after the base struct
    }
}

fn take_ownership_from_user(user: &User) -> &String {
    if user.login_count % 2 == 0 {
        &user.email
    } else {
        &user.username
    }
}
