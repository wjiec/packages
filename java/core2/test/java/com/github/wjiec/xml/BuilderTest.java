package com.github.wjiec.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BuilderTest {

    public static void main(String[] args) throws ParserConfigurationException {
        try (InputStream stream = Books.asStream()) {
            DocumentBuilder builder = Builder.simple();
            doSomething(builder.parse(stream));
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }

        System.out.println();

        try {
            File file = Books.asFile();
            DocumentBuilder builder = Builder.simple();
            doSomething(builder.parse(file));
        } catch (IOException | SAXException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void doSomething(Document document) {
        System.out.println(document);
        System.out.println(document.getDocumentElement().getTagName());
        System.out.println();
        System.out.println(toString(document.getDocumentElement(), 0));;
    }

    private static String toString(Element node, int level) {
        StringBuffer buffer = padding(level);

        // name(attr=val): data\n
        buffer.append(node.getTagName()).append("(");
        NamedNodeMap attrs = node.getAttributes();
        for (int i = 0; i < attrs.getLength(); ++i) {
            if (i != 0) {
                buffer.append(",");
            }
            buffer.append(attrs.item(i).getNodeName()).append("=").append(attrs.item(i).getNodeValue());
        }
        buffer.append("): \n");

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            buffer.append(toString(children.item(i), level + 1));
        }

        return buffer.toString();
    }

    private static String toString(CharacterData data, int level) {
        if (data instanceof Comment) {
            return "";
        }

        String s = padding(level).append(data.getData()).append("\n").toString();
        if (Pattern.compile("^\\s+$").matcher(s).find()) {
            return "";
        }
        return s;
    }

    private static String toString(Node node, int level) {
        if (node instanceof Element) {
            return toString((Element)node, level);
        } else if (node instanceof CharacterData) {
            return toString((CharacterData)node, level);
        }
        return padding(level).append(node.getClass().getName()).append("\n").toString();
    }

    private static StringBuffer padding(int level) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < level; i++) {
            buffer.append("  ");
        }
        return buffer;
    }

}
