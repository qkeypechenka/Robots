package main.java.logic;

public class RobotMath {

    private static final double maxVelocity = 0.1;

    static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = Math.abs(x1 - x2);
        double diffY = Math.abs(y1 - y2);
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    static double applyLimits(double value)
    {
        if (value < 0) return 0;
        if (value > maxVelocity) return maxVelocity;
        return value;
    }

    static double asNormalizedRadians(double angle)
    {
        while (angle < 0) angle += 2*Math.PI;
        while (angle > 2*Math.PI) angle -= 2*Math.PI;
        return angle;
    }

    public static int round(double value)
    {
        return (int)(value + 0.5);
    }

}
