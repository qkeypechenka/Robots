package main.java.Serialization;

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
