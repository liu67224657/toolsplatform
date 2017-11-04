package com.enjoyf.wiki.tools;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.impl.SimpleLog;

public class FileUtil {
    public static ClassLoader getContextClassLoader() {
        ClassLoader classLoader = null;

        if (classLoader == null) {
            try {
                // Are we running on a JDK 1.2 or later system?
                Method method = Thread.class.getMethod("getContextClassLoader", null);

                // Get the thread context class loader (if there is one)
                try {
                    classLoader = (ClassLoader) method.invoke(Thread.currentThread(), null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    /**
                     * InvocationTargetException is thrown by 'invoke' when the
                     * method being invoked (getContextClassLoader) throws an
                     * exception.
                     * 
                     * getContextClassLoader() throws SecurityException when the
                     * context class loader isn't an ancestor of the calling
                     * class's class loader, or if security permissions are
                     * restricted.
                     * 
                     * In the first case (not related), we want to ignore and
                     * keep going. We cannot help but also ignore the second
                     * with the logic below, but other calls elsewhere (to
                     * obtain a class loader) will trigger this exception where
                     * we can make a distinction.
                     */
                    if (e.getTargetException() instanceof SecurityException) {
                        e.printStackTrace();
                    } else {
                        // Capture 'e.getTargetException()' exception for
                        // details
                        // alternate: log 'e.getTargetException()', and pass
                        // back 'e'.
                        throw new LogConfigurationException("Unexpected InvocationTargetException", e.getTargetException());
                    }
                }
            } catch (NoSuchMethodException e) {
                // Assume we are running on JDK 1.1
                e.printStackTrace();
            }
        }

        if (classLoader == null) {
            classLoader = SimpleLog.class.getClassLoader();
        }

        // Return the selected class loader
        return classLoader;
    }

    public static InputStream getResourceAsStream(final String name) {
        return (InputStream) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                ClassLoader threadCL = getContextClassLoader();

                if (threadCL != null) {
                    return threadCL.getResourceAsStream(name);
                } else {
                    return ClassLoader.getSystemResourceAsStream(name);
                }
            }
        });
    }

    public static InputStream getInputStream(String path) throws Exception {
        return getResourceAsStream(path);
    }
}
