package ua.gram.controller;

import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Counters implements Disposable {

    private final HashMap<String, Float> map;

    public Counters() {
        map = new HashMap<>(3);
    }

    public Counters set(String name, float value) {
        synchronized (map) {
            map.put(name, value);
        }

        return this;
    }

    public float get(String name) {
        synchronized (map) {
            if (!map.containsKey(name)) {
                set(name, 0);
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
