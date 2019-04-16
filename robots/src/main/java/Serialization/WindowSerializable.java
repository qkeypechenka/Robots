package main.java.Serialization;

import java.beans.PropertyVetoException;

public interface WindowSerializable {

    public int getWidth();
    public int getHeight();

    public int getX();
    public int getY();

    public WindowState getState();

    public void setLocation(int x, int y);
    public void setSize(int width, int height);
    public void setIcon(boolean icon) throws PropertyVetoException;
}
