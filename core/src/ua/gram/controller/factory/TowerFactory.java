package ua.gram.controller.factory;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSecondary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;

import java.util.ArrayList;

/**
 * TODO Configure prototypes.get(index) according to the fraction.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerFactory implements Factory<Tower> {

    private DDGame game;
    private ArrayList<TowerPrototype> prototypes;

    public void setGame(DDGame game) {
        this.game = game;
    }

    @Override
    public Tower create(Class<? extends Tower> type) {
        TowerPrototype tower = prototypes.get(0);
        float[] stats = new float[]{
                tower.tower_lvl,
                tower.upgrade_lvl,
                tower.damage,
                tower.range,
                tower.rate,
                tower.cost
        };
        Tower towerType;
        if (type.equals(TowerPrimary.class)) {
            towerType = new TowerPrimary(game, stats);
        } else if (type.equals(TowerSecondary.class)) {
            towerType = new TowerSecondary(game, stats);
        } else if (type.equals(TowerStun.class)) {
            towerType = new TowerStun(game, stats);
        } else if (type.equals(TowerSpecial.class)) {
            towerType = new TowerSpecial(game, stats);
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
