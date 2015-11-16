package ua.gram.model.state.enemy;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyStateSwapper;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.AbstractState;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class EnemyState extends AbstractState<Enemy> {

    protected EnemyStateSwapper stateSwapper;

    public EnemyState(DDGame game) {
        super(game);
        stateSwapper = new EnemyStateSwapper();
    }

    public EnemyStateSwapper getStateSwapper() {
        return stateSwapper;
    }
}
