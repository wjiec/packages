package com.wjiec.learn.effectivejava.singleton;

import java.io.*;

public class UnserializeAttack {

    public static void main(String[] args) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.out.println(Lover.INSTANCE);
            try (ObjectOutputStream stream = new ObjectOutputStream(output)) {
                stream.writeObject(Lover.INSTANCE);
            }

            byte[] data = output.toByteArray();
            try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {
                try (ObjectInputStream stream = new ObjectInputStream(input)) {
                    Lover lover = (Lover) stream.readObject();
                    System.out.println(lover);
                    System.out.println(lover.getRingId());
                }
            }
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.out.println(PerfectLover.INSTANCE);
            try (ObjectOutputStream stream = new ObjectOutputStream(output)) {
                stream.writeObject(PerfectLover.INSTANCE);
            }

            byte[] data = output.toByteArray();
            try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {
                try (ObjectInputStream stream = new ObjectInputStream(input)) {
                    PerfectLover lover = (PerfectLover) stream.readObject();
                    System.out.println(lover);
                    System.out.println(lover.getRingId());
                }
            }
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
