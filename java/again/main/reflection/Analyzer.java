package main.reflection;

import javax.print.DocFlavor;
import javax.swing.plaf.basic.BasicTreeUI;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Analyzer {

    private ArrayList<Object> visited;

    public Analyzer() {
        visited = new ArrayList<>();
    }

    public String analysis(Object target) {
        return analysis(target, 0);
    }

    private String analysis(Object obj, int indent) {
        if (obj == null) {
            return "null";
        } else if (obj.getClass() == String.class) {
            return (String)obj;
        } else if (visited.contains(obj)) {
            return "...";
        }
        visited.add(obj);

        Class target = obj.getClass();
        if (target.isArray()) {
            StringBuilder builder = new StringBuilder();
            builder.append("[]").append(target.getComponentType().getName()).append("{\n");
            for (int i = 0; i < Array.getLength(obj); ++i) {
                Object item = Array.get(obj, i);
                builder.append(getIndent(indent)).append(i).append(". := ");
                if (item.getClass().isPrimitive()) {
                    builder.append(item);
                } else {
                    builder.append(analysis(item, indent + 1));
                }
            }
            return builder.append("}").toString();
        }
        return analysisParent(obj, target, indent, 0);
    }

    private String analysisParent(Object obj, Class target, int indent, int depth) {
        StringBuilder builder = new StringBuilder();
        if (depth != 0) {
            builder.append("\t".repeat(indent + depth)).append("#-");
        }

        builder.append(target.getName()).append("{").append(target.getDeclaredFields().length != 0 ? "\n" : "");
        for (Field field : target.getDeclaredFields()) {
            builder.append(getIndent(indent + depth)).append(field.getName()).append(" := ");

            try {
                field.setAccessible(true);
                if (field.getType().isPrimitive()) {
                    builder.append(field.get(obj));
                } else {
                    builder.append(analysis(field.get(obj), indent + depth + 1));
                }
                builder.append("\n");
            } catch (IllegalAccessException ignored) {}
        }

        if (target.getSuperclass() != null) {
            builder.append(analysisParent(obj, target.getSuperclass(), indent, depth + 1)).append("\n");
        }

        if (target.getDeclaredFields().length != 0) {
            builder.append(getIndent(indent + depth - 1));
        }
        return builder.append("}").toString();
    }

    private String getIndent(int indent) {
        return "\t".repeat(indent + 1);
    }

}
