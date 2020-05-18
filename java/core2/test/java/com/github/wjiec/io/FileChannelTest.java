package com.github.wjiec.io;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.zip.CRC32;

public class FileChannelTest {

    public static void main(String[] args) {
        try (FileChannel channel = FileChannel.open(Paths.get("src.bz"))) {
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

            CRC32 crc = new CRC32();
            for (int i = 0; i < channel.size(); i++) {
                crc.update(buffer.get());
            }
//            crc.update(buffer);
            System.out.println(crc.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
