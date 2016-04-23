package ua.gram.controller.state.enemy;

import ua.gram.DDGame;
import ua.gram.controller.state.State;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;

/**
 * Representation of Actor at the moment of time.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class EnemyState extends State<Enemy> {

    protected final EnemyStateManager manager;

    public EnemyState(DDGame game, EnemyStateManager manager) {
        super(game);
        this.manager = manager;
    }

    protected abstract Types.EnemyState getType();

    public EnemyStateManager getManager() {
        return manager;
    }
}
