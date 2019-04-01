package main.java.gui;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private static int gameWindowWidth;
    private static int gameWindowHeight;

    public static int getGameWindowWidth(){
        return GameWindow.gameWindowWidth;
    }

    public static int getGameWindowHeight(){
        return GameWindow.gameWindowHeight;
    }

    public GameWindow(int gameWindowWidth, int gameWindowHeight)
    {
        super("Игровое поле", true, true, true, true);
        GameWindow.gameWindowWidth = gameWindowWidth;
        GameWindow.gameWindowHeight = gameWindowHeight;
        GameVisualizer visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        getContentPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                Component c = (Component)e.getSource();
                GameWindow.gameWindowWidth = c.getWidth();
                GameWindow.gameWindowHeight = c.getHeight();
                System.out.println(GameWindow.gameWindowWidth + " " + GameWindow.gameWindowHeight);
            }
        });
        pack();
    }
}
