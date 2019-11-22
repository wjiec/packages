package main.reflection;

import java.lang.reflect.*;
import java.sql.BatchUpdateException;
import java.util.StringJoiner;

public class Dumper {

    private Class target;

    public Dumper(String name) throws ClassNotFoundException {
        this.target = Class.forName(name);
    }

    public Dumper(Class target) {
        this.target = target;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getHeader()).append(" {\n");
        builder.append(getFields()).append("\n");
        builder.append(getConstructors()).append("\n");
        builder.append(getMethod()).append("}\n");

        return builder.toString();
    }

    private String getHeader() {
        StringBuilder builder = new StringBuilder();
        builder.append(getModifier(target))
            .append("class").append(" ").append(target.getName());

        Class superClass = target.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            builder.append("extends").append(" ").append(superClass.getName());
        }

        return builder.toString();
    }

    private String getFields() {
        StringBuilder builder = new StringBuilder();
        for (Field field : target.getDeclaredFields()) {
            builder.append("\t").append(getModifier(field))
                .append(field.getType().getName()).append(" ")
                .append(field.getName()).append(";\n");
        }
        return builder.toString();
    }

    private String getConstructors() {
        StringBuilder builder = new StringBuilder();
        for (Constructor constructor : target.getDeclaredConstructors()) {
            builder.append("\t").append(getSignature(constructor)).append(";\n");
        }
        return builder.toString();
    }

    private String getMethod() {
        StringBuilder builder = new StringBuilder();
        for (Method method : target.getMethods()) {
            builder.append("\t").append(getSignature(method)).append(";\n");
        }
        return builder.toString();
    }

    private String getModifier(Member member) {
        if (member.getModifiers() != 0) {
            return Modifier.toString(member.getModifiers()) + " ";
        }
        return "";
    }

    private String getModifier(Class c) {
        if (c.getModifiers() != 0) {
            return Modifier.toString(c.getModifiers()) + " ";
        }
        return "";
    }

    private String getSignature(Executable executable) {
        StringBuilder builder = new StringBuilder(getModifier(executable));
        if (executable instanceof Method) {
            Method method = (Method)executable;
            builder.append(method.getReturnType().getName()).append(" ");
        }

        builder.append(executable.getName())
            .append("(").append(getParams(executable.getParameters())).append(")");
        return builder.toString();
    }

    private String getParams(Parameter[] parameters) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Parameter parameter : parameters) {
            joiner.add(parameter.getType().getName());
        }
        return joiner.toString();
    }

}
