package org.geektimes.seri;

import java.io.*;

public class DefaultSerializer implements Serializer {

    public byte[] serialize(Object value) throws Exception {
        byte[] bytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            // Key -> byte[]
            objectOutputStream.writeObject(value);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw e;
        }
        return bytes;
    }

    public <T> T deserialize(byte[] bytes) throws Exception {
        if (bytes == null) {
            return null;
        }
        T value = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            // byte[] -> Value
            value = (T) objectInputStream.readObject();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return value;
    }

}
