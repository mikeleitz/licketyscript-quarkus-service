package com.leadtechnologist.licketyscript.utils;

/**
 * Stores the actual json payload.
 *
 * This is a supplement to the parsed Java objects.
 *
 * @author leitz@mikeleitz.com
 */
public class JsonThreadLocal {
    public static final ThreadLocal<String> jsonThreadLocal = new ThreadLocal();

    public static void set(String jsonString) {
        jsonThreadLocal.set(jsonString);
    }

    public static void unset() {
        jsonThreadLocal.remove();
    }

    public static String get() {
        return jsonThreadLocal.get();
    }

}
