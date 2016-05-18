package ua.gram.controller.factory;

import ua.gram.DDGame;
import ua.gram.model.level.FinalLevel;
import ua.gram.model.level.Level;
import ua.gram.model.level.TutorialLevel;
import ua.gram.model.prototype.level.LevelPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelFactory {

    public static Level create(DDGame game, LevelPrototype prototype) {
        Type type = getType(prototype.type);
        Log.info("Creating " + type.name() + " level...");
        switch (type) {
            case TUTORIAL:
                return new TutorialLevel(game, prototype, type);
            case FINAL:
                return new FinalLevel(game, prototype, type);
            case DEFAULT:
                return new Level(game, prototype, type);
            default:
                throw new IllegalArgumentException("Cannot create " + type.name() + " level type");
        }
    }

    public static Type getType(String type) {
        if (type == null) return Type.DEFAULT;

        switch (type) {
            case "FINAL":
                return Type.FINAL;
            case "TUTORIAL":
                return Type.TUTORIAL;
            case "DEFAULT":
                return Type.DEFAULT;
            default:
                throw new IllegalArgumentException("Cannot get type for " + type + " level");
        }
    }

    public enum Type {
        TUTORIAL, FINAL, DEFAULT
    }
}
