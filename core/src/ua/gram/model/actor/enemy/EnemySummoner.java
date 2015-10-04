package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.model.actor.AbilityUser;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends Enemy implements Cloneable, AbilityUser {

    private float counter = 0;

    public EnemySummoner(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public void ability() {
//        this.getActions().clear();
        try {
            Vector2 posToSpawn = this.getPosition();
            Vector2 next = path.getNextPosition(posToSpawn);
            this.getSpawner().spawn("EnemySoldier", next);
            Gdx.app.log("INFO", "Ability!");
        } catch (Exception e) {
            Gdx.app.log("WARN", "Could not execute ability for Summoner!");
        }
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
        if (counter > 3) {
            ability();
            counter = 0;
        } else {
            counter += delta;
        }
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }


}
