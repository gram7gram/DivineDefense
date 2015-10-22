package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level1.InactiveState;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SpawnState extends InactiveState {

    private float spawnDurationCount;

    public SpawnState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        super.preManage(enemy);
        EnemyAnimationProvider provider = enemy.getSpawner().getAnimationProvider();
        enemy.setAnimation(provider.get(
                enemy.getOriginType(),
                Animator.Types.SPAWN,
                enemy.getCurrentDirectionType())
        .obtain());
        Gdx.app.log("INFO", enemy + " spawns");
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        if (spawnDurationCount >= 5) {
            EnemyStateManager manager = enemy.getSpawner().getStateManager();
            manager.swapLevel2State(enemy, manager.getWalkingState());
            manager.swapLevel1State(enemy, manager.getActiveState());
            spawnDurationCount = 0;
        } else {
            spawnDurationCount += delta;
        }
    }

    @Override
    public void postManage(Enemy enemy) {

    }
}
