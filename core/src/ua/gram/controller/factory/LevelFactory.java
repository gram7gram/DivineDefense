package ua.gram.controller.factory;

import ua.gram.model.Level;
import ua.gram.model.actor.Enemy;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelFactory implements Factory<Level> {

    public ArrayList<ArrayList<Class<? extends Enemy>>> waves;

    @Override
    public Level create(Class<? extends Level> type) {
        if (waves == null) {
            throw new NullPointerException("Factory could not create Level with empty waves");
        }
        return new Level(waves);
    }
}
