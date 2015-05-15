package ua.gram.controller.enemy;

import ua.gram.model.group.EnemyGroup;

/**
 * The Runnable that starts at the moment the Enemy reaches the Base.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyRemover implements Runnable {

    private final EnemySpawner spawner;
    private final EnemyGroup group;

    public EnemyRemover(EnemySpawner spawner, EnemyGroup group) {
        this.group = group;
        this.spawner = spawner;
    }

    /**
     * Puts the removed Enemy to corresponding Pool.
     */
    @Override
    public void run() {
        customAction();
        spawner.free(group.getEnemy());
        group.remove();
    }

    /**
     * Should be @Overriden to perform the commands,
     * which user would like to perform at the moment of the Enemy's death.
     */
    public void customAction() {
    }
}
