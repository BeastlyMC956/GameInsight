package me.beastlymc.gameinsight.networking;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>A Utility Class for everything concerning [{@link HttpURLConnection}]
 *
 * @author BeastlyMC956
 */
public class HTMLUtilities {

    private static final List<String> KEYS = new ArrayList<>();
    private static final List<Object> VALUES = new ArrayList<>();

    /**
     * <b>Sends a request to a website with JSON</b>
     * <p>Gets response from website and writes in json format</p>
     * <p>Writes down the JSON data as a {@link List} for both values & keys </p>
     *
     * @param location  the URL
     * @param jsonArray if the response has an JSON array
     */
    public static void getResponseJSON(String location, boolean jsonArray) {
        try {
            KEYS.clear();
            VALUES.clear();
            URL url = new URL(location);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                if (jsonArray) {

                    String str;
                    while ((str = br.readLine()) != null)
                        System.out.println(str);

                    return;
                }
                String st = "";
                StringBuilder stringBuilder = new StringBuilder(st);
                while ((st = br.readLine()) != null)
                    stringBuilder.append(st);
                System.out.println(stringBuilder.toString());

                JSONObject object = new JSONObject(stringBuilder.toString());

                Map<String, Object> map = object.toMap();

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    KEYS.add(entry.getKey());
                    VALUES.add(entry.getValue());
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getKeys() {
        return KEYS;
    }

    public static List<Object> getValues() {
        return VALUES;
    }

    public static String getAllLists() {
        return KEYS + " : " + VALUES;
    }
}
