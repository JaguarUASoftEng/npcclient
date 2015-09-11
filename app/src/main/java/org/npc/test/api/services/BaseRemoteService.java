package org.npc.test.api.services;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.npc.test.api.interfaces.PathElementInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

public abstract class BaseRemoteService {
    protected JSONObject requestSingle(PathElementInterface[] pathElements, UUID id) {
        URL connectionUrl = this.buildPath(pathElements, id.toString());
        String rawResponse = this.performGetRequest(connectionUrl);

        return this.rawResponseToJSONObject(rawResponse);
    }

    protected JSONArray requestMany(PathElementInterface[] pathElements) {
        URL connectionUrl = this.buildPath(pathElements);
        String rawResponse = this.performGetRequest(connectionUrl);

        return this.rawResponseToJSONArray(rawResponse);
    }

    protected JSONObject putSingle(PathElementInterface[] pathElements, JSONObject jsonObject) {
        URL connectionUrl = this.buildPath(pathElements);
        String rawResponse = this.performPostRequest(connectionUrl, jsonObject);

        return this.rawResponseToJSONObject(rawResponse);
    }

    private String performGetRequest(URL connectionUrl) {
        if (connectionUrl == null) {
            return StringUtils.EMPTY;
        }

        HttpURLConnection httpURLConnection = null;
        StringBuilder rawResponse = new StringBuilder();

        try {
            httpURLConnection = (HttpURLConnection) connectionUrl.openConnection();
            httpURLConnection.setRequestMethod(GET_REQUEST_METHOD);
            httpURLConnection.addRequestProperty(ACCEPT_REQUEST_PROPERTY, JSON_PAYLOAD_TYPE);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            char[] buffer = new char[1024];
            int readCharacters = bufferedReader.read(buffer, 0, buffer.length);
            while (readCharacters > 0) {
                rawResponse.append(buffer, 0, readCharacters);
                readCharacters = bufferedReader.read(buffer, 0, buffer.length);
            }

            bufferedReader.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return rawResponse.toString();
    }

    private String performPostRequest(URL connectionUrl, JSONObject jsonObject) {
        if (connectionUrl == null) {
            return StringUtils.EMPTY;
        }

        HttpURLConnection httpURLConnection = null;
        StringBuilder rawResponse = new StringBuilder();

        try {
            httpURLConnection = (HttpURLConnection) connectionUrl.openConnection();
            httpURLConnection.setRequestMethod(PUT_REQUEST_METHOD);
            httpURLConnection.addRequestProperty(ACCEPT_REQUEST_PROPERTY, JSON_PAYLOAD_TYPE);
            httpURLConnection.addRequestProperty(CONTENT_TYPE_REQUEST_PROPERTY, JSON_PAYLOAD_TYPE);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            char[] buffer = new char[1024];
            int readCharacters = bufferedReader.read(buffer, 0, buffer.length);
            while (readCharacters > 0) {
                rawResponse.append(buffer, 0, readCharacters);
                readCharacters = bufferedReader.read(buffer, 0, buffer.length);
            }

            outputStreamWriter.close();
            bufferedReader.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return rawResponse.toString();
    }

    private JSONObject rawResponseToJSONObject(String rawResponse) {
        JSONObject jsonObject = null;

        if (!StringUtils.isBlank(rawResponse)) {
            try {
                jsonObject = new JSONObject(rawResponse.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    private JSONArray rawResponseToJSONArray(String rawResponse) {
        JSONArray jsonArray = null;

        if (!StringUtils.isBlank(rawResponse)) {
            try {
                jsonArray = new JSONArray(rawResponse.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    private URL buildPath(PathElementInterface[] pathElements) {
        return this.buildPath(pathElements, StringUtils.EMPTY);
    }

    private URL buildPath(PathElementInterface[] pathElements, String specificationEntry) {
        StringBuilder completePath = new StringBuilder(BASE_URL);

        for (PathElementInterface pathElement : pathElements) {
            String pathEntry = pathElement.getPathValue();

            if (!StringUtils.isBlank(pathEntry)) {
                completePath.append(pathEntry).append(URL_JOIN);
            }
        }

        if (!StringUtils.isBlank(specificationEntry)) {
            completePath.append(specificationEntry);
        }

        URL connectionUrl;
        try {
            connectionUrl = new URL(completePath.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            connectionUrl = null;
        }

        return connectionUrl;
    }

    private static final String URL_JOIN = "/";
    private static final String GET_REQUEST_METHOD = "GET";
    private static final String PUT_REQUEST_METHOD = "PUT";
    private static final String ACCEPT_REQUEST_PROPERTY = "Accept";
    private static final String JSON_PAYLOAD_TYPE = "application/json";
    private static final String CONTENT_TYPE_REQUEST_PROPERTY = "Content-Type";
    private static final String BASE_URL = "http://192.168.1.68:8080/test/";
}
