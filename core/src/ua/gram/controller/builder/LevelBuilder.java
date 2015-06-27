package ua.gram.controller.builder;

import ua.gram.controller.Loader;
import ua.gram.model.Level;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelBuilder {

    public static Level create(int lvl, int difficulty) {
        return new Level(Loader.getWaves(lvl, difficulty));
    }
}
