package ua.gram.controller;

import ua.gram.model.ResetableInterface;

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

        if (object instanceof ResetableInterface) {
            ((ResetableInterface) object).resetObject();
        }

        super.writeFields(object);
    }
}
