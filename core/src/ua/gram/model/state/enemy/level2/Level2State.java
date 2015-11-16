package ua.gram.model.state.enemy.level2;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.StateInterface;
import ua.gram.model.state.enemy.EnemyState;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Level2State extends EnemyState implements StateInterface<Enemy> {

    public Level2State(DDGame game) {
        super(game);
    }

}
