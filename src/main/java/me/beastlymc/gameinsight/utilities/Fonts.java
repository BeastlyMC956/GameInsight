package me.beastlymc.gameinsight.utilities;

/**
 * <b>Main Enum of Fonts for the application to use</b>
 * <p>Fonts are loaded from the pc or the application main folder</p>
 * <p>Check {@link FontUtilities#loadFont(Fonts, double)} for loading of these fonts</p>
 *
 * @author BeastlyMC956<br>
 */
public enum Fonts {
    BLACK("/fonts/Poppins-Black.ttf"),
    BLACK_ITALIC("/fonts/Poppins-BlackItalic.ttf"),
    BOLD("/fonts/Poppins-Bold.ttf"),
    BOLD_ITALIC("/fonts/Poppins-BoldItalic.ttf"),
    EXTRA_BOLD("/fonts/Poppins-ExtraBold.ttf"),
    EXTRA_BOLD_ITALIC("/fonts/Poppins-ExtraBoldItalic.ttf"),
    EXTRA_LIGHT("/fonts/Poppins-ExtraLight.ttf"),
    EXTRA_LIGHT_ITALIC("/fonts/Poppins-ExtraLightItalic.ttf"),
    ITALIC("/fonts/Poppins-Italic.ttf"),
    LIGHT("/fonts/Poppins-Light.ttf"),
    LIGHT_ITALIC("/fonts/Poppins-LightItalic.ttf"),
    MEDIUM("/fonts/Poppins-Medium.ttf"),
    MEDIUM_ITALIC("/fonts/Poppins-MediumItalic.ttf"),
    REGULAR("/fonts/Poppins-Regular.ttf"),
    SEMI_BOLD("/fonts/Poppins-SemiBold.ttf"),
    SEMI_BOLD_ITALIC("/fonts/Poppins-SemiBoldItalic.ttf"),
    THIN("/fonts/Poppins-Thin.ttf"),
    THIN_ITALIC("/fonts/Poppins-ThinItalic.ttf");

    final String LOCATION;

    Fonts(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLocation() {
        return LOCATION;
    }
}
