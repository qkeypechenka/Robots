package main.java.gui;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitController;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame implements Closable
{
    private static int gameWindowWidth;
    private static int gameWindowHeight;

    private ExitController closeController;

    public static int getGameWindowWidth(){
        return gameWindowWidth;
    }

    public static int getGameWindowHeight(){
        return gameWindowHeight;
    }

    public GameWindow(int gameWindowWidth, int gameWindowHeight)
    {
        super("Игровое поле", true, true, true, true);
        GameWindow.gameWindowWidth = gameWindowWidth;
        GameWindow.gameWindowHeight = gameWindowHeight;
        GameVisualizer visualizer = new GameVisualizer();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        closeController = new ExitController(this);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                closeController.onClose(CloseOptions.DispsoseOnly);
            }
        });
    }
}
