package main.java.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitHandler;
import main.java.Serialization.WindowSerializer;
import main.java.gui.MenuBar.*;
import main.java.log.Logger;

public class MainApplicationFrame extends JFrame implements Closable
{
    private final JDesktopPane mainWindow = new JDesktopPane();
    private ExitHandler exitHandler;
    private Locale ru_locale = new Locale("ru");
    private Locale en_locale = new Locale("en");
    private ResourceBundle resources = ResourceBundle.getBundle(Constants.localizationBundle, ru_locale);

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

        exitHandler = new ExitHandler(this, resources);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitHandler.onClose(CloseOptions.Exit);
            }
        });
    }

    private LogWindow createLogWindow()
    {
        LogWindow stored = new LogWindow(Logger.getDefaultLogSource(), resources);
        var result = WindowSerializer.deserializeInto(stored, Constants.logWindow);
        if (!result) {
            stored.setLocation(10, 10);
            stored.setSize(Constants.logWindowWidth, Constants.logWindowHeight);
            stored.pack();
        }
        Logger.debug(resources.getString("DebugMessage"));
        WindowSerializer.addWindow(stored, Constants.logWindow);
        return stored;
    }

    private GameWindow createGameWindow(Dimension screenSize) {
        var windowModel = WindowSerializer.deserializeWindow(Constants.gameWindow);
        GameWindow stored;
        if (windowModel == null) {
            stored = new GameWindow(this,
                    Constants.gameWindowWidth,
                    Constants.gameWindowHeight,
                    resources);
            stored.setLocation((screenSize.width / 3), (screenSize.height / 3));
            stored.setSize(Constants.gameWindowWidth, Constants.gameWindowHeight);
        } else {
            stored = new GameWindow(this, windowModel.width, windowModel.height, resources);
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

        var lookAndFeelMenuModel = new MenuModel(resources.getString("LookAndFeelMenuText"),
                resources.getString("LookAndFeelMenuDescription"),
                KeyEvent.VK_V);
        lookAndFeelMenuModel.addMenuItemModel(new MenuItemModel(resources.getString("LookAndFeelSystemScheme"),
                KeyEvent.VK_S, e -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        }));
        lookAndFeelMenuModel.addMenuItemModel(new MenuItemModel(resources.getString("LookAndFeelUniversalScheme"),
                KeyEvent.VK_S, e -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        }));
        factory.addMenu(lookAndFeelMenuModel);

        var testMenuModel = new MenuModel(resources.getString("TestMenuText"),
                resources.getString("TestMenuDescription"),
                KeyEvent.VK_T);
        testMenuModel.addMenuItemModel(new MenuItemModel(resources.getString("TestMenuLogMessageText"), KeyEvent.VK_S,
                e -> Logger.debug(resources.getString("TestMenuMessage"))));
        factory.addMenu(testMenuModel);

        var localizationMenuModel = new MenuModel(resources.getString("LocalizationMenuText"),
                resources.getString("LocalizationMenuDescription"), KeyEvent.VK_L);
        localizationMenuModel.addMenuItemModel(new MenuItemModel(resources.getString("LocalizationRussian"),
                KeyEvent.VK_L,
                e -> resources = ResourceBundle.getBundle(Constants.localizationBundle, ru_locale)));
        localizationMenuModel.addMenuItemModel(new MenuItemModel(resources.getString("LocalizationEnglish"),
                KeyEvent.VK_L,
                e -> resources = ResourceBundle.getBundle(Constants.localizationBundle, en_locale)));
        factory.addMenu(localizationMenuModel);

        var exitMenuModel = new MenuModel(resources.getString("OptionsMenuText"),
                resources.getString("OptionsMenuDescription"),
                KeyEvent.VK_O);
        exitMenuModel.addMenuItemModel(new MenuItemModel(resources.getString("OptionsMenuQuit"), KeyEvent.VK_Q,
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
