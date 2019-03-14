package main.java.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;

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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
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
        return menuBarFactory.createMenuBar();
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

    private void onClose() {

        var n = JOptionPane.showOptionDialog(new JFrame(), "Are you sure?", "Do you want close program?",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Yes", "No"},
                JOptionPane.YES_OPTION);

        if (n == JOptionPane.YES_OPTION) {
            this.dispose();
            System.exit(0);
        }
    }
}
