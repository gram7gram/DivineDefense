package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.state.enemy.level2.Level2State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class FinishState extends InactiveState {

    public FinishState(DDGame game, Enemy enemy) {
        super(game, enemy);
    }

    @Override
    public void preManage() {
        Gdx.app.log("INFO", getActor().getClass().getSimpleName()
                + "#" + getActor().hashCode() + " reaches the Base");
    }

    @Override
    public void manage() {
        EnemySpawner spawner = getActor().getSpawner();
        EnemyGroup group = getActor().getEnemyGroup();
        Enemy enemy = group.getEnemy();

        enemy.clearActions();
        spawner.free(enemy);

        if (group.remove()) group.clear();
        else Gdx.app.error("ERROR", "Could not remove " + enemy + " from its parent!");

        getGame().getPlayer().decreaseHealth();

        getActor().getStateManager().swapLevel1State(null);
    }
}
