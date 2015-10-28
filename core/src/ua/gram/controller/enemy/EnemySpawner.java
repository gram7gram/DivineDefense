package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.pool.EnemyPool;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.Level;
import ua.gram.model.actor.enemy.*;
import ua.gram.model.actor.misc.HealthBar;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.map.Map;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.EnemyStateManager;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySpawner {

    private final DDGame game;
    private final GameBattleStage stage_battle;
    private final Level level;
    private final EnemyAnimationProvider animationProvider;
    private final EnemyStateManager stateManager;
    private float count;
    private Pool<Enemy> poolWarrior;
    private Pool<Enemy> poolSoldier;
    private Pool<Enemy> poolSoldierArmored;
    private Pool<Enemy> poolSummoner;
    private Pool<Enemy> poolRunner;
    private LinkedList<String> enemiesToSpawn;

    public EnemySpawner(DDGame game, Level level, GameBattleStage stage) {
        this.game = game;
        this.stage_battle = stage;
        this.level = level;
        stateManager = new EnemyStateManager(game);
        animationProvider = new EnemyAnimationProvider(game.getResources().getSkin());
        Gdx.app.log("INFO", "EnemySpawner is OK");
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
                Gdx.app.error("EXC", "Spawn conflict: " + e
                        + "\r\n" + Arrays.toString(e.getStackTrace()));
            }
        } else {
            count += delta;
        }
    }

    /**
     * Spawns obtained from pool and cloned Enemy,
     * places it at the Spawn position and gives it the path to go.
     * Spawn takes place in Group with the coresponding HealthBar.
     *
     * @param type  the Enemy ancestor to spawn.
     * @param spawn map tile position to spawn at (not in pixels)
     * @throws CloneNotSupportedException - error occcured at cloning.
     * @throws NullPointerException       - type does not belong to known Enemy ancestor.
     */
    public void spawn(String type, Vector2 spawn) throws CloneNotSupportedException, NullPointerException {
        Enemy enemy = this.obtain(type);
        stateManager.init(enemy);
        animationProvider.init(enemy);
        enemy.setSpawner(this);
        enemy.setAnimationProvider(animationProvider);
        stateManager.swapLevel1State(enemy, stateManager.getInactiveState());
        stateManager.swapLevel2State(enemy, stateManager.getIdleState());
        try {
            Skin skin = game.getResources().getSkin();
            enemy.setPosition(spawn.x * DDGame.TILE_HEIGHT, spawn.y * DDGame.TILE_HEIGHT);
            HealthBar bar = new HealthBar(skin, enemy);
            EnemyGroup enemyGroup = new EnemyGroup(skin, enemy, bar);
            enemyGroup.setVisible(true);
            enemy.setGroup(enemyGroup);
            enemy.setBattleStage(stage_battle);
            stage_battle.updateZIndexes(enemyGroup);
            stateManager.swapLevel1State(enemy, stateManager.getSpawnState());
        } catch (Exception e) {
            Gdx.app.error("EXC", "EnemySpawner failed to spawn " + enemy);
        }
    }

    /**
     * Sets the Actions for Enemy to do to walk the path
     * FIX Bigger speed - slower walk of Enemy
     */
    public void setActionPath(final Enemy enemy, Vector2 spawn) {
        Map map = level.getMap();

        Path path = level.getMap().normalizePath(map.getSpawn().getPosition());
        enemy.setPath(path);

        enemy.setCurrentDirection(enemy.getPath().peekNextDirection());
        enemy.setCurrentDirectionType(Path.getType(enemy.getCurrentDirection()));

        stateManager.swapLevel1State(enemy, stateManager.getSpawnState());
        stateManager.swapLevel2State(enemy, stateManager.getIdleState());
    }

    private LinkedList<String> convertList(String[] list) {
        LinkedList<String> enemies = new LinkedList<String>();
        for (String type : list) {
            enemies.add(type);
            if (type.equals("EnemyWarrior") && poolWarrior == null) {
                poolWarrior = new EnemyPool<EnemyWarrior>(game, "EnemyWarrior");
            } else if (type.equals("EnemyRunner") && poolRunner == null) {
                poolRunner = new EnemyPool<EnemyRunner>(game, "EnemyRunner");
            } else if (type.equals("EnemySoldier") && poolSoldier == null) {
                poolSoldier = new EnemyPool<EnemySoldier>(game, "EnemySoldier");
            } else if (type.equals("EnemySoldierArmored") && poolSoldierArmored == null) {
                poolSoldierArmored = new EnemyPool<EnemySoldierArmored>(game, "EnemySoldierArmored");
            } else if (type.equals("EnemySummoner") && poolSummoner == null) {
                poolSummoner = new EnemyPool<EnemySummoner>(game, "EnemySummoner");
            }
        }
        return enemies;
    }

    public void setEnemiesToSpawn(String[] enemiesToSpawn) {
        this.enemiesToSpawn = convertList(enemiesToSpawn);
        Gdx.app.log("INFO", "Enemies for wave " + level.getCurrentWave()
                + " are prepared. Size: " + enemiesToSpawn.length);
    }

    public Pool<Enemy> getPool(Class<? extends Enemy> type) {
        if (type.equals(EnemyWarrior.class)) {
            return poolWarrior;
        } else if (type.equals(EnemySoldier.class)) {
            return poolSoldier;
        } else if (type.equals(EnemySoldierArmored.class)) {
            return poolSoldierArmored;
        } else if (type.equals(EnemyRunner.class)) {
            return poolRunner;
        } else if (type.equals(EnemySummoner.class)) {
            return poolSummoner;
        } else {
            throw new NullPointerException("Unknown pool for: " + type.getSimpleName());
        }
    }

    public void free(Enemy enemy) {
        this.getPool(enemy.getClass()).free(enemy);
//        Gdx.app.log("INFO", enemy + "@" + enemy.hashCode() + " is set free");
    }

    public Enemy obtain(String type) throws CloneNotSupportedException {
        Enemy enemy;
        if (type.equals("EnemyWarrior")) {
            enemy = ((EnemyWarrior) (poolWarrior.obtain())).clone();
        } else if (type.equals("EnemyRunner")) {
            enemy = ((EnemyRunner) (poolRunner.obtain())).clone();
        } else if (type.equals("EnemySoldier")) {
            enemy = ((EnemySoldier) (poolSoldier.obtain())).clone();
        } else if (type.equals("EnemySoldierArmored")) {
            enemy = ((EnemySoldierArmored) (poolSoldierArmored.obtain())).clone();
        } else if (type.equals("EnemySummoner")) {
            enemy = ((EnemySummoner) (poolSummoner.obtain())).clone();
        } else {
            throw new NullPointerException("Couldn't add enemy: " + type);
        }
        return enemy;
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
