package main.java.Serialization;

import java.beans.PropertyVetoException;
import java.io.*;
import java.util.HashMap;

public class WindowSerializer {

    private static HashMap<WindowSerializable, String> windows = new HashMap<>();

    public static void addWindow(WindowSerializable frame, String path) {
        windows.put(frame, path);
    }

    private static void serializeWindow(String path, WindowSerializable frame) {
        var window = convertToModel(frame);
        try {
            var fos = new FileOutputStream(path);
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(window);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error- " + e.toString());
        }
    }

    public static void serializeStored() {
        var keys = windows.keySet();
        for (var key : keys) {
            WindowSerializer.serializeWindow(windows.get(key), key);
        }
    }

    public static Window deserializeWindow(String path) {
        try {
            var fis = new FileInputStream(path);
            var oin = new ObjectInputStream(fis);
            return (Window) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static boolean deserializeInto(WindowSerializable frame, String path) {
        var windowModel = deserializeWindow(path);
        if (windowModel == null) {
            return false;
        }
        frame.setSize(windowModel.width, windowModel.height);
        frame.setLocation(windowModel.xPosition, windowModel.yPosition);
        try {
            frame.setIcon(windowModel.state == WindowState.Minimized);
        } catch (PropertyVetoException ignored) {

        }
        return true;
    }

    private static Window convertToModel(WindowSerializable frame) {
        var window = new Window();
        window.yPosition = frame.getY();
        window.xPosition = frame.getX();
        window.height = frame.getHeight();
        window.width = frame.getWidth();
        window.state = frame.getState();
        return window;
    }
}
