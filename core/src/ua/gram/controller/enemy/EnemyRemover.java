package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.model.actor.Enemy;
import ua.gram.model.group.EnemyGroup;

/**
 * The Runnable that starts at the moment the Enemy reaches the Base or is killed.
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
        Enemy enemy = group.getEnemy();
        Stage stage = group.getStage();
        spawner.free(enemy);
        group.clearChildren();
        if (group.remove()) {
            int count = 0;
            for (Actor actor1 : stage.getActors()) {
                if (actor1 instanceof Group) {
                    for (Actor actor2 : ((Group) actor1).getChildren()) {
                        if (actor2 instanceof EnemyGroup) {
                            ++count;
                        }
                    }
                }
            }
            Gdx.app.log("INFO", "Stage has " + count + " Enemies left");
        } else {
            Gdx.app.error("ERROR", "Could not remove " + enemy + " from its parent!");
        }
    }

    /**
     * Perform the commands, which user would like
     * to perform at the moment of the Enemy's death.
     */
    public void customAction() {
    }
}
