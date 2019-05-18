package main.java.Localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    private static Locale ru_locale = new Locale("ru");
    private static Locale en_locale = new Locale("en");
    private static String bundleName = "main.java.Localization.Localization";
    private static ResourceBundle instance = ResourceBundle.getBundle(bundleName, ru_locale);

    private static String getStringBy(String name) {
        return instance.getString(name);
    }

    public static void setRuLocale() {
        instance = ResourceBundle.getBundle(bundleName, ru_locale);
    }

    public static void setEnLocale() {
        instance = ResourceBundle.getBundle(bundleName, en_locale);
    }

    public static String getDebugMessage() {
        return getStringBy("DebugMessage");
    }

    public static String getLookAndFeelMenuText() {
        return getStringBy("LookAndFeelMenuText");
    }

    public static String getLookAndFeelMenuDescription() {
        return getStringBy("LookAndFeelMenuDescription");
    }

    public static String getLookAndFeelSystemScheme() {
        return getStringBy("LookAndFeelSystemScheme");
    }

    public static String getLookAndFeelUniversalScheme() {
        return getStringBy("LookAndFeelUniversalScheme");
    }

    public static String getTestMenuText() {
        return getStringBy("TestMenuText");
    }

    public static String getTestMenuDescription() {
        return getStringBy("TestMenuDescription");
    }

    public static String getTestMenuLogMessageText() {
        return getStringBy("TestMenuLogMessageText");
    }

    public static String getTestMenuMessage() {
        return getStringBy("TestMenuMessage");
    }

    public static String getOptionsMenuText() {
        return getStringBy("OptionsMenuText");
    }

    public static String getOptionsMenuDescription() {
        return getStringBy("OptionsMenuDescription");
    }

    public static String getOptionsMenuQuit() {
        return getStringBy("OptionsMenuQuit");
    }

    public static String getGameWindowTitle() {
        return getStringBy("GameWindowTitle");
    }

    public static String getLogWindowTitle() {
        return getStringBy("LogWindowTitle");
    }

    public static String getCoordinatesWindowTitle() {
        return getStringBy("CoordinatesWindowTitle");
    }

    public static String getCoordinatesPositionX() {
        return getStringBy("CoordinatesPositionX");
    }

    public static String getCoordinatesPositionY() {
        return getStringBy("CoordinatesPositionY");
    }

    public static String getLocalizationMenuText() {
        return getStringBy("LocalizationMenuText");
    }

    public static String getLocalizationMenuDescription() {
        return getStringBy("LocalizationMenuDescription");
    }

    public static String getLocalizationRussian() {
        return getStringBy("LocalizationRussian");
    }

    public static String getLocalizationEnglish() {
        return getStringBy("LocalizationEnglish");
    }

    public static String getConfirmExit() {
        return getStringBy("ConfirmExit");
    }

    public static String getConfirmYes() {
        return getStringBy("ConfirmYes");
    }

    public static String getConfirmNo() {
        return getStringBy("ConfirmNo");
    }

    public static String getRobotsMenu() {
        return getStringBy("Robots");
    }

    public static String getChooseJar() {
        return getStringBy("Choose robot");
    }
}
