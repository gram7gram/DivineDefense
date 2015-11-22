package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SpawnState extends InactiveState {

    private float spawnDurationCount;
    private Vector2 spawnPosition;

    public SpawnState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        super.preManage(enemy);
        enemy.setCurrentLevel1StateType(Animator.Types.SPAWN);
        EnemySpawner spawner = enemy.getSpawner();
        spawner.setActionPath(enemy, spawnPosition == null ? spawner.getSpawnPosition() : spawnPosition);
        EnemyAnimationProvider provider = enemy.getAnimationProvider();
        AnimationPool pool = provider.get(
                enemy.getOriginType(),
                enemy.getCurrentLevel1StateType(),
                enemy.getCurrentDirectionType());
        enemy.setAnimation(pool.obtain());
        spawnDurationCount = 0;
        Gdx.app.log("INFO", enemy + " state: " + enemy.getCurrentLevel1StateType());
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        if (spawnDurationCount >= enemy.getSpawnDuration()) {
            manager.swapLevel1State(enemy, manager.getActiveState());
            manager.swapLevel2State(enemy, manager.getWalkingState());
            spawnDurationCount = 0;
        } else {
            spawnDurationCount += delta;
        }
    }

    @Override
    public void postManage(Enemy enemy) {
        spawnDurationCount = 0;
        spawnPosition = null;
    }

    public void setSpawnPosition(Vector2 spawnPosition) {
        this.spawnPosition = spawnPosition;
    }
}
