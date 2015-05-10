package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.factory.TowerFactory;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPool<T extends ua.gram.model.actor.Tower> extends Pool<ua.gram.model.actor.Tower> {

    private final Class<? extends ua.gram.model.actor.Tower> type;
    private final String towerFilesDir = "data/world/towers/";
    private final TowerFactory container;

    public TowerPool(DDGame game, int capacity, int max, Class<? extends ua.gram.model.actor.Tower> type) {
        super(capacity, max);
        this.type = type;
        String file = "";
        if (type.equals(ua.gram.model.actor.tower.TowerPrimary.class)) {
            file = towerFilesDir + "primary.json";
        } else if (type.equals(ua.gram.model.actor.tower.TowerCannon.class)) {
            file = towerFilesDir + "cannon.json";
        } else if (type.equals(ua.gram.model.actor.tower.TowerStun.class)) {
            file = towerFilesDir + "stun.json";
        } else if (type.equals(ua.gram.model.actor.tower.TowerSpecial.class)) {
            file = towerFilesDir + "special.json";
        } else {
            throw new NullPointerException("Couldn't get configuration for: " + type.getSimpleName());
        }
        container = game.getFactory(file, TowerFactory.class);
        container.setGame(game);
        Gdx.app.log("INFO", "Pool for " + type.getSimpleName() + " is created.");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T newObject() {
        return (T) container.create(type);
    }
}
