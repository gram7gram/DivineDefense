package ua.gram.controller.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import ua.gram.DDGame;
import ua.gram.controller.animation.enemy.EnemyAnimationProvider;
import ua.gram.controller.pool.EnemyPool;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.Initializer;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.enemy.EnemySummoner;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.level.Level;
import ua.gram.model.map.WalkablePath;
import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySpawner implements Initializer {

    private final DDGame game;
    private final Level level;
    private final BattleStage battleStage;
    private final EnemyStateManager stateManager;
    private final EnemyAnimationProvider animationProvider;
    private final Map<String, Pool<Enemy>> identityMap;
    private final Stack<String> enemiesToSpawn;
    private float count;

    public EnemySpawner(DDGame game, Level level, BattleStage stage) {
        this.game = game;
        this.battleStage = stage;
        this.level = level;

        EnemyPrototype[] prototypes = game.getPrototype().enemies;
        if (prototypes.length == 0)
            throw new NullPointerException("Nothing to register");

        identityMap = new HashMap<String, Pool<Enemy>>(prototypes.length);

        registerAll(prototypes);

        Skin skin = game.getResources().getSkin();
        enemiesToSpawn = new Stack<String>();
        stateManager = new EnemyStateManager(game);
        animationProvider = new EnemyAnimationProvider(skin, prototypes);

        Log.info("EnemySpawner is OK");
    }

    @Override
    public void init() {
        stateManager.init();
        animationProvider.init();
    }

    public void spawn(String type, Vector2 spawn, EnemySummoner parent) {
        Enemy enemy = obtain(type);
        enemy.setSpawner(this);

        stateManager.swapLevel1State(enemy, stateManager.getInactiveState());
        stateManager.swapLevel2State(enemy, stateManager.getIdleState());

        EnemyGroup enemyGroup = new EnemyGroup(game, enemy);
        enemy.setGroup(enemyGroup);

        try {
            battleStage.updateZIndexes(enemyGroup);
            if (parent != null) {
                enemy.setParentEnemy(parent);
                enemy.getDirectionHolder().copy(parent.getDirectionHolder());
            }
            stateManager.getSpawnState().setSpawnIndex(spawn);
            stateManager.swapLevel1State(enemy, stateManager.getSpawnState());
        } catch (Exception e) {
            Log.exc("EnemySpawner failed to spawn " + enemy, e);
            enemy.remove();
        }
    }

    private void registerAll(EnemyPrototype[] prototypes) {
        for (EnemyPrototype prototype : prototypes) {
            identityMap.put(prototype.name, new EnemyPool(game, prototype.name));
        }
    }

    public void update(float delta) {
        float delay = 1;
        if (count >= delay) {
            count = 0;
            if (!enemiesToSpawn.isEmpty()) {
                Vector2 spawnPosition = level.getMap().getSpawn().getPosition();

                spawn(enemiesToSpawn.pop(), spawnPosition, null);

            } else if (canFinishWave()) {
                level.getWave().finish();
            }
        } else {
            count += delta;
        }
    }

    private boolean canFinishWave() {
        return !battleStage.hasEnemiesOnMap() || level.isCleared();
    }

    public void createPath(final Enemy enemy, Vector2 spawn, Vector2 previous) {
        WalkablePath path = level.getMap().normalizePath(previous, spawn);
        enemy.setPath(path);
        Vector2 current = path.peekNextDirection();
        enemy.getDirectionHolder().setCurrentDirection(current.x, current.y);
    }

    public void setEnemiesToSpawn(String[] types) {
        for (String type : types) {
            enemiesToSpawn.push(type);
        }
        Log.info("Enemies for wave " + level.getCurrentWaveIndex()
                + " are prepared. Size: " + enemiesToSpawn.size());
    }

    public Pool<Enemy> getPool(String type) {
        Pool<Enemy> pool = null;
        for (String name : identityMap.keySet()) {
            if (name.equals(type)) {
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
