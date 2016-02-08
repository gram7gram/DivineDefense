package ua.gram.controller;

import java.lang.reflect.Field;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StringProcessor {

    public static String processString(Object object, String text) {
        if (text == null) throw new NullPointerException("NULL passed to string processor");

        if (!text.contains("{{")) return text;

        for (Field field : object.getClass().getFields()) {
            Class type = field.getType();
            if (canBeProcessed(type)) {
                try {
                    String value = field.get(object) == null ? "?" : field.get(object).toString();
                    text = text.replace("{{ " + field.getName() + " }}", value);
                } catch (IllegalAccessException e) {
                    //ignore
                }
            }
        }
        return text;
    }

    private static boolean canBeProcessed(Class type) {
        return type.equals(String.class)
                || type.equals(Integer.class)
                || type.equals(Boolean.class);
    }
}
