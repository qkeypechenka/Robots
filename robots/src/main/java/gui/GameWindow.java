package main.java.gui;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitHandler;
import main.java.Localization.Localizable;
import main.java.Localization.Localization;
import main.java.Serialization.WindowSerializable;
import main.java.Serialization.WindowSerializer;
import main.java.Serialization.WindowState;
import main.java.logic.GameLogic;
import main.java.logic.RobotStructure;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame implements Closable, WindowSerializable, Localizable
{
    private static int gameWindowWidth;
    private static int gameWindowHeight;
    private CoordinatesWindow coordinatesWindow;

    private ExitHandler exitHandler;

    public static int getGameWindowWidth(){
        return gameWindowWidth;
    }

    public static int getGameWindowHeight(){
        return gameWindowHeight;
    }

    public GameWindow(MainApplicationFrame mainAppFrame,
                      int gameWindowWidth,
                      int gameWindowHeight,
                      GameVisualizer gameVisualizer,
                      GameLogic logic,
                      CoordinatesWindow coordinatesWindow)
    {
        super(Localization.getGameWindowTitle(), true, true, true, true);
//        GameLogic gameLogic = new GameLogic();
//        GameVisualizer visualizer = new GameVisualizer(gameLogic);
        var gameLogic = logic;
        var visualizer = gameVisualizer;
        GameWindow.gameWindowWidth = gameWindowWidth;
        GameWindow.gameWindowHeight = gameWindowHeight;
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);

        this.coordinatesWindow = coordinatesWindow;
//        this.coordinatesWindow = createCoordinatesWindow(gameLogic.getRobots().get(0));
        mainAppFrame.addWindow(this.coordinatesWindow);

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
        CoordinatesWindow coordinatesWindow = new CoordinatesWindow(robot,
                                                                    Constants.robotStartX,
                                                                    Constants.robotStartY);
        var result = WindowSerializer.deserializeInto(coordinatesWindow, Constants.coordinatesWindow);
        if (!result) {
            coordinatesWindow.setLocation(300, 10);
            coordinatesWindow.setSize(Constants.coordinatesWindowWidth, Constants.coordinatesWindowHeight);
            setMinimumSize(coordinatesWindow.getSize());
            coordinatesWindow.pack();
        }

        WindowSerializer.addWindow(coordinatesWindow, Constants.coordinatesWindow);
        return coordinatesWindow;
    }

    public WindowState getState() {
        return isIcon() ? WindowState.Minimized : WindowState.Default;
    }

    @Override
    public void updateLanguage() {
        this.title = Localization.getGameWindowTitle();
        coordinatesWindow.updateLanguage();
    }
}
