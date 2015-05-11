package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.view.group.EnemyGroup;

/**
 * <pre>
 * The Runnable that starts at the moment the Enemy reaches the Base.
 * Causes Player's health decrease and purges the Enemy from Stage.
 * </pre>
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyRemover implements Runnable {

    private final DDGame game;
    private final EnemySpawner spawner;
    private final EnemyGroup group;

    public EnemyRemover(DDGame game, EnemySpawner spawner, EnemyGroup group) {
        this.game = game;
        this.group = group;
        this.spawner = spawner;
    }

    /**
     * <pre>
     * Causes damage to Player,as well as removes the Enemy
     * and corresponding HealthBar from Stage.
     * Puts the removed Enemy to corresponding Pool.
     * </pre>
     */
    @Override
    public void run() {
        try {
            customAction();
            group.remove();
            spawner.free(group.getEnemy());
        } catch (IllegalStateException e) {
            Gdx.app.error("EXC", "Player conflict: " + e);
            game.getPlayer().setHealth(0);
        }
    }

    /**
     * <pre>
     * Should be @Override to perform the commands,
     * which user would like to perform at the moment of the Enemy death.
     * </pre>
     */
    public void customAction() {
    }
}
