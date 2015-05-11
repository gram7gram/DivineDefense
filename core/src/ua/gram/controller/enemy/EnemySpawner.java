package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.pool.EnemyPool;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.Level;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.HealthBar;
import ua.gram.model.actor.enemy.*;
import ua.gram.view.group.EnemyGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemySpawner {

    private final DDGame game;
    private final ArrayList<Vector2> path;
    private final GameBattleStage stage_battle;
    private final Level level;
    private final byte capacity = 5;
    private final float delay = .5f; //2
    private float count;
    private Pool<Enemy> poolWarrior;
    private Pool<Enemy> poolSoldier;
    private Pool<Enemy> poolSoldierArmored;
    private Pool<Enemy> poolSummoner;
    private Pool<Enemy> poolRunner;
    private LinkedList<Class<? extends Enemy>> enemiesToSpawn;

    public EnemySpawner(DDGame game, Level level, GameBattleStage stage) {
        this.game = game;
        this.stage_battle = stage;
        this.level = level;
        this.path = level.getMap().getPathArray();
        Gdx.app.log("INFO", "EnemySpawner is OK");
    }

    /**
     * <pre>
     * Basic loop that spawns enemies with specified delay.
     * NOTE Level is Finished, if there are no enemies to spawn
     * </pre>
     *
     * @param delta Same as Gdx.graphics.getDeltaTime()
     */
    public void update(float delta) {
        if (count >= delay) {
            count = 0;
            try {
                if (!enemiesToSpawn.isEmpty()) {
                    spawn(enemiesToSpawn.pop());
                } else if (!stage_battle.hasEnemiesOnMap() || level.isCleared) {
                    level.getWave().finished();
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
     * <pre>
     * Spawns obtained from pool and cloned Enemy,
     * places it at the Spawn position and gives it the path to go.
     * Spawn takes place in Group with the coresponding HealthBar.
     * <pre>
     * FIXME Zindex at spawn
     *
     * @param type the Enemy ancestor to spawn.
     * @throws CloneNotSupportedException - error occcured at cloning.
     * @throws NullPointerException       - type does not belong to known Enemy ancestor.
     */
    private void spawn(Class<? extends Enemy> type) throws CloneNotSupportedException {
        Enemy enemy;
        if (type.equals(EnemyWarrior.class)) {
            enemy = ((EnemyWarrior) (poolWarrior.obtain())).clone();
        } else if (type.equals(EnemyRunner.class)) {
            enemy = ((EnemyRunner) (poolRunner.obtain())).clone();
        } else if (type.equals(EnemySoldier.class)) {
            enemy = ((EnemySoldier) (poolSoldier.obtain())).clone();
        } else if (type.equals(EnemySoldierArmored.class)) {
            enemy = ((EnemySoldierArmored) (poolSoldierArmored.obtain())).clone();
        } else if (type.equals(EnemySummoner.class)) {
            enemy = ((EnemySummoner) (poolSummoner.obtain())).clone();
        } else {
            throw new NullPointerException("Couldn't add enemy: " + type);
        }
        enemy.setPosition(
                level.getMap().getSpawn().getPosition().x * DDGame.TILEHEIGHT,
                level.getMap().getSpawn().getPosition().y * DDGame.TILEHEIGHT
        );
        enemy.setSpawner(this);
        EnemyGroup enemyGroup = new EnemyGroup(
                enemy,
                new HealthBar(
                        game.getResources().getSkin(),
                        enemy)
        );
        enemyGroup.getEnemy().setGroup(enemyGroup);
        setActionPath(enemyGroup, path);
        enemyGroup.setVisible(true);
        stage_battle.updateZIndexes(enemyGroup);
    }

    /**
     * <pre>
     * Sets the Actions for Enemy to do to walk the path
     *
     * FIXME Bigger speed - slower walk of Enemy
     * FIXME Merge Enemy animations in one atlas and image
     * </pre>
     *
     * @param path - list of directions
     */
    private void setActionPath(EnemyGroup group, ArrayList<Vector2> path) {
        Enemy enemy = group.getEnemy();
        enemy.setBattleStage(stage_battle);
        enemy.setAnimationController(new EnemyAnimationController(game.getResources().getAtlas(Resources.ENEMIES_ATLAS), enemy));
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
                                dir.x * DDGame.TILEHEIGHT,
                                dir.y * DDGame.TILEHEIGHT,
                                enemy.speed)
                );
            } else {
                action = Actions.moveBy(
                        dir.x * DDGame.TILEHEIGHT,
                        dir.y * DDGame.TILEHEIGHT,
                        enemy.speed);
            }
            prevDir = new Vector2(dir);
            pathToGo.addAction(action);
        }
        pathToGo.addAction(
                Actions.parallel(
                        Actions.hide(),
                        Actions.run(new EnemyRemover(game, this, group) {
                            @Override
                            public void customAction() {
                                Gdx.app.log("INFO", "Enemy reached the Base");
                                game.getPlayer().decreaseHealth();
                            }
                        }))
        );
        enemy.addAction(pathToGo);
    }

    private LinkedList<Class<? extends Enemy>> convertList(ArrayList<Class<? extends Enemy>> list) {
        LinkedList<Class<? extends Enemy>> enemies = new LinkedList<Class<? extends Enemy>>();
        for (Class<? extends Enemy> type : list) {
            enemies.add(type);
            if (type.equals(EnemyWarrior.class) && poolWarrior == null) {
                poolWarrior = new EnemyPool<EnemyWarrior>(game, capacity, DDGame.MAX, type);
            } else if (type.equals(EnemyRunner.class) && poolRunner == null) {
                poolRunner = new EnemyPool<EnemyRunner>(game, capacity, DDGame.MAX, type);
            } else if (type.equals(EnemySoldier.class) && poolSoldier == null) {
                poolSoldier = new EnemyPool<EnemySoldier>(game, capacity, DDGame.MAX, type);
            } else if (type.equals(EnemySoldierArmored.class) && poolSoldierArmored == null) {
                poolSoldierArmored = new EnemyPool<EnemySoldierArmored>(game, capacity, DDGame.MAX, type);
            } else if (type.equals(EnemySummoner.class) && poolSummoner == null) {
                poolSummoner = new EnemyPool<EnemySummoner>(game, capacity, DDGame.MAX, type);
            }
        }
        return enemies;
    }

    public boolean hasEnemiesToSpawn() {
        return !enemiesToSpawn.isEmpty();
    }

    public void setEnemiesToSpawn(ArrayList<Class<? extends Enemy>> enemiesToSpawn) {
        this.enemiesToSpawn = convertList(enemiesToSpawn);
        Gdx.app.log("INFO", "Enemies for wave " + level.getCurrentWave() + " are prepared. Size: " + enemiesToSpawn.size());
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
        Gdx.app.log("INFO", enemy + "@" + enemy.hashCode() + " is set free");
    }

    public GameBattleStage getStage_battle() {
        return stage_battle;
    }
}
