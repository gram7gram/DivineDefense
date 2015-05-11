package ua.gram.model.actor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.tower.TowerAnimationController;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPrimary extends ua.gram.model.actor.Tower implements Cloneable, Pool.Poolable {

    public TowerPrimary(DDGame game, float[] stats) {
        super(game, stats);
        controller = new TowerAnimationController(game.getResources().getAtlas(Resources.TOWERS_ATLAS), this);
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
