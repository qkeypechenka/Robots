package main.java.Localization;

public class Localization_en extends Localization_ru {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"DebugMessage", "Protocol works"},
                {"LookAndFeelMenuText", "Look and Feel mode"},
                {"LookAndFeelMenuDescription", "Manage look and feel"},
                {"LookAndFeelSystemScheme", "System scheme"},
                {"LookAndFeelUniversalScheme", "Universal scheme"},
                {"TestMenuText", "Tests"},
                {"TestMenuDescription", "Test commands"},
                {"TestMenuLogMessageText", "Log message"},
                {"TestMenuMessage", "New string"},
                {"OptionsMenuText", "Options"},
                {"OptionsMenuDescription", "Additional features"},
                {"OptionsMenuQuit", "Quit"},
                {"GameWindowTitle", "Game window"},
                {"LogWindowTitle", "Work protocol"},
                {"CoordinatesWindowTitle", "Robot coordinates"},
                {"CoordinatesPositionX", "X coordinate: "},
                {"CoordinatesPositionY", "Y coordinate: "},
                {"LocalizationMenuText", "Choose language"},
                {"LocalizationMenuDescription", "Choose language"},
                {"LocalizationRussian", "Русский"},
                {"LocalizationEnglish", "English"},
                {"ConfirmExit", "Are you sure?"},
                {"ConfirmYes", "Yes"},
                {"ConfirmNo", "No"}
        };
    }
}
