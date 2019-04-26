package main.java.Localization;

import java.util.ListResourceBundle;

public class Localization_ru extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"DebugMessage", "Протокол работает"},
                {"LookAndFeelMenuText", "Режим отображения"},
                {"LookAndFeelMenuDescription", "Управление режимом отображения приложения"},
                {"LookAndFeelSystemScheme", "Системная схема"},
                {"LookAndFeelUniversalScheme", "Универсальная схема"},
                {"TestMenuText", "Тесты"},
                {"TestMenuDescription", "Тестовые команды"},
                {"TestMenuLogMessageText", "Сообщение в лог"},
                {"TestMenuMessage", "Новая строка"},
                {"OptionsMenuText", "Дополнительно"},
                {"OptionsMenuDescription", "Дополнительные возможности"},
                {"OptionsMenuQuit", "Выйти"},
                {"GameWindowTitle", "Игровое поле"},
                {"LogWindowTitle", "Протокол работы"},
                {"CoordinatesWindowTitle", "Координаты робота"},
                {"CoordinatesPositionX", "Координата робота по X: "},
                {"CoordinatesPositionY", "Координата робота по Y: "},
                {"LocalizationMenuText", "Выбор языка"},
                {"LocalizationMenuDescription", "Выбор языка"},
                {"LocalizationRussian", "Русский"},
                {"LocalizationEnglish", "English"},
                {"ConfirmExit", "Вы уверены?"},
                {"ConfirmYes", "Да"},
                {"ConfirmNo", "Нет"}
        };
    }
}
