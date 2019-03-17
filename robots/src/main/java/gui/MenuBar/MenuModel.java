package main.java.gui.MenuBar;

import java.util.ArrayList;

public class MenuModel implements IMenu {

    private String text;
    private String description;
    private int keyEvent;
    private ArrayList<IMenuItemModel> models;


    public MenuModel(String text, String description, int keyEvent, ArrayList<IMenuItemModel> models) {
        this.text = text;
        this.description = description;
        this.keyEvent = keyEvent;
        this.models = models;
    }

    public MenuModel(String text, String description, int keyEvent) {
        this.text = text;
        this.description = description;
        this.keyEvent = keyEvent;
        models = new ArrayList<>();
    }

    @Override
    public String getAccessibleDescription() {
        return description;
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
    public ArrayList<IMenuItemModel> getModels() {
        return models;
    }

    @Override
    public void addMenuItemModel(IMenuItemModel model) {
        models.add(model);
    }
}
