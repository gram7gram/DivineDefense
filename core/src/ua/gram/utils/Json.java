package ua.gram.utils;

import com.badlogic.gdx.utils.Disposable;

/**
 * NOTE Do not serialize classes from external libraries
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Json extends com.badlogic.gdx.utils.Json {

    @Override
    public void writeFields(Object object) {
        String className = object.getClass().getCanonicalName();

        if (className.startsWith("ua.gram.")) {
            writeValue("class", className);
        }

        if (object instanceof Disposable) {
            ((Disposable) object).dispose();
        }

        super.writeFields(object);
    }
}
