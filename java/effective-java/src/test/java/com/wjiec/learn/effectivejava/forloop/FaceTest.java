package com.wjiec.learn.effectivejava.forloop;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;

public class FaceTest {

    public static void main(String[] args) {
        Collection<Face> faces = EnumSet.allOf(Face.class);
        for (Iterator<Face> i = faces.iterator(); i.hasNext();) {
            for (Iterator<Face> j = faces.iterator(); j.hasNext(); ) {
                System.out.println(i.next() + " " + j.next());
            }
        }
    }

}
