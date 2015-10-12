package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyRemover;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.state.enemy.level2.Level2State;

import java.util.Random;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class DeathState extends InactiveState {

    public DeathState(DDGame game, Enemy actor) {
        super(game, actor);
    }

    @Override
    public void preManage() {
        Gdx.app.log("INFO", getActor().getClass().getSimpleName()
                + "#" + getActor().hashCode() + " is dead");
    }

    @Override
    public void manage() {
        getGame().getPlayer().addCoins(getActor().reward);
        float value = new Random().nextFloat();

        //10% chance to get a gem
        if (value >= .45 && value < .55) {
            getGame().getPlayer().addGems(1);
            Gdx.app.log("INFO", "Player got 1 gem");
        }

    }

    @Override
    public void postManage() {

    }
}
