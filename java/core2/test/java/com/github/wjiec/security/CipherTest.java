package com.github.wjiec.security;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CipherTest {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(new SecureRandom());

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, generator.generateKey());

        byte[] inBytes = new byte[cipher.getBlockSize()];
        byte[] outBytes = new byte[cipher.getOutputSize(cipher.getBlockSize())];

        DataInputStream stream = new DataInputStream(new ByteArrayInputStream("I am a secret text".getBytes()));
        while (stream.read(inBytes) == cipher.getBlockSize()) {
            cipher.update(inBytes, 0, cipher.getBlockSize(), outBytes);
        }
        cipher.doFinal();
    }

}
