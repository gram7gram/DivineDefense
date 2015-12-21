package ua.gram.controller.enemy;

import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.StateInterface;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyStateSwapper implements Runnable {

    private Enemy enemy;
    private StateInterface state1;
    private StateInterface state2;
    private int level;

    public EnemyStateSwapper update(Enemy enemy, StateInterface state1, StateInterface state2, int level) {
        this.enemy = enemy;
        this.state1 = state1;
        this.state2 = state2;
        this.level = level;

        return this;
    }

    @Override
    public void run() {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, state1, state2, level);
    }
}
