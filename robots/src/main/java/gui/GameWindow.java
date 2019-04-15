package main.java.gui;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitHandler;
import main.java.Serialization.Serializable;
import main.java.Serialization.WindowSerializer;
import main.java.Serialization.WindowState;
import main.java.logic.GameLogic;
import main.java.logic.RobotStructure;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame implements Closable, Serializable
{
    private static int gameWindowWidth;
    private static int gameWindowHeight;

    private ExitHandler exitHandler;

    public static int getGameWindowWidth(){
        return gameWindowWidth;
    }

    public static int getGameWindowHeight(){
        return gameWindowHeight;
    }

    public GameWindow(MainApplicationFrame mainAppFrame, int gameWindowWidth, int gameWindowHeight)
    {
        super("Игровое поле", true, true, true, true);
        GameLogic gameLogic = new GameLogic();
        GameVisualizer visualizer = new GameVisualizer(gameLogic);
        GameWindow.gameWindowWidth = gameWindowWidth;
        GameWindow.gameWindowHeight = gameWindowHeight;
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);

        CoordinatesWindow coordinatesWindow = createCoordinatesWindow(gameLogic.getRobots().get(0));
        mainAppFrame.addWindow(coordinatesWindow);

        pack();
        exitHandler = new ExitHandler(this);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                exitHandler.onClose(CloseOptions.DispsoseOnly);
            }
        });
    }

    private CoordinatesWindow createCoordinatesWindow(RobotStructure robot)
    {
        var windowModel = WindowSerializer.deserializeWindow(Constants.coordinatesWindow);
        CoordinatesWindow coordinatesWindow = new CoordinatesWindow(robot, Constants.robotStartX, Constants.robotStartY);
        if (windowModel == null) {
            coordinatesWindow.setLocation(300, 10);
            coordinatesWindow.setSize(Constants.coordinatesWindowWidth, Constants.coordinatesWindowHeight);
            setMinimumSize(coordinatesWindow.getSize());
            coordinatesWindow.pack();
        } else {
            coordinatesWindow.setSize(windowModel.width, windowModel.height);
            coordinatesWindow.setLocation(windowModel.xPosition, windowModel.yPosition);
            try {
                coordinatesWindow.setIcon(windowModel.state == WindowState.Minimized);
            } catch (PropertyVetoException e) {
                System.out.println("Cannot change state");
            }
        }
        WindowSerializer.addWindow(coordinatesWindow, Constants.coordinatesWindow);
        return coordinatesWindow;
    }

    public WindowState getState() {
        return isIcon() ? WindowState.Minimized : WindowState.Default;
    }
}
