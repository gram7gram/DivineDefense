package ua.gram.controller;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Json extends com.badlogic.gdx.utils.Json {

    @Override
    public void writeFields(Object object) {
        String className = object.getClass().getCanonicalName();
        if (className.startsWith("ua.gram.")) {
            writeValue("class", className);
        }
        super.writeFields(object);
    }
}
