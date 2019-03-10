package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class MenuBarFactory {

    private Optional<ActionListener> listener;

    public MenuBarFactory(Optional<ActionListener> listener) {
        this.listener = listener;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        var menu = createMenu("Document", KeyEvent.VK_D);
        menuBar.add(menu);

        //Set up the first menu item.
        var menuItem = createMenuItem("New", KeyEvent.VK_N);
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = createMenuItem("Quit", KeyEvent.VK_Q);
        menu.add(menuItem);

        return menuBar;
    }

    private JMenu createMenu(String title, int keyEvent) {
        JMenu menu = new JMenu(title);
        menu.setMnemonic(keyEvent);
        return menu;
    }

    private JMenuItem createMenuItem(String text, int keyEvent) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setMnemonic(keyEvent);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                keyEvent, ActionEvent.ALT_MASK));
        menuItem.setActionCommand(text.toLowerCase());
        listener.ifPresent(menuItem::addActionListener);
        return menuItem;
    }
}
