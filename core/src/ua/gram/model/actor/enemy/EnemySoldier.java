package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemySoldier extends Enemy implements Cloneable {

    public EnemySoldier(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public EnemySoldier clone() throws CloneNotSupportedException {
        return (EnemySoldier) super.clone();
    }
}
