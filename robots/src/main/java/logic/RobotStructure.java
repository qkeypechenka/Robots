package main.java.logic;

import main.java.gui.GameWindow;

public class RobotStructure {

    private volatile double robotPositionX;
    private volatile double robotPositionY;
    private volatile double robotDirection;

    RobotStructure(double robotPositionX, double robotPositionY){
        this.robotPositionX = robotPositionX;
        this.robotPositionY = robotPositionY;
    }

    public double getRobotPositionX(){
        return robotPositionX;
    }

    public double getRobotPositionY(){
        return robotPositionY;
    }

    public double getRobotDirection(){
        return robotDirection;
    }

    void moveRobot(double velocity, double angularVelocity)
    {
        velocity = RobotMath.applyLimits(velocity);
        double newX = robotPositionX + velocity * 10 * Math.cos(robotDirection);
        if (15 < newX && newX < GameWindow.getGameWindowHeight() - 25)
            robotPositionX = newX;
        double newY = robotPositionY + velocity * 10 * Math.sin(robotDirection);
        if (15 < newY && newY < GameWindow.getGameWindowWidth() - 25 - 20)
            robotPositionY = newY;
        double angleForNormalizing = robotDirection + angularVelocity * 10;
        robotDirection = RobotMath.asNormalizedRadians(angleForNormalizing);
    }
}
