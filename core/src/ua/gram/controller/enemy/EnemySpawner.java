package ua.gram.controller.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import ua.gram.DDGame;
import ua.gram.controller.pool.EnemyPool;
import ua.gram.controller.stage.BattleStage;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.enemy.EnemySummoner;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.level.Level;
import ua.gram.model.map.WalkablePath;
import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySpawner {

    private final DDGame game;
    private final Level level;
    private final BattleStage stage_battle;
    private final EnemyStateManager stateManager;
    private final EnemyAnimationProvider animationProvider;
    private final Map<String, Pool<Enemy>> identityMap;
    private final Stack<String> enemiesToSpawn;
    private float count;

    public EnemySpawner(DDGame game, Level level, BattleStage stage) {
        this.game = game;
        this.stage_battle = stage;
        this.level = level;

        EnemyPrototype[] prototypes = game.getPrototype().enemies;
        if (prototypes.length == 0)
            throw new NullPointerException("Nothing to register");

        identityMap = new HashMap<String, Pool<Enemy>>(prototypes.length);
        registerAll(prototypes);

        enemiesToSpawn = new Stack<String>();
        stateManager = new EnemyStateManager(game);
        animationProvider = new EnemyAnimationProvider(
                game.getResources().getSkin(),
                prototypes);
        animationProvider.init();

        Log.info("EnemySpawner is OK");
    }

    private void registerAll(EnemyPrototype[] prototypes) {
        for (EnemyPrototype prototype : prototypes) {
            identityMap.put(prototype.name, new EnemyPool(game, prototype.name));
        }
    }

    /**
     * Basic loop that spawns enemies with specified delay.
     * NOTE Level is Finished, if there are no enemies to spawn
     *
     * @param delta Same as Gdx.graphics.getDeltaTime()
     */
    public void update(float delta) {
        float delay = 1;
        if (count >= delay) {
            count = 0;
            try {
                if (!enemiesToSpawn.isEmpty()) {
                    Vector2 spawnPosition = level.getMap().getSpawn().getPosition();

                    spawn(enemiesToSpawn.pop(), spawnPosition, null);

                } else if (!stage_battle.hasEnemiesOnMap() || level.isCleared) {
                    level.getWave().finish();
                }
            } catch (Exception e) {
                Log.exc("EnemySpawner conflict", e);
            }
        } else {
            count += delta;
        }
    }

    /**
     * Spawns obtained from pool and cloned EnemyState,
     * places it at the Spawn positionIndex and gives it the path to go.
     * Spawn takes place in Group with the coresponding HealthBar.
     *
     * @param type  the EnemyState ancestor to spawn.
     * @param spawn map tile positionIndex to spawn at (not in pixels)
     */
    public void spawn(String type, Vector2 spawn, EnemySummoner parent) {
        Enemy enemy;
        try {
            enemy = obtain(type);
        } catch (Exception e) {
            Log.exc("Unable to obtain [" + type + "] from pool", e);
            return;
        }
        stateManager.init(enemy);
        enemy.setSpawner(this);
        stateManager.swapLevel1State(enemy, stateManager.getInactiveState());
        stateManager.swapLevel2State(enemy, stateManager.getIdleState());
        try {
            enemy.setPosition(spawn.x * DDGame.TILE_HEIGHT, spawn.y * DDGame.TILE_HEIGHT);
            EnemyGroup enemyGroup = new EnemyGroup(game, enemy);
            enemyGroup.setVisible(true);
            enemy.setGroup(enemyGroup);
            stage_battle.updateZIndexes(enemyGroup);
            if (parent != null) {
                enemy.setParentEnemy(parent);
            }
            stateManager.getSpawnState().setSpawnPosition(spawn);
            stateManager.swapLevel1State(enemy, stateManager.getSpawnState());
        } catch (Exception e) {
            Log.exc("EnemySpawner failed to spawn " + enemy, e);
        }
    }

    /**
     * Sets the Actions for EnemyState to do to walk the path
     * FIX Bigger speed - slower walk of EnemyState
     */
    public void setActionPath(final Enemy enemy, Vector2 spawn, Vector2 previous) {
        WalkablePath path = level.getMap().normalizePath(previous, spawn);
        enemy.setPath(path);
        if (enemy.getCurrentDirection() == null) {
            Vector2 current = path.peekNextDirection();
            enemy.setCurrentDirection(current);
        }
    }

    public void setEnemiesToSpawn(String[] types) {
        for (String type : types) enemiesToSpawn.push(type);
        Log.info("Enemies for wave " + level.getCurrentWaveIndex()
                + " are prepared. Size: " + enemiesToSpawn.size());
    }

    public Pool<Enemy> getPool(String type) {
        Pool<Enemy> pool = null;
        for (String name : identityMap.keySet()) {
            if (Objects.equals(name, type)) {
                pool = identityMap.get(name);
                break;
            }
        }

        if (pool == null)
            throw new NullPointerException("Couldn't build tower: " + type);

        return pool;
    }

    public void free(Enemy enemy) {
        getPool(enemy.getName()).free(enemy);
        Log.info(enemy + " is set free");
    }

    public Enemy obtain(String type) {
        return getPool(type).obtain();
    }

    public EnemyAnimationProvider getAnimationProvider() {
        return animationProvider;
    }

    public EnemyStateManager getStateManager() {
        return stateManager;
    }

    public Level getLevel() {
        return level;
    }
}
