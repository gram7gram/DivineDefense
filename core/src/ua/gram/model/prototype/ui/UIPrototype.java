package ua.gram.model.prototype.ui;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.model.prototype.Prototype;
import ua.gram.model.prototype.SoundStatePrototype;
import ua.gram.model.prototype.ui.window.WindowPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class UIPrototype extends Prototype {
    public WindowPrototype[] windows;
    public SoundStatePrototype[] sounds;
    public SoundStatePrototype[] music;

    public SoundStatePrototype getMusicByState(String state) {
        SoundStatePrototype prototype = null;
        for (SoundStatePrototype p : music) {
            if (p.state.equals(state)) {
                prototype = p;
                break;
            }
        }
        if (prototype == null)
            throw new GdxRuntimeException("No SoundStatePrototype registered with name: " + state);
        return prototype;
    }

    public WindowPrototype getWindow(String type) {
        WindowPrototype prototype = null;
        for (WindowPrototype p : windows) {
            if (p.name.equals(type)) {
                prototype = p;
                break;
            }
        }
        if (prototype == null)
            throw new GdxRuntimeException("No WindowPrototype registered with name: " + type);
        return prototype;
    }
}
