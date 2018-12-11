package io.toro.ojtbe.jimenez.dictionary.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public final class JsonParser {
    private final Gson gson = new Gson();
    private final com.google.gson.JsonParser gsonJsonParser = new com.google.gson.JsonParser();
    public static final JsonParser instance = new JsonParser();

    private JsonParser() {}

    public Map<String, String> jsonToMap(String url){
        JsonElement rawJson = gsonJsonParser.parse(new InputStreamReader((InputStream) getConnection(url)));

        return gson.fromJson(rawJson, Map.class);
    }

    private Object getConnection(String url){
        URL jsonUrl;
        try{
            jsonUrl = new URL(url);
        }
        catch(MalformedURLException e){
            System.out.println("Invalid url passed." + e.getMessage());
            throw new RuntimeException();
        }

        URLConnection connection;
        try{
            connection = jsonUrl.openConnection();
            connection.connect();
        }
        catch(IOException e){
            System.out.println("Cannot open a new connection. " + e.getMessage());
            throw new RuntimeException();
        }

        Object jsonContents;
        try{
            jsonContents = connection.getContent();
        }
        catch(IOException e){
            System.out.println("Cannot get json contents.");
            throw new RuntimeException();
        }

        return jsonContents;
    }
}
