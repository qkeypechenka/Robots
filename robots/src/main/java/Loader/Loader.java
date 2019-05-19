package main.java.Loader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Loader {
    public static Class<?> loadFromJar(File jarPath, String className) throws
            IllegalArgumentException,
            NoSuchMethodException,
            ClassNotFoundException,
            MalformedURLException,
            InvocationTargetException,
            IllegalAccessException{

        if(jarPath != null) {
            URL url = jarPath.toURI().toURL();
            URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            add.setAccessible(true);
            add.invoke(sysLoader, url);
            return Class.forName(className);
        } else {
            throw new NullPointerException();
        }
    }
}
