package ua.gram.controller.factory;

import ua.gram.DDGame;
import ua.gram.model.group.LevelTile;
import ua.gram.model.group.LockedLevelTile;
import ua.gram.model.prototype.LevelPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelTileFactory {

    public static LevelTile create(final DDGame game, final LevelPrototype prototype) {
        if (prototype.level <= game.getPlayer().getLastUnlockedLevel()) {
            return new LevelTile(game, prototype);
        } else {
            return new LockedLevelTile(game, prototype);
        }
    }
}
