package ua.gram.controller;

import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Flags implements Disposable {

    private final HashMap<String, Boolean> map;

    public Flags() {
        map = new HashMap<>(3);
    }

    public Flags set(String name, boolean value) {
        synchronized (map) {
            map.put(name, value);
        }

        return this;
    }

    public boolean get(String name) {
        synchronized (map) {
            if (!map.containsKey(name)) {
                set(name, false);
            }
        }
        return map.get(name);
    }

    @Override
    public void dispose() {
        synchronized (map) {
            map.clear();
        }
    }
}
