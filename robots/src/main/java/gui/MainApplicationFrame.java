package main.java.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.WindowController;
import main.java.gui.MenuBar.*;
import main.java.log.Logger;

public class MainApplicationFrame extends JFrame implements Closable
{
    private final JDesktopPane mainWindow = new JDesktopPane();
    private WindowController closeController;

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

        GameWindow gameWindow = new GameWindow(Constants.gameWindowWidth, Constants.gameWindowHeight);
        gameWindow.setLocation((screenSize.width - GameWindow.getGameWindowWidth())/2,
                (screenSize.height - GameWindow.getGameWindowHeight())/2);
        gameWindow.setSize(GameWindow.getGameWindowWidth(),  GameWindow.getGameWindowHeight());
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        closeController = new WindowController(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeController.onClose(CloseOptions.Full);
            }
        });
    }

    private LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(Constants.logWindowWidth, Constants.logWindowHeight);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
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
                e -> closeController.onClose(CloseOptions.Full)));

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
