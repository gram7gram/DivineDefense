package ua.gram.model.prototype;

import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class UIPrototype extends Prototype {
    public ua.gram.model.prototype.ui.window.WindowPrototype[] windows;

    public ua.gram.model.prototype.ui.window.WindowPrototype getWindow(String type) {
        ua.gram.model.prototype.ui.window.WindowPrototype prototype = null;
        for (ua.gram.model.prototype.ui.window.WindowPrototype p : windows) {
            if (p.name.equals(type)) {
                prototype = p;
                break;
            }
        }
        if (prototype == null)
            throw new GdxRuntimeException("No WindowPrototype refistered with name: " + type);
        return prototype;
    }
}
