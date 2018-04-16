import java.util.Date;

public class BadReturnValue {
    public static void main(String[] args) {
        Person person = new Person();
        Date birthDay = person.getBirthDay();

        birthDay = person.getBirthdayGood();
        birthDay.setTime(0);
        System.out.println(person.getBirthDay());

        birthDay = person.getBirthDay();
        birthDay.setTime(0);
        System.out.println(person.getBirthDay());
    }
}

class Person {
    private Date birthday;

    Person() {
        this.birthday = new Date(1523891055);
    }

    public Date getBirthDay() {
        return this.birthday;
    }

    public Date getBirthdayGood() {
        return (Date)this.birthday.clone();
    }
}