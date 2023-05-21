package mkhabibullin.core;

import org.testng.Assert;

import javax.annotation.Nullable;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class ApiExecution {

    private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);
            methodsField.setAccessible(true);
            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);
            methodsField.set(null, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public String executeGetJson(String targetURL,
                                 @Nullable String urlParameters,
                                 Header header) throws Exception {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder(targetURL);
        String response;
        if (urlParameters != null) {
            stringBuilder.append("?");
            stringBuilder.append(urlParameters);
        }
        try {
            URL url = new URL(stringBuilder.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            if (header != null && !header.getHeader().isEmpty()) {
                for (Map.Entry<String, String> entry : header.getHeader().entrySet()) {
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            response = readResponseForEmptyBodyRequest(connection);
        } catch (Exception e) {
            if (connection == null) {
                throw new AssertionError();
            }
            throw new Exception(connection.getResponseMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }


    public String executePatchJson(String targetURL,
                                   @Nullable String urlParameters,
                                   @Nullable Header header,
                                   String body) throws Exception {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder(targetURL);
        String response;
        if (urlParameters != null) {
            stringBuilder.append("?");
            stringBuilder.append(urlParameters);
        }
        try {
            allowMethods("PATCH");
            URL url = new URL(stringBuilder.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PATCH");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", "application/json");
            if (header != null && !header.getHeader().isEmpty()) {
                for (Map.Entry<String, String> entry : header.getHeader().entrySet()) {
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            response = readResponseForRequestWithBody(connection, body);
        } catch (Exception e) {
            if (connection == null) {
                throw new AssertionError();
            }
            throw new Exception(connection.getResponseMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    private String readResponseForEmptyBodyRequest(HttpURLConnection connection) throws IOException {
        StringBuilder response;
        InputStream is;
        if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            is = connection.getInputStream();
        } else {
            is = connection.getErrorStream();
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append(System.lineSeparator());
        }
        rd.close();
        return response.toString();
    }

    private String readResponseForRequestWithBody(HttpURLConnection connection, String body) throws IOException {
        StringBuilder response;
        InputStream is;
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(body.getBytes());
        }
        if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            is = connection.getInputStream();
        } else {
            is = connection.getErrorStream();
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append(System.lineSeparator());
        }
        rd.close();
        String json = null;
        boolean isResponseArray = response.toString().startsWith("[");
        boolean isResponseObject = response.toString().startsWith("{");
        if (isResponseArray || isResponseObject) {
            json = response.toString();
        } else if (response.toString().endsWith("]\r\n") || response.toString().endsWith("]") || response.toString().endsWith("]\n")) {
            json = response.substring(response.toString().indexOf("["));
        } else {
            try {
                json = response.substring(response.toString().indexOf("{"));
            } catch (Exception e) {
                String errorMsg = e.getMessage();
                if (errorMsg.contains("out of range")) {
                    Assert.fail(String.format("Incorrect response format - '%s'. Error: '%s'", response, errorMsg));
                }
            }
        }
        return json;
    }

}
