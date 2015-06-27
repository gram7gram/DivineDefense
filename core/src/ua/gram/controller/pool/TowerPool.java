package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.factory.TowerFactory;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSecondary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPool<T extends Tower> extends Pool<Tower> {

    private final Class<? extends ua.gram.model.actor.Tower> type;
    private final TowerFactory container;

    public TowerPool(DDGame game, int capacity, int max, Class<? extends Tower> type) {
        super(capacity, max);
        this.type = type;
        String file = "";
        String towerFilesDir = "data/world/towers/";
        if (type.equals(TowerPrimary.class)) {
            file = towerFilesDir + "primary.json";
        } else if (type.equals(TowerSecondary.class)) {
            file = towerFilesDir + "cannon.json";
        } else if (type.equals(TowerStun.class)) {
            file = towerFilesDir + "stun.json";
        } else if (type.equals(TowerSpecial.class)) {
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
