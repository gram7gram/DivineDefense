package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends Enemy implements Cloneable {

    public EnemySummoner(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public void ability() {
        try {
            this.getSpawner().spawn("EnemySoldier", this.getPosition());
        } catch (CloneNotSupportedException e) {
            Gdx.app.log("WARN", "Could not execute ability for EnemySpawner!");
        }
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }


}
