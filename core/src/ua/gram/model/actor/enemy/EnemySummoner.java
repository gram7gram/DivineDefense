package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemySummoner extends Enemy implements Cloneable, Pool.Poolable {

    public EnemySummoner(DDGame game, float[] stats) {
        super(game, stats);
        this.setSize(animationWidth, animationHeight);
        this.setBounds(getX(), getY(), animationWidth, animationHeight);
    }

    public Enemy summon() {
        return null;
    }

    @Override
    public void reset() {
        this.health = defaultHealth;
        this.speed = defaultSpeed;
        this.armor = defaultArmor;
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " was reset");
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }

}
