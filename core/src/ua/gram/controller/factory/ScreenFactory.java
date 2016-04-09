package ua.gram.controller.factory;

import com.badlogic.gdx.Screen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ua.gram.DDGame;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ScreenFactory {

    public static Screen create(DDGame game, String name) {
        name = "ua.gram.view.screen." + name;
        Class<?> clazz;
        try {
            clazz = Class.forName(name);
            Constructor<?> constructor;
            try {
                constructor = clazz.getConstructor(DDGame.class);
                try {
                    return (Screen) constructor.newInstance(game);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    Log.exc("Cannot instansiate class: " + name, e);
                }

            } catch (NoSuchMethodException e) {
                Log.exc("No such method for class: " + name, e);
            }

        } catch (ClassNotFoundException e) {
            Log.exc("No such class: " + name, e);
        }

        return null;
    }
}
