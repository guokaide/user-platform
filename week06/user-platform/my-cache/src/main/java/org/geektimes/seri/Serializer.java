package org.geektimes.seri;

public interface Serializer {

    byte[] serialize(Object value) throws Exception;

    <T> T deserialize(byte[] bytes) throws Exception;
}
