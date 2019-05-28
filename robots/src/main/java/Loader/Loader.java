package main.java.Loader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Loader {

    public static Class<?> loadFromJar(File jarPath, String className) throws
            IllegalArgumentException,
            NoSuchMethodException,
            ClassNotFoundException,
            MalformedURLException,
            InvocationTargetException,
            IllegalAccessException{

        if(jarPath != null) {
            URL url = convertPathToJarURL(jarPath.getAbsolutePath());
            URL[] urls = {url};
            var loader = new URLClassLoader(urls);
            return loader.loadClass(className);
        } else {
            throw new NullPointerException();
        }
    }

    public static Object createInstance(Class<?> clazz, Object... args) throws
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return getMatchingAccessibleConstructor(clazz, convertToTypes(args)).newInstance(args);
    }

    public static Object invokeMethod(Object self, String method, Object... args) throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return self.getClass().getMethod(method, convertToTypes(args)).invoke(self, args);
    }

    private static Constructor getMatchingAccessibleConstructor(Class<?> clazz, Class<?>... args) throws
            NoSuchMethodException {
        loop:
        for (var constructor : clazz.getConstructors()) {
            var types = constructor.getParameterTypes();
            if (types.length == args.length) {
                for (var i = 0; i < args.length; i++) {
                    if (!types[i].isAssignableFrom(args[i])) {
                        continue loop;
                    }
                }
            }
            return constructor;
        }
        return clazz.getConstructor(args);
    }

    private static Class<?>[] convertToTypes(Object[] objects) {
        return Arrays.stream(objects).map(Object::getClass).toArray(Class[]::new);
    }

    private static URL convertPathToJarURL(String path) {
        try {
            return new URL("jar", "", "file:" + path + "!/");
        } catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
