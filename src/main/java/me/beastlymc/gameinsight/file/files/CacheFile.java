package me.beastlymc.gameinsight.file.files;

import me.beastlymc.gameinsight.file.GameInsightFile;

public class CacheFile extends GameInsightFile {

    public CacheFile(String path, String name) {
        super(path, name);

        this.writeToFile("fv", true);
        this.writeToFile("sad", false);
        this.writeToFile("lol", true);
        this.writeToFile("abc", false);

        System.out.println(this.findData("sad"));
        System.out.println(this.findLineFromData("lol"));
        System.out.println(this.findDataFromLine(4));
    }
}
