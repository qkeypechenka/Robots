package main.java.gui.MenuBar;

import java.util.ArrayList;

public interface IMenu {

    String getAccessibleDescription();
    String getText();
    int getKeyEvent();
    ArrayList<IMenuItemModel> getModels();
    void addMenuItemModel(IMenuItemModel model);
}
