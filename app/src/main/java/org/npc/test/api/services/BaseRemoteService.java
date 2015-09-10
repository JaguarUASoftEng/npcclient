package org.npc.test.api.services;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.npc.test.api.interfaces.PathElementInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

public abstract class BaseRemoteService {
    protected JSONObject requestSingle(PathElementInterface[] pathElements, UUID id) {
        JSONObject jsonObject = null;
        HttpURLConnection httpURLConnection = null;
        StringBuilder rawResponse = new StringBuilder();

        try {
            httpURLConnection = (HttpURLConnection) this.buildPath(pathElements, id.toString()).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.addRequestProperty("Accept", "application/json");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            char[] buffer = new char[1024];
            int readCharacters = bufferedReader.read(buffer, 0, buffer.length);

            while (readCharacters > 0) {
                rawResponse.append(buffer, 0, readCharacters);
                readCharacters = bufferedReader.read(buffer, 0, buffer.length);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        if (rawResponse.length() > 0) {
            try {
                jsonObject = new JSONObject(new JSONTokener(rawResponse.toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    private URL buildPath(PathElementInterface[] pathElements) throws MalformedURLException {
        return this.buildPath(pathElements, StringUtils.EMPTY);
    }

    private URL buildPath(PathElementInterface[] pathElements, String specificationEntry) throws MalformedURLException {
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

        return new URL(completePath.toString());
    }

    private static final String URL_JOIN = "/";
    private static final String BASE_URL = "http://192.168.1.79:4568/npctest/";
}
