use std::collections::HashMap;

pub fn median(v: &Vec<i32>) -> Option<i32> {
    let mut sorted = Vec::new();
    for elem in v {
        sorted.push(*elem);
    }
    sorted.sort();

    sorted.get(sorted.len() / 2).copied()
}

pub fn most_often(v: &Vec<i32>) -> Option<i32> {
    if v.len() == 0 {
        return None;
    }

    let mut ans = 0;
    let mut cnt = 0;
    let mut m = HashMap::new();
    for &elem in v {
        let count = m.entry(elem).or_insert(0);
        *count += 1;

        if *count > cnt {
            cnt = *count;
            ans = elem;
        }
    }

    Some(ans)
}

pub fn pig_latin(s: &str) -> String {
    if s.len() == 0 {
        return "".to_string();
    }

    let chars: Vec<char> = s.chars().collect();
    match chars[0] {
        'a' | 'e' | 'i' | 'o' | 'u' | 'A' | 'E' | 'I' | 'O' | 'U' => s.to_string() + "-hay",
        c => String::from_iter(&chars[1..]) + "-" + &c.to_string() + "ay",
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn median_works() {
        assert_eq!(median(&vec![1, 2, 3]), Some(2));
        assert_eq!(median(&vec![1, 3, 2]), Some(2));
    }

    #[test]
    fn most_often_works() {
        assert_eq!(most_often(&vec![]), None);
        assert_eq!(most_often(&vec![1, 1, 2, 3, 2, 2]), Some(2));
    }

    #[test]
    fn pig_latin_works() {
        assert_eq!(pig_latin("first"), "irst-fay");
        assert_eq!(pig_latin("apple"), "apple-hay");
    }
}

