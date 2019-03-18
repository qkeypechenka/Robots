package main.java.logic;

import java.awt.Point;
import java.util.ArrayList;

public class GameLogic {

    private static final double maxAngularVelocity = 0.001;

    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;

    private ArrayList<RobotStructure> robots = new ArrayList<>();

    public ArrayList<RobotStructure> getRobots(){
        return robots;
    }

    public void setTargetPosition(Point p)
    {
        targetPositionX = p.x;
        targetPositionY = p.y;
    }

    public int getTargetPositionX(){
        return targetPositionX;
    }

    public int getTargetPositionY(){
        return targetPositionY;
    }

    public void onModelUpdateEvent()
    {
        for (RobotStructure robot : getRobots()){
            double distance = RobotMath.distance(getTargetPositionX(), getTargetPositionY(),
                    robot.getRobotPositionX(), robot.getRobotPositionY());
            if (distance < 0.5)
            {
                return;
            }
            double velocity = 0.1;
            double angleToTarget = RobotMath.angleTo(robot.getRobotPositionX(), robot.getRobotPositionY(),
                    getTargetPositionX(), getTargetPositionY());
            double angularVelocity = 0;
            if (angleToTarget > robot.getRobotDirection())
            {
                angularVelocity = maxAngularVelocity;
            }
            if (angleToTarget < robot.getRobotDirection())
            {
                angularVelocity = -maxAngularVelocity;
            }
            robot.moveRobot(velocity, angularVelocity);
        }

    }

    public void createRobot(double x, double y){
        RobotStructure r = new RobotStructure(x, y);
        getRobots().add(r);
    }
}
