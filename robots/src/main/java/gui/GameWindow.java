package main.java.gui;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private static int gameWindowWidth;
    private static int gameWindowHeight;

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
    }
}
