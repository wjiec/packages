import java.util.Date;
import java.util.Objects;

public class PerfectEquals {
    private Date createAt;

    private String name;

    private PerfectEquals(String name) {
        this.name = name;
        this.createAt = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        // the same object
        if (this == obj) {
            return true;
        }

        // check other object null
        if (obj == null) {
            return false;
        }

        // check class name [IN CHILD REQUIRED]
        if (getClass() != obj.getClass()) {
            return false;
        }

        // check parent class [IN PARENT REQUIRED]
        if (!(obj instanceof PerfectEquals)) {
            return false;
        }

        PerfectEquals other = (PerfectEquals) obj;
        return Objects.equals(name, other.name) && Objects.equals(createAt, other.createAt);
    }

    public static void main(String[] args) throws InterruptedException {
        PerfectEquals object1 = new PerfectEquals("Jack");
        PerfectEquals object2 = new PerfectEquals("John");
        System.out.println(object1.equals(object2));

        PerfectEquals copyObject1 = object1;
        System.out.println(object1.equals(copyObject1));

        PerfectEquals newObject1 = new PerfectEquals("Jack");
        System.out.println(object1.equals(newObject1));

        Thread.sleep(1000);
        PerfectEquals newObject2 = new PerfectEquals("Jack");
        System.out.println(object1.equals(newObject2));
    }
}
