package main.java.gui.MenuBar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MenuBarFactory {

    ArrayList<IMenu> models;

    public MenuBarFactory(ArrayList<IMenu> models) {
        this.models = models;
    }

    public MenuBarFactory() {
        this.models = new ArrayList<>();
    }

    public void addMenu(IMenu model) {
        models.add(model);
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        for (var model: models) {
            var menu = createMenu(model.getText(), model.getKeyEvent(), model.getAccessibleDescription());
            for (var item: model.getModels()) {
                var menuItem = createMenuItem(item.getText(), item.getKeyEvent(), item.getListener());
                menu.add(menuItem);
            }
            menuBar.add(menu);
        }

        return menuBar;
    }

    private JMenu createMenu(String title, int keyEvent, String description) {
        JMenu menu = new JMenu(title);
        menu.setMnemonic(keyEvent);
        if (description != null) {
            menu.getAccessibleContext().setAccessibleDescription(description);
        }
        return menu;
    }

    private JMenuItem createMenuItem(String text, int keyEvent, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setMnemonic(keyEvent);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                keyEvent, ActionEvent.ALT_MASK));
        menuItem.setActionCommand(text.toLowerCase());
        menuItem.addActionListener(listener);
        return menuItem;
    }
}
