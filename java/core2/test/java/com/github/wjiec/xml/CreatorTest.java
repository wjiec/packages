package com.github.wjiec.xml;

import com.github.wjiec.stream.AliceInWonderLand;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class CreatorTest {

    public static void main(String[] args) {
        try {
            Document document = Builder.simple().newDocument();
            Element books = document.createElement("books");

            Element book = document.createElement("book");

            Element author = document.createElement("author");
            author.setTextContent(AliceInWonderLand.words().findAny().orElse("UNKNOWN"));
            author.setAttribute("age", String.valueOf((int)(Math.random() * 50)));
            book.appendChild(author);

            Element title = document.createElement("title");
            title.setTextContent(AliceInWonderLand.words().findAny().orElse("UNKNOWN"));
            book.appendChild(title);

            books.appendChild(book.cloneNode(true));
            books.appendChild(book.cloneNode(true));
            books.appendChild(book.cloneNode(true));

            document.appendChild(books);

            byLss(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void byLss(Document document) {
        DOMImplementation impl = document.getImplementation();
        DOMImplementationLS implLs = (DOMImplementationLS)impl.getFeature("LS", "3.0");
        LSSerializer serializer = implLs.createLSSerializer();
        serializer.getDomConfig().setParameter("format-pretty-print", true);
        System.out.println(serializer.writeToString(document));
    }

}
