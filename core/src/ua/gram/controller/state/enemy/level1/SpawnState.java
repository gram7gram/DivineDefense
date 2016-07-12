package ua.gram.controller.state.enemy.level1;

import com.badlogic.gdx.math.Vector2;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SpawnState extends InactiveState {

    private Vector2 spawnPosition;

    public SpawnState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.SPAWN;
    }

    @Override
    public void preManage(Enemy enemy) {
        if (enemy.hasParentEnemy()) {
            minionSpawn(enemy);
        } else {
            normalSpawn(enemy);
        }

        getManager().getAnimationChanger()
                .update(enemy, getType());

        super.preManage(enemy);

        enemy.setSpawnDurationCount(0);
        enemy.setVisible(true);

        Log.info(enemy + " is spawned at " + Path.toString(
                enemy.getDirectionHolder().getCurrentPositionIndex()));
    }

    private void normalSpawn(Enemy enemy) {
        EnemySpawner spawner = enemy.getSpawner();
        Vector2 initial = spawner.getLevel().getPrototype().initialDirection;
        enemy.getDirectionHolder().setCurrentDirection(initial.x, initial.y);
        Vector2 prev = Path.opposite(initial);
        spawner.setActionPath(enemy, spawnPosition, prev);
    }

    private void minionSpawn(Enemy enemy) {
        EnemySpawner spawner = enemy.getSpawner();
        Vector2 prev = enemy.getDirectionHolder().getPreviousDirection();
        spawner.setActionPath(enemy, spawnPosition, prev);
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        if (enemy.getSpawnDurationCount() >= enemy.getSpawnDuration()) {
            manager.swapLevel1State(enemy, manager.getActiveState());
            manager.swapLevel2State(enemy, manager.getWalkingState(enemy));
            enemy.setSpawnDurationCount(0);
        } else {
            enemy.addSpawnDurationCount(delta);
        }
    }

    @Override
    public void postManage(Enemy enemy) {
        super.postManage(enemy);
        enemy.setSpawnDurationCount(0);
    }

    public void setSpawnPosition(Vector2 spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

}
