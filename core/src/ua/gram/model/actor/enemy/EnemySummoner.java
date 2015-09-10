package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.actor.AbilityUser;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends Enemy implements Cloneable, AbilityUser {

    private final float delayAbility = 3;
    private float counter = 0;

    public EnemySummoner(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public void ability() {
//        try {
//            Vector2 posToSpawn = this.getPosition();
//            Vector2 nextDirection = this.getPath().getNextDirection(posToSpawn);
//            this.getSpawner().spawn("EnemySoldier"
//        } catch (CloneNotSupportedException e) {
//            Gdx.app.log("WARN", "Could not execute ability for Summoner!");
//        }
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
        if (Float.compare(counter, delayAbility) == 0) {
            ability();
            counter = 0;
        } else
            counter += delta;
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }


}
