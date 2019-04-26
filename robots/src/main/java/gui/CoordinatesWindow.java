package main.java.gui;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitHandler;
import main.java.Localization.Localization;
import main.java.Serialization.WindowSerializable;
import main.java.Serialization.WindowState;
import main.java.logic.RobotStructure;

import java.awt.*;
import java.util.Observer;
import java.util.Observable;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class CoordinatesWindow extends JInternalFrame implements Closable, Observer, WindowSerializable
{
    private static double robotCoordinateX;
    private static double robotCoordinateY;

    private int updateCounter;

    private TextArea coordinatesContent;
    private ExitHandler closeController;

    CoordinatesWindow(RobotStructure robot, int robotStartX, int robotStartY)
    {
        super(Localization.getCoordinatesWindowTitle(), true, true, true, true);
        robot.addObserver(this);
        coordinatesContent = new TextArea("");
        coordinatesContent.setSize(Constants.coordinatesContentWidth, Constants.coordinatesContentHeight);
        CoordinatesWindow.robotCoordinateX = robotStartX;
        CoordinatesWindow.robotCoordinateY = robotStartY;
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coordinatesContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateCoordinatesContent(robot);
        closeController = new ExitHandler(this);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                closeController.onClose(CloseOptions.DispsoseOnly);
            }
        });
    }

    public WindowState getState() {
        return this.isIcon() ? WindowState.Minimized : WindowState.Default;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (updateCounter == 25){
            updateCoordinatesContent((RobotStructure)observable);
            updateCounter = 0;
        }
        updateCounter++;
    }

    private void updateCoordinatesContent(RobotStructure robot){
        robotCoordinateX = robot.getRobotPositionX();
        robotCoordinateY = robot.getRobotPositionY();
        StringBuilder text = new StringBuilder();
        text.append(Localization.getCoordinatesPositionX());
        text.append(robotCoordinateX);
        text.append('\n');
        text.append(Localization.getCoordinatesPositionY());
        text.append(robotCoordinateY);
        coordinatesContent.setFont(new Font("monospaced", Font.PLAIN, 16));
        coordinatesContent.setText(text.toString());
        coordinatesContent.invalidate();
    }
}
