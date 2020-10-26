package me.beastlymc.gameinsight.file.files;

import me.beastlymc.gameinsight.file.GameInsightFile;

public class CacheFile extends GameInsightFile {

    public CacheFile(String path, String name) {
        super(path, name);

        this.writeToFile("fv", true);
        this.writeToFile("sad", false);
        this.writeToFile("lol", true);
        this.writeToFile("abc", false);
        this.writeInline(50, "Hej");
        this.writeInline(40, "this is line 40 :D");
        this.writeInline(0, "this is line 0");

//        System.out.println(this.findData("sad"));
//        System.out.println(this.findLineFromData("lol"));
//        System.out.println(this.findDataFromLine(4));
//        System.out.println(this.findData("fv"));
    }
}
