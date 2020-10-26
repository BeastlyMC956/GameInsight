package me.beastlymc.gameinsight.gui;

import javafx.scene.paint.Color;

import java.util.*;

public class Theme {
    private static final List<Color> DARK_THEME = new ArrayList<>() {{
        add(Color.rgb(0, 199, 206));
        add(Color.rgb(206, 0, 199));
        add(Color.rgb(0, 0, 0));
        add(Color.rgb(255, 255, 255));
        add(Color.rgb(207, 102, 121));
    }};
    private static final List<Color> LIGHT_THEME = new ArrayList<>() {{
        add(Color.rgb(0, 199, 206));
        add(Color.rgb(206, 0, 199));
        add(Color.rgb(255, 255, 255));
        add(Color.rgb(0, 0, 0));
        add(Color.rgb(207, 102, 121));
    }};
    private static final List<Color> FALLBACK_THEME = DARK_THEME;

    public static final Theme DARK = new Theme(DARK_THEME, true);
    public static final Theme LIGHT = new Theme(LIGHT_THEME, false);
    public static final Theme COLORFUL = new Theme(DARK_THEME);

    private final Map<List<Color>, Boolean> THEME_MANAGER = new HashMap<>();

    boolean darkBase;
    List<Color> theme;
    EnumMap<ThemeBase, Color> themeBaseColorMap = new EnumMap<>(ThemeBase.class);

    public Theme(List<Color> theme, boolean darkBase) {
        if (theme.size() < ThemeBase.BACKGROUND_COLOR.ordinal() || theme.size() > ThemeBase.values().length) {
            theme = FALLBACK_THEME;
            if (darkBase)
                this.darkBase = true;
        }
        this.theme = theme;
        this.darkBase = darkBase;

        addTheme(theme, darkBase);
    }

    public Theme(List<Color> theme) {
        if (theme.size() < ThemeBase.BACKGROUND_COLOR.ordinal() || theme.size() > ThemeBase.values().length)
            theme = FALLBACK_THEME;
        this.theme = theme;

        int position = ThemeBase.BACKGROUND_COLOR.ordinal();
        float f = 150.0F / 255.0F;
        if ((theme.get(position).getRed() < f && theme.get(position).getGreen() < f && theme.get(position).getBlue() < f) || theme.get(position).getBrightness() < f / 3.0F)
            this.darkBase = true;
        addTheme(theme, this.darkBase);
    }

    private void addTheme(List<Color> theme, boolean darkBase) {
        for (Map.Entry<List<Color>, Boolean> entry : THEME_MANAGER.entrySet()) {
            for (int i = 0; i < entry.getKey().size(); i++) {
                if (entry.getKey().get(i) == theme.get(i))
                    return;
            }
        }
        int i = 0;
        for (Color color : theme) {
            this.themeBaseColorMap.put(ThemeBase.values()[i], color);
            i++;
        }
        THEME_MANAGER.put(theme, darkBase);
    }

    public Color addOverlay(double percent){
        double offset = isDarkBase() ? 0.8D : 0.6D;

        if(percent > 1.0D)
            percent /= 100.0D;
        percent = 1 - percent;

        return getThemeBaseColorMap().get(ThemeBase.BACKGROUND_COLOR).deriveColor(1.00D,1.00D, offset + percent, 1.00D);
    }

    public Color getEmphasis(double percent) {
        if(percent > 1.0D)
            percent /= 100.0D;

        return getThemeBaseColorMap().get(ThemeBase.TEXT_COLOR).deriveColor(1.00D, 1.00D, percent, 1.00D);
    }

    public boolean isDarkBase() {
        return darkBase;
    }

    public EnumMap<ThemeBase, Color> getThemeBaseColorMap() {
        return themeBaseColorMap;
    }

    public enum ThemeBase {

        PRIMARY_ACCENT_COLOR,
        SECONDARY_ACCENT_COLOR,
        BACKGROUND_COLOR,
        TEXT_COLOR,
        ERROR_COLOR;

    }
}
