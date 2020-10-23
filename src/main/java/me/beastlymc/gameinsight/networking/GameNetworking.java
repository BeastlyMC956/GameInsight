package me.beastlymc.gameinsight.networking;

import me.beastlymc.gameinsight.file.FileUtilities;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;

/**
 * <b>Main Networking Class for Games</b>
 *
 * <p>Able to write and Read JSON files from websites</p>
 *
 * @author BeastlyMC956
 */
public class GameNetworking {

    String name, id, summonerId;

    public GameNetworking() {
        riotInit();
    }

    public void riotInit() {
        name = "BeastlyMC956";
        id = "RGAPI-4e4f2fc0-5a80-41e3-b124-acb6e673eeb2";
        HTMLUtilities.getResponseJSON("https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + id, false);
        File cache = FileUtilities.getCache().toFile();

        JSONObject obj = new JSONObject();

        for (int i = 0; i < HTMLUtilities.getKeys().size(); i++)
            obj.put(HTMLUtilities.getKeys().get(i), HTMLUtilities.getValues().get(i));

        try {
            PrintWriter pw = new PrintWriter(cache);
            pw.write(obj.toString(1));

            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        summonerId = obj.get("id").toString();

        HTMLUtilities.getResponseJSON("https://eun1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId + "?api_key=" + id, true);
        for (int i = 0; i < HTMLUtilities.getKeys().size(); i++)
            obj.put(HTMLUtilities.getKeys().get(i), HTMLUtilities.getValues().get(i));

        try {
            PrintWriter pw = new PrintWriter(cache);
            pw.write(obj.toString(1));

            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
