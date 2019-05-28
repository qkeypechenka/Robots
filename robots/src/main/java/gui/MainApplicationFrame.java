package main.java.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitHandler;
import main.java.Loader.Loader;
import main.java.Localization.Localizable;
import main.java.Localization.Localization;
import main.java.Serialization.WindowSerializer;
import main.java.gui.MenuBar.*;
import main.java.log.Logger;
import main.java.logic.GameLogic;

public class MainApplicationFrame extends JFrame implements Closable, Localizable
{
    private final JDesktopPane mainWindow = new JDesktopPane();
    private LogWindow logWindow;
    private GameWindow gameWindow;
    private ExitHandler exitHandler;

    public MainApplicationFrame() {
        //Сделали под экран наше приложение с отступом в (50, 50) от сторон экрана
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(mainWindow);

        logWindow = createLogWindow();
        addWindow(logWindow);

//        gameWindow = createGameWindow(screenSize);
//        addWindow(gameWindow);

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

//    private GameWindow createGameWindow(Dimension screenSize) {
//        var windowModel = WindowSerializer.deserializeWindow(Constants.gameWindow);
//        GameWindow stored;
//        if (windowModel == null) {
//            stored = new GameWindow(this,
//                    Constants.gameWindowWidth,
//                    Constants.gameWindowHeight);
//            stored.setLocation((screenSize.width / 3), (screenSize.height / 3));
//            stored.setSize(Constants.gameWindowWidth, Constants.gameWindowHeight);
//        } else {
//            stored = new GameWindow(this, windowModel.width, windowModel.height);
//            WindowSerializer.deserializeInto(stored, Constants.gameWindow);
//        }
//        WindowSerializer.addWindow(stored, Constants.gameWindow);
//        return stored;
//    }

//    private GameWindow createGameWindow(Dimension screenSize) {
//
//    }
    
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
                KeyEvent.VK_E,
                e -> {
                    Localization.setRuLocale();
                    updateLanguage();
                }
                ));
        localizationMenuModel.addMenuItemModel(new MenuItemModel(Localization.getLocalizationEnglish(),
                KeyEvent.VK_R,
                e -> {
                    Localization.setEnLocale();
                    updateLanguage();
                }
                ));
        factory.addMenu(localizationMenuModel);

        var jarMenuModel = new MenuModel(Localization.getRobotsMenu(),
                "", KeyEvent.VK_R);
        jarMenuModel.addMenuItemModel(new MenuItemModel(Localization.getChooseJar(),
                KeyEvent.VK_J,
                e -> {
                    var chooser = new JFileChooser();
                    var filter = new FileNameExtensionFilter("JAR file", "jar");
                    chooser.setFileFilter(filter);
                    var desicion = chooser.showOpenDialog(this);
                    if (desicion == JFileChooser.APPROVE_OPTION) {
                        System.out.println("You've chosen file" + chooser.getSelectedFile().getName());
                        var file = chooser.getSelectedFile();
                        try {
                            var logic = Loader.loadFromJar(file, "logic.GameLogic");
                            var gameVisualiserClass = Loader.loadFromJar(file, "gui.GameVisualizer");
                            var gameLogic = (GameLogic) Loader.createInstance(logic);
                            var gameVisualizer = (GameVisualizer) Loader.createInstance(gameVisualiserClass,
                                                                                         gameLogic);
                            var coordinatesWindow = createCoordinatesWindow(file, gameLogic);
                            gameWindow = new GameWindow(this,
                                                                Constants.gameWindowWidth,
                                                                Constants.gameWindowHeight,
                                                                gameVisualizer,
                                                                gameLogic,
                                                                coordinatesWindow);
                            addWindow(gameWindow);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        // TODO: добавление gamewindow
                    }
                }));
        factory.addMenu(jarMenuModel);

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

    private CoordinatesWindow createCoordinatesWindow(File file, GameLogic logic) {
        try {
            var coordinatesClass = Loader.loadFromJar(file, "gui.CoordinatesWindow");
            var robot = logic.getRobots().get(0);
            var coordinatesWindow = (CoordinatesWindow) Loader.createInstance(coordinatesClass, robot,
                                                            Constants.robotStartX,
                                                            Constants.robotStartY);
            return coordinatesWindow;
        } catch(Exception e) {
            System.exit(100500);
        }
        return null;
    }

    @Override
    public void updateLanguage(){
        setJMenuBar(generateMenuBar());
        logWindow.updateLanguage();
        Logger.debug(Localization.getDebugMessage());
        gameWindow.updateLanguage();
        this.invalidate();
    }
}
