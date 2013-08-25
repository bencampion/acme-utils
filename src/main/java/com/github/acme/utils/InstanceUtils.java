package com.github.acme.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Lightweight, enterprise ready, instance management solution that can be used
 * to create and manage singleton instances of any class with a nullary
 * constructor. Evil reflection is used to allow new instantiation of classes
 * with inaccessible constructors, such as classes that implement the singleton
 * pattern. InstanceUtils is itself one such class, making it possible for an
 * InstanceUtils instance to create and manage one new instance of itself. This
 * powerful feature allows the creation of unlimited singletons where each
 * singleton instance is managed via an instance of InstanceUtils.
 * 
 * Fun fact: the case insensitive (sub)string "instance" appears 76 times in
 * this class definition.
 */
public class InstanceUtils {

    private static InstanceUtils instance;

    private Map<Class<?>, Object> instances;

    private InstanceUtils() {
        instances = new HashMap<Class<?>, Object>();
    }

    /**
     * Gets the first instance of InstanceUtils.
     * 
     * @return first instance
     */
    public static synchronized InstanceUtils getInstance() {
        if (instance == null) {
            instance = new InstanceUtils();
        }
        return instance;
    }

    /**
     * Convenience method to get the nth instance of InstanceUtils.
     * 
     * @param n
     *            instance to return
     * @return nth instance
     */
    public static synchronized InstanceUtils getInstance(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must not be less than 1");
        }
        return getInstance(getInstance(), n);
    }

    private static InstanceUtils getInstance(InstanceUtils iu, int n) {
        if (n == 1) {
            return iu;
        }
        return getInstance(iu.getInstance(InstanceUtils.class), n - 1);
    }

    /**
     * Returns the number of instances of InstanceUtils.
     * 
     * @return number of instances
     */
    public static synchronized int instances() {
        return instances(getInstance());
    }

    private static int instances(InstanceUtils iu) {
        if (iu.instances.containsKey(InstanceUtils.class)) {
            return instances(iu.getInstance(InstanceUtils.class)) + 1;
        }
        return 1;
    }

    /**
     * Gets the instance for the specified class managed by this instance of
     * InstanceUtils.
     * 
     * @param cls
     *            class to get
     * @return class instance
     */
    public synchronized <T> T getInstance(Class<T> cls) {
        @SuppressWarnings("unchecked")
        T o = (T) instances.get(cls);
        if (o == null) {
            try {
                Constructor<T> c = cls.getDeclaredConstructor(new Class<?>[0]);
                if (!c.isAccessible()) {
                    c.setAccessible(true);
                }
                o = c.newInstance(new Object[0]);
                instances.put(cls, o);
            }
            catch (IllegalAccessException e) {
                throw new AssertionError("Constructor is always accessable");
            }
            catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
            catch (InstantiationException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
            catch (InvocationTargetException e) {
                String msg = cls.getName() + " constructor throw an exception";
                throw new IllegalArgumentException(msg, e.getCause());
            }
        }
        return o;
    }

    /**
     * Stringly typed method for those who mistakenly describe their programming
     * style as dynamic.
     * 
     * @param className
     *            class to get
     * @return class instance (assuming you can spell and concatenate strings
     *         correctly)
     */
    public Object getInstance(String className) {
        try {
            return getInstance(Class.forName(className));
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * The instance number for this instance of InstanceUtils.
     * 
     * @return instance number
     */
    public int instance() {
        return instance(getInstance());
    }

    private int instance(InstanceUtils iu) {
        if (iu == this) {
            return 1;
        }
        return instance(iu.getInstance(InstanceUtils.class)) + 1;
    }

}
