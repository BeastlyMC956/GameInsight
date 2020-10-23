package me.beastlymc.gameinsight.utilities;

import javafx.scene.text.Font;
import me.beastlymc.gameinsight.GameInsight;

/**
 * <b>A Utility Class for everything concerning [{@link Fonts}]</b>
 *
 * @author BeastlyMC956
 */
public class FontUtilities {

    private static double fontSize;
    private static String location;

    public FontUtilities(Fonts fonts, double fontSize) {
        FontUtilities.fontSize = fontSize;
        location = fonts.getLocation();
        Font.loadFont(fonts.getLocation(), fontSize);
    }

    /**
     * <p>Simple loading of a font from {@link Fonts}</p>
     * @param fonts The font to load
     * @param fontSize The size of the font to use
     * @return The loaded font
     */
    public static Font loadFont(Fonts fonts, double fontSize) {
        return Font.loadFont(GameInsight.class.getResource(fonts.getLocation()).toExternalForm(), fontSize);
    }

    @SuppressWarnings({"unused"})
    public static double getFontSize() {
        return fontSize;
    }

    @SuppressWarnings({"unused"})
    public static String getLocation() {
        return location;
    }
}
