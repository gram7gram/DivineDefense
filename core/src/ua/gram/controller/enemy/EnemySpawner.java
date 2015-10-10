package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.pool.EnemyPool;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.Level;
import ua.gram.model.actor.enemy.*;
import ua.gram.model.actor.misc.HealthBar;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.map.Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemySpawner {

    private final DDGame game;
    private final GameBattleStage stage_battle;
    private final Level level;
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
                    spawn(enemiesToSpawn.pop(), level.getMap().getSpawn().getPosition());
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
     * @param spawn map tile position to spawn at.
     * @throws CloneNotSupportedException - error occcured at cloning.
     * @throws NullPointerException       - type does not belong to known Enemy ancestor.
     */
    public void spawn(String type, Vector2 spawn) throws CloneNotSupportedException, NullPointerException {
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
        enemy.setPosition(
                spawn.x * DDGame.TILE_HEIGHT,
                spawn.y * DDGame.TILE_HEIGHT
        );
        enemy.setSpawner(this);
        Map map = level.getMap();
        enemy.setPath(map.getPath());
        EnemyGroup enemyGroup = new EnemyGroup(enemy,
                new HealthBar(game.getResources().getSkin(), enemy)
        );
        ArrayList<Vector2> path;
        if (spawn != map.getSpawn().getPosition()) {
            path = map.getDirectionsFrom(spawn);
        } else
            path = this.normalizePathFrom(spawn);
        this.setActionPath(enemyGroup, path);
        enemyGroup.setVisible(true);
        stage_battle.updateZIndexes(enemyGroup);
    }

    /**
     * Get Direction which ACtor should go to reach Base.
     *
     * @param start start position
     * @return list of directions
     */
    public ArrayList<Vector2> normalizePathFrom(Vector2 start) {
        return level.getMap().normalizePath(start).getDirections();
    }

    /**
     * Sets the Actions for Enemy to do to walk the path
     * <p/>
     * FIXME Bigger speed - slower walk of Enemy
     *
     * @param path - list of directions
     */
    private void setActionPath(EnemyGroup group, ArrayList<Vector2> path) {
        Enemy enemy = group.getEnemy();
        enemy.setBattleStage(stage_battle);
        enemy.setAnimationController(new EnemyAnimationController(game.getResources().getSkin(), enemy));
        enemy.setAnimation(enemy.getAnimationController().getUpAnimation());
        SequenceAction pathToGo = new SequenceAction();
        pathToGo.addAction(Actions.show());//spawns enemy
        Vector2 prevDir = Vector2.Zero;
        for (final Vector2 dir : path) {
            Action action;
            if (!dir.equals(prevDir)) {
                action = new SequenceAction(
                        Actions.run(new EnemyAnimationChanger(dir, enemy)),
                        Actions.moveBy(
                                dir.x * DDGame.TILE_HEIGHT,
                                dir.y * DDGame.TILE_HEIGHT,
                                enemy.speed)
                );
            } else {
                action = Actions.moveBy(
                        dir.x * DDGame.TILE_HEIGHT,
                        dir.y * DDGame.TILE_HEIGHT,
                        enemy.speed);
            }
            prevDir = new Vector2(dir);
            pathToGo.addAction(action);
        }
        pathToGo.addAction(
                Actions.parallel(
                        Actions.hide(),
                        Actions.run(new EnemyRemover(this, group) {
                            @Override
                            public void customAction() {
                                Gdx.app.log("INFO", "Enemy reached the Base");
                                game.getPlayer().decreaseHealth();
                            }
                        }))
        );
        enemy.addAction(pathToGo);
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
        Gdx.app.log("INFO", "Enemies for wave " + level.getCurrentWave() + " are prepared. Size: " + enemiesToSpawn.length);
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

}
