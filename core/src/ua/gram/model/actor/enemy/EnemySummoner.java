package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.model.prototype.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends Enemy implements Cloneable, AbilityUser {

    private float counter = 0;
    private final float abilityDelay;
    private final float abilityDuration;

    public EnemySummoner(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);
        abilityDelay = prototype.abilityDelay;
        abilityDuration = prototype.abilityDuration;
    }

    @Override
    public void ability() {
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

    @Override
    public float getAbilityDelay() {
        return abilityDelay;
    }

    @Override
    public float getAbilityDuration() {
        return abilityDuration;
    }
}
