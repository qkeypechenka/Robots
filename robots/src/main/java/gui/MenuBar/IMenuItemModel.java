package main.java.gui.MenuBar;

import java.awt.event.ActionListener;

public interface IMenuItemModel {
    
    String getText();
    int getKeyEvent();
    ActionListener getListener();
}
