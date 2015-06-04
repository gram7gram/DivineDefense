package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyWarrior extends Enemy implements Cloneable {

    public EnemyWarrior(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public EnemyWarrior clone() throws CloneNotSupportedException {
        return (EnemyWarrior) super.clone();
    }

}
