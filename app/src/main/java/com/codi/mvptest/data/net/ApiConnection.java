package com.codi.mvptest.data.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by Codi on 2015/2/26.
 */
public class ApiConnection implements Callable<String> {

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

    public static final String REQUEST_METHOD_GET = "GET";

    private URL url;
    private String requestVerb;
    private int responseCode = 0;
    private String response = "";

    private ApiConnection(String url, String requestVerb) throws MalformedURLException {
        this.url = new URL(url);
        this.requestVerb = requestVerb;
    }

    public static ApiConnection createGET(String url) throws MalformedURLException {
        return new ApiConnection(url, REQUEST_METHOD_GET);
    }

    public String requestSyncCall() {
        connectToApi();
        return response;
    }

    private void connectToApi() {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            setupConnection(connection);

            responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                response = getStringFromInputStream(connection.getInputStream());
            } else {
                response = getStringFromInputStream(connection.getErrorStream());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String getStringFromInputStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    private void setupConnection(HttpURLConnection connection) throws ProtocolException {
        if (connection != null) {
            connection.setRequestMethod(requestVerb);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setRequestProperty(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON);
        }
    }

    @Override
    public String call() throws Exception {
        return requestSyncCall();
    }
}
