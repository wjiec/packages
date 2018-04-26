package reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ObjectAnalyzer {

    private ArrayList<Object> visited;

    public ObjectAnalyzer() {
        visited = new ArrayList<>();
    }

    public String toString(Object obj) {
        if (obj == null) {
            return "null";
        }

        if (visited.contains(obj)) {
            return "...";
        }
        visited.add(obj);

        Class classObject = obj.getClass();
        if (classObject == String.class) {
            return (String) obj;
        } else if (classObject.isArray()) {
            String componentStringify = "";

            Class componentClassObject = classObject.getComponentType();
            for (int i = 0; i < Array.getLength(obj); i++) {
                componentStringify += i != 0 ? ", " : "";

                Object value = Array.get(obj, i);
                if (componentClassObject.isPrimitive()) {
                    componentStringify += value;
                } else {
                    componentStringify += toString(value);
                }
            }

            return String.format("%s[] {%s}", componentClassObject, componentStringify);
        }

        String objectStringify = classObject.getName();
        do {
            String fieldsStringify = "";

            Field[] fields = classObject.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (!Modifier.isStatic(field.getModifiers())) {
                    fieldsStringify += fieldsStringify.length() == 0 ? "" : ", ";

                    try {
                        Object value = field.get(obj);
                        fieldsStringify += String.format("%s=%s",
                            field.getName(),
                            field.getType().isPrimitive() ? value : toString(value)
                        );
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            objectStringify += String.format("[%s]", fieldsStringify);
            classObject = classObject.getSuperclass();
        } while (classObject != null);
        return objectStringify;
    }
}
