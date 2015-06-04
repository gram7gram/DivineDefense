package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemySummoner extends Enemy implements Cloneable {

    public EnemySummoner(DDGame game, float[] stats) {
        super(game, stats);
    }

    public Enemy summon() {
        return null;
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }


}
