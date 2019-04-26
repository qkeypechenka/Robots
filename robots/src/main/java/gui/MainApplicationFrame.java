package main.java.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitHandler;
import main.java.Localization.Localization;
import main.java.Serialization.WindowSerializer;
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
        LogWindow stored = new LogWindow(Logger.getDefaultLogSource());
        var result = WindowSerializer.deserializeInto(stored, Constants.logWindow);
        if (!result) {
            stored.setLocation(10, 10);
            stored.setSize(Constants.logWindowWidth, Constants.logWindowHeight);
            stored.pack();
        }
        Logger.debug(Localization.getDebugMessage());
        WindowSerializer.addWindow(stored, Constants.logWindow);
        return stored;
    }

    private GameWindow createGameWindow(Dimension screenSize) {
        var windowModel = WindowSerializer.deserializeWindow(Constants.gameWindow);
        GameWindow stored;
        if (windowModel == null) {
            stored = new GameWindow(this,
                    Constants.gameWindowWidth,
                    Constants.gameWindowHeight);
            stored.setLocation((screenSize.width / 3), (screenSize.height / 3));
            stored.setSize(Constants.gameWindowWidth, Constants.gameWindowHeight);
        } else {
            stored = new GameWindow(this, windowModel.width, windowModel.height);
            WindowSerializer.deserializeInto(stored, Constants.gameWindow);
        }
        WindowSerializer.addWindow(stored, Constants.gameWindow);
        return stored;
    }
    
    void addWindow(JInternalFrame frame)
    {
        mainWindow.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar()
    {
        var factory = new MenuBarFactory();

        var lookAndFeelMenuModel = new MenuModel(Localization.getLookAndFeelMenuText(),
                Localization.getLookAndFeelMenuDescription(),
                KeyEvent.VK_V);
        lookAndFeelMenuModel.addMenuItemModel(new MenuItemModel(Localization.getLookAndFeelSystemScheme(),
                KeyEvent.VK_S, e -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        }));
        lookAndFeelMenuModel.addMenuItemModel(new MenuItemModel(Localization.getLookAndFeelUniversalScheme(),
                KeyEvent.VK_S, e -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        }));
        factory.addMenu(lookAndFeelMenuModel);

        var testMenuModel = new MenuModel(Localization.getTestMenuText(),
                Localization.getTestMenuDescription(),
                KeyEvent.VK_T);
        testMenuModel.addMenuItemModel(new MenuItemModel(Localization.getTestMenuLogMessageText(), KeyEvent.VK_S,
                e -> Logger.debug(Localization.getTestMenuMessage())));
        factory.addMenu(testMenuModel);

        var localizationMenuModel = new MenuModel(Localization.getLocalizationMenuText(),
                Localization.getLocalizationMenuDescription(), KeyEvent.VK_L);
        localizationMenuModel.addMenuItemModel(new MenuItemModel(Localization.getLocalizationRussian(),
                KeyEvent.VK_L,
                e -> Localization.setRuLocale()));
        localizationMenuModel.addMenuItemModel(new MenuItemModel(Localization.getLocalizationEnglish(),
                KeyEvent.VK_L,
                e -> Localization.setEnLocale()));
        factory.addMenu(localizationMenuModel);

        var exitMenuModel = new MenuModel(Localization.getOptionsMenuText(),
                Localization.getOptionsMenuDescription(),
                KeyEvent.VK_O);
        exitMenuModel.addMenuItemModel(new MenuItemModel(Localization.getOptionsMenuQuit(), KeyEvent.VK_Q,
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
