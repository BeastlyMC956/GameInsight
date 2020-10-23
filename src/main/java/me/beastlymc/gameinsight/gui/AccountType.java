package me.beastlymc.gameinsight.gui;

import javafx.scene.paint.Color;

public enum AccountType {
    BASIC("Basic", 35, Color.rgb(0,100,150)),
    SUPPORTER("Supporter", 63, Color.rgb(0,150,100)),
    PRO("Pro", 25, Color.rgb(175,100,0)),
    ADMIN("Admin", 39, Color.rgb(170,0,0));

    String name;
    int size;
    Color background;

    AccountType(String name, int size, Color background) {
        this.name = name;
        this.background = background;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Color getBackground() {
        return background;
    }
}
