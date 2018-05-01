package interfaces;

public class PersonSample implements Person, TheSameToPerson {

    public static void main(String[] args) {
        PersonSample person = new PersonSample();
        System.out.println(person.getName());
        System.out.println(person.getAge());
        System.out.println(person.getAge(1));
    }

    @Override
    public String getName() {
        return Person.super.getName();
    }

    @Override
    public int getAge(int additional) {
        return 0;
    }

    @Override
    public int getAge() {
        return 0;
    }
}
