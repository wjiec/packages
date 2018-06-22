package generic;

public class WildcardGeneric {
    static class BaseClass {
        int getSize() {
            return 1;
        }
    }

    static class DerivedClass1 extends BaseClass {
        @Override
        int getSize() {
            return 11;
        }
    }

    static class DerivedClass2 extends BaseClass {
        @Override
        int getSize() {
            return 12;
        }
    }

    static class GenericClass<T> {
        private T value;

        GenericClass(T value) {
            this.value = value;
        }

        T getValue() {
            return value;
        }
    }

    int getResult(GenericClass<? extends BaseClass> p) {
        return p.getValue().getSize();
    }

    public static void main(String[] args) {
        GenericClass<DerivedClass1> sample1 = new GenericClass<>(new DerivedClass1());
    }
}
