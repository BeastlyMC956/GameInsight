package me.beastlymc.gameinsight.gui;

import javafx.scene.paint.Color;
import me.beastlymc.gameinsight.GameInsight;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Collection of Themes</b>
 *
 * <p>Dynamic theme creation</p>
 * <p>Contains few themes</p>
 *
 * @author BeastlyMC956
 */
public enum Theme {

    /**
     * <b>Dark Material Theme</b>
     * <p>Simple Dark Theme</p>
     */
    DARK(true,
            Color.rgb(0, 199, 206),
            Color.rgb(0, 0, 0),
            Color.rgb(255, 255, 255),
            Color.rgb(206, 0, 199),
            Color.rgb(207, 102, 121)),
    /**
     * <b>Light Material Theme</b>
     * <p>Simple Light Theme</p>
     */
    LIGHT(false,
            Color.rgb(0, 199, 206),
            Color.rgb(255, 255, 255),
            Color.rgb(0, 0, 0),
            Color.rgb(206, 0, 199),
            Color.rgb(207, 102, 121)),
    /**
     * <b>Colorful Theme</b>
     * <p>More Colors!</p>
     */
    COLORFUL(false,
            Color.rgb(1, 1, 1));


    Map<ThemeBase, Color> themeBaseColorMap;
    Color[] colors;
    boolean darkBase;

    /**
     * <b>Dynamic creation of themes</b>
     * <p>Theme colors will be put in to a {@link HashMap} so it will be easier to read</p>
     *
     * @param colors The color scheme of your theme
     */
    Theme(boolean darkBase, Color... colors) {
        // If a theme is not valid, assign the values of the invalid theme with the values of the fallback theme.
        if (colors.length < 3 || colors.length > ThemeBase.values().length) {
            colors = GameInsight.getInstance().getFallbackTheme().colors;
            if (GameInsight.getInstance().getFallbackTheme().isDarkBase())
                darkBase = true;
        }

        themeBaseColorMap = new HashMap<>();
        this.colors = colors;
        this.darkBase = darkBase;

        ThemeBase[] themeBases = ThemeBase.values();

        for (int c = 0; c < colors.length; c++)
            themeBaseColorMap.put(themeBases[c], colors.clone()[c]);
    }

    /**
     * <b>Main Accent Color Method</b>
     *
     * @return The selected themes main accent color
     */
    public Color getMainAccentColor() {
        return themeBaseColorMap.get(ThemeBase.MAIN_ACCENT_COLOR);
    }

    /**
     * <b>Main Background Color Method</b>
     *
     * @return The selected themes main background color
     */
    public Color getMainBackgroundColor() {
        return themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR);
    }

    /**
     * <b>Text Color Method</b>
     *
     * @return The selected themes text color
     */
    public Color getTextColor() {
        return themeBaseColorMap.get(ThemeBase.TEXT_COLOR);
    }

    /**
     * <b>Sub Accent Color Method</b>
     *
     * @return Either the sub accent color or the {@link GameInsight#getFallbackTheme()} sub accent color
     */
    public Color getSubAccentColor() {
        if (themeBaseColorMap.get(ThemeBase.SUB_ACCENT_COLOR) == null)
            themeBaseColorMap.put(ThemeBase.SUB_ACCENT_COLOR, GameInsight.getInstance().getFallbackTheme().getSubAccentColor());
        return themeBaseColorMap.get(ThemeBase.SUB_ACCENT_COLOR);
    }

    /**
     * <b>Text Emphasis</b>
     *
     * @param emphasis The emphasis the text element will be<br>{@link Emphasis#HIGH} 88%<br>{@link Emphasis#MEDIUM} 60%<br>{@link Emphasis#LOW} 38%<br>{@link Emphasis#VERY_LOW} 10%
     * @return The selected emphasis color
     */
    public Color getEmphasis(Emphasis emphasis) {
        return switch (emphasis) {
            case HIGH -> themeBaseColorMap.get(ThemeBase.TEXT_COLOR).deriveColor(1.00D, 1.00D, 0.88D, 1.00D);
            case MEDIUM -> themeBaseColorMap.get(ThemeBase.TEXT_COLOR).deriveColor(1.00D, 1.00D, 0.60D, 1.00D);
            case LOW -> themeBaseColorMap.get(ThemeBase.TEXT_COLOR).deriveColor(1.00D, 1.00D, 0.38D, 1.00D);
            case VERY_LOW -> themeBaseColorMap.get(ThemeBase.TEXT_COLOR).deriveColor(1.00D, 1.00D, 0.10D, 1.00D);
        };
    }

    /**
     * <b>Background Overlays</b>
     *
     * @param overlayValues The overlay the background color will be
     * @return The selected overlay color
     */
    public Color getOverlay(OverlayValues overlayValues) {
        double offset = 0.70D;
        if (isDarkBase())
            offset = 0.9D;
        return switch (overlayValues) {
            case OVERLAY_1 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.10D, 1.00D);
            case OVERLAY_2 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.14D, 1.00D);
            case OVERLAY_3 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.16D, 1.00D);
            case OVERLAY_4 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.18D, 1.00D);
            case OVERLAY_5 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.22D, 1.00D);
            case OVERLAY_6 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.24D, 1.00D);
            case OVERLAY_7 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.28D, 1.00D);
            case OVERLAY_8 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.30D, 1.00D);
            case OVERLAY_9 -> themeBaseColorMap.get(ThemeBase.MAIN_BACKGROUND_COLOR).deriveColor(1.00D, 1.00D, offset + 0.32D, 1.00D);
        };
    }

    public boolean isDarkBase() {
        return darkBase;
    }

    /**
     * <b>Constructors of a Theme</b>
     *
     * @author BeastlyMC956
     */
    private enum ThemeBase {
        MAIN_ACCENT_COLOR,
        MAIN_BACKGROUND_COLOR,
        TEXT_COLOR,

        SUB_ACCENT_COLOR,
        ERROR_COLOR
    }

    /**
     * <b>Emphasis Enum for Text</b>
     *
     * @author BeastlyMC956
     */
    public enum Emphasis {
        HIGH,
        MEDIUM,
        LOW,
        VERY_LOW
    }

    /**
     * <b>Overlay Enum for the Background</b>
     *
     * @author BeastlyMC956
     */
    public enum OverlayValues {
        /**
         * <p>10%</p>
         */
        OVERLAY_1,
        /**
         * <p>14%</p>
         */
        OVERLAY_2,
        /**
         * <p>16%</p>
         */
        OVERLAY_3,
        /**
         * <p>18%</p>
         */
        OVERLAY_4,
        /**
         * <p>22%</p>
         */
        OVERLAY_5,
        /**
         * <p>24%</p>
         */
        OVERLAY_6,
        /**
         * <p>28%</p>
         */
        OVERLAY_7,
        /**
         * <p>30%</p>
         */
        OVERLAY_8,
        /**
         * <p>32%</p>
         */
        OVERLAY_9
    }
}
