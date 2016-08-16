package ua.gram.controller.action;

import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.prototype.enemy.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StunAction implements Runnable {

    private final Enemy enemy;
    private final boolean canStun;
    private final String STUNNED = "stunned";

    public StunAction(Enemy enemy, boolean canStun) {
        this.enemy = enemy;
        this.canStun = canStun;
    }

    @Override
    public void run() {
        if (canStun) {
            if (!enemy.getFlags().get(STUNNED)) {
                stun();
            }
        } else {
            unstun();
        }
    }

    private void stun() {
        EnemyPrototype prototype = enemy.getPrototype();

        enemy.getSpeed().decrease();
        enemy.getAnimator().getAnimation().setFrameDuration(prototype.frameDuration * 1.5f);
        enemy.getFlags().set(STUNNED, true);
    }

    private void unstun() {
        EnemyPrototype prototype = enemy.getPrototype();
        EnemyStateManager manager = enemy.getStateManager();
        if (enemy.getStateHolder().getCurrentLevel4State() != null) {
            manager.swapLevel4State(enemy, null);
        }

        enemy.getSpeed().reset();
        enemy.getAnimator().getAnimation().setFrameDuration(prototype.frameDuration);
        enemy.getFlags().set(STUNNED, false);
    }
}
