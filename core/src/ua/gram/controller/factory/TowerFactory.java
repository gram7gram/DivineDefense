package ua.gram.controller.factory;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.Player;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerFactory implements AbstractFactory<ua.gram.model.actor.Tower> {

    private DDGame game;
    private ArrayList<TowerPrototype> prototypes;

    public void setGame(DDGame game) {
        this.game = game;
    }

    @Override
    public ua.gram.model.actor.Tower create(Class<? extends ua.gram.model.actor.Tower> type) {
        TowerPrototype tower = prototypes.get(Player.PLAYER_FRACTION.equals("Angels") ? 0 : 1);
        float[] stats = new float[]{
                tower.tower_lvl,
                tower.upgrade_lvl,
                tower.damage,
                tower.range,
                tower.rate,
                tower.cost
        };
        ua.gram.model.actor.Tower towerType = null;
        if (type.equals(ua.gram.model.actor.tower.TowerPrimary.class)) {
            towerType = new ua.gram.model.actor.tower.TowerPrimary(game, stats);
        } else if (type.equals(ua.gram.model.actor.tower.TowerCannon.class)) {
            towerType = new ua.gram.model.actor.tower.TowerCannon(game, stats);
        } else if (type.equals(ua.gram.model.actor.tower.TowerStun.class)) {
            towerType = new ua.gram.model.actor.tower.TowerStun(game, stats);
        } else if (type.equals(ua.gram.model.actor.tower.TowerSpecial.class)) {
            towerType = new ua.gram.model.actor.tower.TowerSpecial(game, stats);
        } else {
            throw new NullPointerException("Factory couldn't create: " + type.getSimpleName());
        }
        Gdx.app.log("INFO", "Factory creates " + type.getSimpleName());
        return towerType;
    }

    static class TowerPrototype {

        public int tower_lvl;
        public int upgrade_lvl;
        public float damage;
        public float range;
        public float rate;
        public int cost;
    }
}
