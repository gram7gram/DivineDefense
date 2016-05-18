package ua.gram.model.level;

import ua.gram.DDGame;
import ua.gram.controller.factory.LevelFactory;
import ua.gram.model.prototype.level.LevelPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TutorialLevel extends Level {

    public TutorialLevel(DDGame game, LevelPrototype prototype, LevelFactory.Type type) {
        super(game, prototype, type);
    }
}
