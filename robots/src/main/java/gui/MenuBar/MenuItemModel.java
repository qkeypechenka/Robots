package main.java.gui.MenuBar;

import java.awt.event.ActionListener;

public class MenuItemModel implements IMenuItemModel {

    String text;
    int keyEvent;
    ActionListener listener;

    public MenuItemModel(String text, int keyEvent, ActionListener listener) {
        this.text = text;
        this.keyEvent = keyEvent;
        this.listener = listener;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getKeyEvent() {
        return keyEvent;
    }

    @Override
    public ActionListener getListener() {
        return listener;
    }
}
