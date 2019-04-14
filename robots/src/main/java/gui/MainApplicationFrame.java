package main.java.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;

import javax.swing.*;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitHandler;
import main.java.Serialization.WindowSerializer;
import main.java.Serialization.WindowState;
import main.java.gui.MenuBar.*;
import main.java.log.Logger;

public class MainApplicationFrame extends JFrame implements Closable
{
    private final JDesktopPane mainWindow = new JDesktopPane();
    private ExitHandler exitHandler;

    public MainApplicationFrame() {
        //Сделали под экран наше приложение с отступом в (50, 50) от сторон экрана
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(mainWindow);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = createGameWindow(screenSize);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        exitHandler = new ExitHandler(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitHandler.onClose(CloseOptions.Exit);
            }
        });
    }

    private LogWindow createLogWindow()
    {
        var windowModel = WindowSerializer.deserializeWindow(Constants.logWindow);
        LogWindow stored = new LogWindow(Logger.getDefaultLogSource());
        if (windowModel == null) {
            stored.setLocation(10, 10);
            stored.setSize(Constants.logWindowWidth, Constants.logWindowHeight);
            stored.pack();
        } else {
            stored.setLocation(windowModel.xPosition, windowModel.yPosition);
            stored.setSize(windowModel.width, windowModel.height);
            if (windowModel.state == WindowState.Minimized && stored.isIconifiable()) {
                try {
                    stored.setIcon(true);
                } catch (PropertyVetoException e) {
                    System.out.println("Cannot change state");
                }
            }
        }
        Logger.debug("Протокол работает");
        WindowSerializer.addWindow(stored, Constants.logWindow);
        return stored;
    }

    private GameWindow createGameWindow(Dimension screenSize) {
        var windowModel = WindowSerializer.deserializeWindow(Constants.gameWindow);
        GameWindow stored;
        if (windowModel == null) {
            stored = new GameWindow(Constants.gameWindowWidth, Constants.gameWindowHeight);
            stored.setLocation((screenSize.width / 3), (screenSize.height / 3));
            stored.setSize(Constants.gameWindowWidth, Constants.gameWindowHeight);
        } else {
            stored = new GameWindow(windowModel.width, windowModel.height);
            stored.setLocation(windowModel.xPosition, windowModel.yPosition);
            stored.setSize(windowModel.width, windowModel.height);
            if (windowModel.state == WindowState.Minimized && stored.isIconifiable()) {
                try {
                    stored.setIcon(true);
                } catch (PropertyVetoException e) {
                    System.out.println("Cannot change state");
                }
            }
        }
        WindowSerializer.addWindow(stored, Constants.gameWindow);
        return stored;
    }
    
    private void addWindow(JInternalFrame frame)
    {
        mainWindow.add(frame);
        frame.setVisible(true);
    }
    
    protected JMenuBar createMenuBar() {
        var menuBarFactory = new MenuBarFactory();

        var menuModel = new MenuModel("Document", null, KeyEvent.VK_D);
        menuModel.addMenuItemModel(new MenuItemModel("New", KeyEvent.VK_N,
                e -> Logger.debug("Pressed new")));
        menuModel.addMenuItemModel(new MenuItemModel("Quit", KeyEvent.VK_Q,
                e -> exitHandler.onClose(CloseOptions.Exit)));

        menuBarFactory.addMenu(menuModel);

        return menuBarFactory.createMenuBar();
    }
    
    private JMenuBar generateMenuBar()
    {
        var factory = new MenuBarFactory();

        var lookAndFeelMenuModel = new MenuModel("Режим отображения",
                "Управление режимом отображения приложения",
                KeyEvent.VK_V);
        lookAndFeelMenuModel.addMenuItemModel(new MenuItemModel("Системная схема", KeyEvent.VK_S, e -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        }));
        lookAndFeelMenuModel.addMenuItemModel(new MenuItemModel("Универсальная схема", KeyEvent.VK_S, e -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        }));
        factory.addMenu(lookAndFeelMenuModel);

        var testMenuModel = new MenuModel("Тесты", "Тестовые команды",
                KeyEvent.VK_T);
        testMenuModel.addMenuItemModel(new MenuItemModel("Сообщение в лог", KeyEvent.VK_S,
                e -> Logger.debug("Новая строка")));
        factory.addMenu(testMenuModel);

        var exitMenuModel = new MenuModel("Options", "Options", KeyEvent.VK_O);
        exitMenuModel.addMenuItemModel(new MenuItemModel("Quit", KeyEvent.VK_Q,
                e -> exitHandler.onClose(CloseOptions.Exit)));
        factory.addMenu(exitMenuModel);

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
