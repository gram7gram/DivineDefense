package ua.gram.controller.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.pool.EnemyPool;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.EnemyPath;
import ua.gram.model.Level;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySpawner {

    private final DDGame game;
    private final Level level;
    private final GameBattleStage stage_battle;
    private final EnemyStateManager stateManager;
    private final EnemyAnimationProvider animationProvider;
    private final Map<String, Pool<Enemy>> identityMap;
    private final Stack<String> enemiesToSpawn;
    private float count;

    public EnemySpawner(DDGame game, Level level, GameBattleStage stage) {
        this.game = game;
        this.stage_battle = stage;
        this.level = level;

        EnemyPrototype[] prototypes = game.getPrototype().enemies;
        if (prototypes.length == 0)
            throw new NullPointerException("Nothing to register");

        identityMap = Collections.synchronizedMap(new HashMap<>(prototypes.length));
        registerAll(prototypes);

        enemiesToSpawn = new Stack<>();
        stateManager = new EnemyStateManager(game);
        animationProvider = new EnemyAnimationProvider(
                game.getResources().getSkin(),
                prototypes);

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
                    this.spawn(enemiesToSpawn.pop(), spawnPosition);
                } else if (!stage_battle.hasEnemiesOnMap() || level.isCleared) {
                    level.getWave().finish();
                }
            } catch (Exception e) {
                Log.exc("Spawn conflict", e);
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
     * @throws CloneNotSupportedException - error occcured at cloning.
     * @throws NullPointerException       - type does not belong to known EnemyState ancestor.
     */
    public void spawn(String type, Vector2 spawn) throws CloneNotSupportedException, NullPointerException {
        Enemy enemy;
        try {
            enemy = this.obtain(type);
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
            stateManager.swapLevel1State(enemy, stateManager.getSpawnState());
        } catch (Exception e) {
            Log.exc("EnemySpawner failed to spawn " + enemy, e);
        }
    }

    /**
     * Spawns obtained from pool and cloned EnemyState,
     * places it at the Spawn positionIndex and gives it the path to go.
     * Spawn takes place in Group with the coresponding HealthBar.
     *
     * @param type  the EnemyState ancestor to spawn.
     * @param spawn map tile positionIndex to spawn at (not in pixels)
     * @throws CloneNotSupportedException - error occcured at cloning.
     * @throws NullPointerException       - type does not belong to known EnemyState ancestor.
     */
    public void spawnChild(Enemy parent, String type, Vector2 spawn) throws CloneNotSupportedException, NullPointerException {
        Enemy enemy;
        try {
            enemy = this.obtain(type);
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
            stateManager.getSpawnState().setSpawnPosition(spawn);
            stateManager.getSpawnState().setParent(parent);
            stateManager.swapLevel1State(enemy, stateManager.getSpawnState());
        } catch (Exception e) {
            Log.exc("EnemySpawner failed to spawn " + parent + "'s child " + enemy, e);
        }
    }

    /**
     * Sets the Actions for EnemyState to do to walk the path
     * FIX Bigger speed - slower walk of EnemyState
     */
    public void setActionPath(final Enemy enemy, Vector2 spawn, Vector2 previous) {
        EnemyPath path = level.getMap().normalizePath(previous, spawn);
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
        Optional<String> entity = identityMap.keySet().stream()
                .filter(name -> Objects.equals(name, type))
                .findFirst();

        if (!entity.isPresent())
            throw new NullPointerException("Couldn't build tower: " + type);

        return identityMap.get(entity.get());
    }

    public void free(Enemy enemy) {
        this.getPool(enemy.getName()).free(enemy);
        Log.info(enemy + " is set free");
    }

    public Enemy obtain(String type) throws CloneNotSupportedException {
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

    public Vector2 getSpawnPosition() {
        return level.getMap().getSpawn().getPosition();
    }
}
