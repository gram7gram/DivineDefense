package ua.gram.model.actor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.tower.TowerAnimationController;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerSpecial extends ua.gram.model.actor.Tower implements Cloneable, Pool.Poolable {

    public TowerSpecial(DDGame game, float[] stats) {
        super(game, stats);
        controller = new TowerAnimationController(game.getResources().getAtlas(Resources.TOWERS_ATLAS), this);
    }

    @Override
    public TowerSpecial clone() throws CloneNotSupportedException {
        return (TowerSpecial) super.clone();
    }

    @Override
    public void reset() {
        this.strategy = Strategy.STRONGEST;
        this.tower_lvl = 1;
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " was reset");
    }

}
