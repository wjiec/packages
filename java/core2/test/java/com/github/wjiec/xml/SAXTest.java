package com.github.wjiec.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXTest {

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(Books.asFile(), new DefaultHandler(){
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("author")) {
                        for (int i = 0; i < attributes.getLength(); ++i) {
                            if (attributes.getLocalName(i).equals("age")) {
                                int age = Integer.parseInt(attributes.getValue(i));
                                System.out.printf("%s: %d\n", localName, age);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
