package main.java.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.java.gui.MenuBar.*;
import main.java.log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
    protected JMenuBar createMenuBar() {
        var itemModels = new ArrayList<IMenuItemModel>();
        itemModels.add(new MenuItemModel("New", KeyEvent.VK_N,
                                    e -> Logger.debug("Pressed new")));
        itemModels.add(new MenuItemModel("Quit", KeyEvent.VK_Q,
                                    e -> Logger.debug("Pressed quit")));

        var menuModel = new MenuModel("Document", null, KeyEvent.VK_D, itemModels);
        var models = new ArrayList<IMenu>(Collections.singletonList(menuModel));

        var menuBarFactory = new MenuBarFactory(models);
        var menuBar = menuBarFactory.createMenuBar();
        return menuBar;
    }
    
    private JMenuBar generateMenuBar()
    {

        var firstMenuModels = new ArrayList<IMenuItemModel>();
        firstMenuModels.add(new MenuItemModel("Системная схема", KeyEvent.VK_S, e -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        }));
        firstMenuModels.add(new MenuItemModel("Универсальная схема", KeyEvent.VK_S, e -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        }));
        var lookAndFeelMenuModel = new MenuModel("Режим отображения",
                "Управление режимом отображения приложения",
                KeyEvent.VK_V, firstMenuModels);


        var secondMenuModels = new ArrayList<IMenuItemModel>();
        secondMenuModels.add(new MenuItemModel("Сообщение в лог", KeyEvent.VK_S,
                e -> Logger.debug("Новая строка")));

        var testMenuModel = new MenuModel("Тесты", "Тестовые команды", KeyEvent.VK_T, secondMenuModels);

        var factory = new MenuBarFactory(new ArrayList<>(Arrays.asList(lookAndFeelMenuModel, testMenuModel)));
        return factory.createMenuBar();
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
