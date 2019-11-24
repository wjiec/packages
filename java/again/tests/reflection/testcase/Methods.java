package tests.reflection.testcase;

public class Methods {

    private String prefix;

    {
        prefix = "Methods::";
    }

    private static String staticPrefix;

    static {
        staticPrefix = "Methods::static::";
    }

    public String publicMethod() {
        return prefix + "publicMethod";
    }

    protected String protectedMethod() {
        return prefix + "protectedMethod";
    }

    private String privateMethod() {
        return prefix + "privateMethod";
    }

    private String privateMethod(int i) {
        return prefix + "privateMethod" + i;
    }

    String packageMethod() {
        return prefix + "packageMethod";
    }

    static public String staticPublicMethod() {
        return staticPrefix + "staticPublicMethod";
    }

    protected static String staticProtectedMethod() {
        return staticPrefix +  "staticProtectedMethod";
    }

    private static String staticPrivateMethod() {
        return staticPrefix + "staticPrivateMethod";
    }

    static String staticPackageMethod() {
        return staticPrefix + "staticPackageMethod";
    }

}
