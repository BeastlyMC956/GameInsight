package me.beastlymc.gameinsight.controller;

import javafx.scene.text.Font;
import me.beastlymc.gameinsight.utilities.FontUtilities;
import me.beastlymc.gameinsight.utilities.Fonts;

public class ControllerUtilities {
    public static final Font TITLE = FontUtilities.loadFont(Fonts.BOLD, 20),
            TEXT = FontUtilities.loadFont(Fonts.SEMI_BOLD, 15),
            BODY_TEXT = FontUtilities.loadFont(Fonts.MEDIUM, 12),
            MAIN_CONTENT = FontUtilities.loadFont(Fonts.SEMI_BOLD, 25);
}
