package com.github.wjiec.xml;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class XPathTest {

    public static void main(String[] args) {
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath path = factory.newXPath();

            DocumentBuilder builder = Builder.simple();
            Document document = builder.parse(Books.asFile());

            System.out.println(path.evaluate("/bookstore/book/author/text()", document));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
