package ua.gram.model;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.map.Map;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.model.prototype.WavePrototype;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Level {

    public static int MAX_WAVES;
    private final ArrayList<Wave> waves;
    private final LevelPrototype prototype;
    public boolean isCleared;
    private Wave currentWave;
    private Map map;
    private DDGame game;
    private EnemySpawner spawner;
    private GameBattleStage stage_battle;
    private int currentLevel;

    public Level(DDGame game, LevelPrototype prototype) throws Exception {
        this.game = game;
        this.prototype = prototype;
        if (prototype.waves.length == 0) throw new Exception("Missing waves");
        MAX_WAVES = prototype.waves.length;
        waves = new ArrayList<>(prototype.waves.length);
        for (WavePrototype proto : prototype.waves) {
            waves.add(new Wave(this, proto));
        }
        isCleared = false;
        currentLevel = prototype.level;
        map = new Map(game, prototype.map);
        Gdx.app.log("INFO", "Level " + currentLevel + " is OK");
    }

    public void createSpawner() {
        spawner = new EnemySpawner(game, this, stage_battle);
    }

    public void update(float delta) {
        if (currentWave != null) {
            if (currentWave.getIndex() <= MAX_WAVES) {
                if (currentWave.isStarted) {
                    if (spawner == null)
                        createSpawner();
                    spawner.update(delta);
                }
            }
        }
    }

    public void nextWave() throws IndexOutOfBoundsException {
        currentWave = waves.get(waves.indexOf(currentWave) + 1);
        spawner.setEnemiesToSpawn(currentWave.getEnemies());
        currentWave.isStarted = true;
        Gdx.app.log("INFO", "Wave " + currentWave.getIndex()
                + "(" + currentWave.getEnemies().length + ") / " + MAX_WAVES + " has started");
    }

    /**
     * Player successfully clears the Level.
     */
    public boolean isVictorious() {
        return !game.getPlayer().isDead()
                && isCleared
                && !stage_battle.hasEnemiesOnMap();
    }

    public boolean isLast() {
        return currentLevel == DDGame.MAX_LEVELS;
    }

    /**
     * Player fails the Level.
     */
    public boolean isDefeated() {
        return game.getPlayer().isDead();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public Map getMap() {
        return map;
    }

    public int getCurrentWave() {
        return currentWave != null ? currentWave.getIndex() : 0;
    }

    public int getMaxWaves() {
        return MAX_WAVES;
    }

    public Wave getWave() {
        return currentWave;
    }

    public GameBattleStage getStage() {
        return stage_battle;
    }

    public void setStage(GameBattleStage stage) {
        this.stage_battle = stage;
    }

    public LevelPrototype getPrototype() {
        return prototype;
    }

    public boolean isActiveWave() {
        return currentWave != null && currentWave.isStarted;
    }

    public boolean isFinished() {
        return isCleared || (currentWave != null && currentWave.getIndex() == MAX_WAVES);
    }
}
