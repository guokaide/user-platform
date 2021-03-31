package org.geektimes.rest.demo;

import org.apache.commons.io.IOUtils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class RestClientPostDemo {
    public static void main(String[] args) throws Throwable {
        doPostWithHttpURLConnection();
    }

    private static void doPostWithHttpURLConnection() throws Throwable {
        URI uri = new URI("http://127.0.0.1:8080/hi");
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        String body = "{\"name\": \"kai\", \"age\": 1}";
        Entity<String> entity = Entity.entity(body, MediaType.APPLICATION_JSON_TYPE);
        connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = entity.getEntity().getBytes("UTF-8");
            outputStream.write(input, 0, input.length);
        }
        try (InputStream inputStream = connection.getInputStream()) {
            System.out.println(IOUtils.toString(inputStream, "UTF-8"));
        }
        // 关闭连接
        connection.disconnect();
    }

}
