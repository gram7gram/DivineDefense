package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Map;
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
        enemy.setCurrentLevel1StateType(Animator.Types.SPAWN);

        EnemySpawner spawner = enemy.getSpawner();

        Map map = spawner.getLevel().getMap();

        Vector2 pos = spawnPosition == null
                ? spawner.getSpawnPosition()
                : spawnPosition;
        Vector2 prev = parent == null
                ? Vector2.Zero
                : parent.getPreviousDirection();

        if (checkSpawnPosition(map, pos))
            spawner.setActionPath(enemy, pos, prev);
        else {
            throw new GdxRuntimeException("Cannot spawn child. Requested cell "
                    + Path.toString(pos) + " does not contain nessesary property");
        }

        Gdx.app.log("INFO", enemy + " is spawned at " + Path.toString(pos));

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
        parent = null;
    }

    private boolean checkSpawnPosition(Map map, Vector2 pos) {
        TiledMapTileLayer layer = map.getActiveLayer();
        TiledMapTileLayer.Cell cell = layer.getCell((int) pos.x, (int) pos.y);
        if (cell != null) {
            MapProperties prop = cell.getTile().getProperties();
            return prop.containsKey(map.getPrototype().walkableProperty);
        } else {
            return false;
        }
    }

    public void setSpawnPosition(Vector2 spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public void setParent(Enemy parent) {
        this.parent = parent;
    }

}
