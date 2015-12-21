package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SpawnState extends InactiveState {

    private float spawnDurationCount;
    private Vector2 spawnPosition;
    private Enemy parent;

    public SpawnState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) throws GdxRuntimeException {
        super.preManage(enemy);
        EnemySpawner spawner = enemy.getSpawner();
        Vector2 initial = spawner.getLevel().getPrototype().initialDirection;

        enemy.setCurrentDirection(parent == null || parent.getCurrentDirection() == null
                ? initial : parent.getCurrentDirection());

        initAnimation(enemy, Animator.Types.SPAWN);

        Vector2 pos = spawnPosition == null
                ? spawner.getSpawnPosition()
                : spawnPosition;

        Vector2 prev = parent == null
                ? Path.opposite(initial)
                : parent.getPreviousDirection();

        spawner.setActionPath(enemy, pos, prev);

        Gdx.app.log("INFO", enemy + " is spawned at " + Path.toString(pos));

        spawnDurationCount = 0;

        enemy.setVisible(true);

        Gdx.app.log("INFO", enemy + " state: " + enemy.getAnimator().getType());
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        if (spawnDurationCount >= enemy.getSpawnDuration()) {
            manager.swapLevel1State(enemy, manager.getActiveState());
            manager.swapLevel2State(enemy, manager.getWalkingState(enemy));
            spawnDurationCount = 0;
        } else {
            spawnDurationCount += delta;
        }
    }

    @Override
    public void postManage(Enemy enemy) {
        spawnDurationCount = 0;
        spawnPosition = null;
        parent = null;
    }

    public void setSpawnPosition(Vector2 spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public void setParent(Enemy parent) {
        this.parent = parent;
    }

}
