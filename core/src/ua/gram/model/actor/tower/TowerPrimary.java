package ua.gram.model.actor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.model.actor.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPrimary extends Tower implements Cloneable, Pool.Poolable {

    public TowerPrimary(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public TowerPrimary clone() throws CloneNotSupportedException {
        return (TowerPrimary) super.clone();
    }

    @Override
    public void reset() {
        this.tower_lvl = 1;
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " was reset");
    }

}
