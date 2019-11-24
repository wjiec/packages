package tests.reflection;

import tests.reflection.testcase.Methods;

import java.lang.reflect.Method;

public class MethodInvokeTest {

    public static void main(String[] args) {
        Methods m = new Methods();
        Class mcl = m.getClass();

        try {
            Method publicMethod = mcl.getMethod("publicMethod");
            System.out.println(publicMethod.invoke(m));

//            Method protectedMethod = mcl.getMethod("protectedMethod");
//            System.out.println(protectedMethod.invoke(m));

//            Method privateMethod1 = mcl.getMethod("privateMethod");
//            System.out.println(privateMethod1.invoke(m));

//            Method privateMethod2 = mcl.getMethod("privateMethod", int.class);
//            System.out.println(privateMethod2.invoke(m, 2));

//            Method packageMethod = mcl.getMethod("packageMethod");
//            System.out.println(packageMethod.invoke(m));

            Method staticPublicMethod = mcl.getMethod("staticPublicMethod");
            System.out.println(staticPublicMethod.invoke(m));

//            Method staticProtectedMethod = mcl.getMethod("staticProtectedMethod");
//            System.out.println(staticProtectedMethod.invoke(m));

//            Method staticPrivateMethod = mcl.getMethod("staticPrivateMethod");
//            System.out.println(staticPrivateMethod.invoke(m));

//            Method staticPackageMethod = mcl.getMethod("staticPackageMethod");
//            System.out.println(staticPackageMethod.invoke(m));

            MethodInvokeTest mit = new MethodInvokeTest();
            Method selfPackageMethod = mit.getClass().getMethod("selfPackageMethod");
            System.out.println(selfPackageMethod.invoke(mit));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String selfPackageMethod() {
        return "selfPackageMethod";
    }

}
