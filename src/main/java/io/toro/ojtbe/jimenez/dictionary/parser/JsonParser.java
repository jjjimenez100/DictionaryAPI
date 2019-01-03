package io.toro.ojtbe.jimenez.dictionary.parser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public final class JsonParser {
    private final Gson gson;
    private final Logger logger;
    private final com.google.gson.JsonParser gsonJsonParser;

    public static final JsonParser instance = new JsonParser();

    private JsonParser() {
        gsonJsonParser = new com.google.gson.JsonParser();
        gson = new Gson();
        logger = LoggerFactory.getLogger(JsonParser.class);
    }

    public Map<String, String> jsonToMap(String url){
        JsonElement rawJson = gsonJsonParser.parse(new InputStreamReader((InputStream) getConnection(url)));

        return gson.fromJson(rawJson, Map.class);
    }

    private Object getConnection(String url){
        URL jsonUrl;
        String message;
        try{
            jsonUrl = new URL(url);
        } catch(MalformedURLException e){
            message = "Invalid url passed." + e.getMessage();
            logger.warn(message);

            throw new RuntimeException(message);
        }

        URLConnection connection;
        try{
            connection = jsonUrl.openConnection();
            connection.connect();
        } catch(IOException e){
            message ="Failed to open a new connection. " + e.getMessage();
            logger.warn(message);

            throw new RuntimeException();
        }

        Object jsonContents;
        try{
            jsonContents = connection.getContent();
        } catch(IOException e){
            message = "Failed to get json contents.";
            logger.warn(message);

            throw new RuntimeException(message);
        }

        return jsonContents;
    }
}
