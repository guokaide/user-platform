package org.geektimes.rest.demo;

import org.apache.commons.io.IOUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class RestClientPostDemo {
    public static void main(String[] args) throws Throwable {
//        doPostWithHttpURLConnection();
        Client client = ClientBuilder.newClient();
        String body = "{\"name\": \"kai\", \"age\": 1}";
        Entity<String> entity = Entity.entity(body, MediaType.APPLICATION_JSON_TYPE);
        Response response = client
                .target("http://127.0.0.1:8080/hi")              // WebTarget
                .request()                                             // Invocation.Builder
                .post(entity);                                         //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);

    }

    private static void doPostWithHttpURLConnection() throws Throwable {
        URI uri = new URI("http://127.0.0.1:8080/hi");
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        String body = "{\"name\": \"kai\", \"age\": 1}";
        Entity<String> entity = Entity.entity(body, MediaType.APPLICATION_JSON_TYPE);
        byte[] input = entity.getEntity().getBytes("UTF-8");
        connection.setRequestProperty("Content-Type", entity.getMediaType().getType() + "/" + entity.getMediaType().getSubtype());
        connection.setRequestProperty("Content-Length", String.valueOf(input.length));
        connection.getOutputStream().write(input);
        try (InputStream inputStream = connection.getInputStream()) {
            System.out.println(IOUtils.toString(inputStream, "UTF-8"));
        }
        // 关闭连接
        connection.disconnect();
    }

}
