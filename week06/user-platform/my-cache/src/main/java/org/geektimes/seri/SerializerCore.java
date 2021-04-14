package org.geektimes.seri;

public class SerializerCore {

    public static byte[] serialize(Object value) {
        try {
            return serialize(value, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] serialize(Object value, Serializer serializer) throws Exception {
        serializer = serializer == null ? new DefaultSerializer() : serializer;
        return serializer.serialize(value);
    }

    public <T> T deserialize(byte[] bytes) {
        try {
            return deserialize(bytes, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] bytes, Serializer serializer) throws Exception {
        serializer = serializer == null ? new DefaultSerializer() : serializer;
        return serializer.deserialize(bytes);
    }

}
