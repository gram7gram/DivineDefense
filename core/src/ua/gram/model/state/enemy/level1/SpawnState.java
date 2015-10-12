package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level2.Level2State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class SpawnState extends InactiveState {

    private float spawnDurationCount;

    public SpawnState(DDGame game, Enemy actor) {
        super(game, actor);
    }

    @Override
    public void preManage() {
        super.preManage();
        Gdx.app.log("INFO", getActor().getClass().getSimpleName()
                + "#" + getActor().hashCode() + " spawns");
    }

    @Override
    public void manage() {
        float spawnDuration = 1;
        if (spawnDurationCount >= spawnDuration) {
            EnemyStateManager manager = getActor().getStateManager();
            manager.swapLevel1State(manager.getActiveState());
            manager.swapLevel2State(manager.getWalkingState());
            spawnDurationCount = 0;
        } else {
            ++spawnDurationCount;
        }
    }
}
