package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemySoldier extends Enemy implements Cloneable, Pool.Poolable {

    public EnemySoldier(DDGame game, float[] stats) {
        super(game, stats);
        this.setSize(animationWidth, animationHeight);
        this.setBounds(getX(), getY(), animationWidth, animationHeight);
    }

    @Override
    public void reset() {
        this.health = defaultHealth;
        this.speed = defaultSpeed;
        this.armor = defaultArmor;
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " was reset");
    }

    @Override
    public EnemySoldier clone() throws CloneNotSupportedException {
        return (EnemySoldier) super.clone();
    }
}
