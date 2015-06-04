package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyRunner extends Enemy implements Cloneable {

    public EnemyRunner(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public EnemyRunner clone() throws CloneNotSupportedException {
        return (EnemyRunner) super.clone();
    }
}
