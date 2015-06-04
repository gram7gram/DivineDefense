package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemySoldierArmored extends Enemy implements Cloneable {

    public EnemySoldierArmored(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public EnemySoldierArmored clone() throws CloneNotSupportedException {
        return (EnemySoldierArmored) super.clone();
    }

}
