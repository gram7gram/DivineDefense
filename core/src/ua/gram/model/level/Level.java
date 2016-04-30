package ua.gram.model.level;

import java.util.ArrayList;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.stage.UIStage;
import ua.gram.model.Initializer;
import ua.gram.model.map.Map;
import ua.gram.model.prototype.level.LevelPrototype;
import ua.gram.model.prototype.level.WavePrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Level implements Initializer {

    public static int MAX_WAVES;
    private final ArrayList<Wave> waves;
    private final LevelPrototype prototype;
    public boolean isCleared;
    private Wave currentWave;
    private Map map;
    private DDGame game;
    private EnemySpawner spawner;
    private BattleStage battleStage;
    private int currentLevel;

    public Level(DDGame game, LevelPrototype prototype) {
        if (prototype.waves == null || prototype.waves.length == 0)
            throw new NullPointerException("Missing waves");
        this.game = game;
        this.prototype = prototype;
        currentLevel = prototype.level;
        MAX_WAVES = prototype.waves.length;
        isCleared = false;
        waves = new ArrayList<Wave>(prototype.waves.length);
        map = new Map(game, prototype.map);
        Log.info("Level " + currentLevel + " is OK");
    }

    @Override
    public void init() {
        map.init();
        for (WavePrototype proto : prototype.waves) {
            waves.add(new Wave(this, proto));
        }
        spawner = new EnemySpawner(game, this, battleStage);
        spawner.init();
    }

    public void update(float delta) {
        if (currentWave != null) {
            if (currentWave.getIndex() <= MAX_WAVES) {
                if (currentWave.isStarted) {
                    spawner.update(delta);
                }
            }
        }
    }

    public void nextWave() throws IndexOutOfBoundsException {
        currentWave = waves.get(waves.indexOf(currentWave) + 1);
        spawner.setEnemiesToSpawn(currentWave.getEnemies());
        currentWave.isStarted = true;
        showNextWaveNotification();
        Log.info("Wave " + currentWave.getIndex()
                + "(" + currentWave.getEnemies().length
                + ") / " + MAX_WAVES
                + " has started");
    }

    private void showNextWaveNotification() {
        int index = getCurrentWaveIndex();
        if (index > 0) {
            String text = "WAVE " + index;
            UIStage stage = battleStage.getStageHolder().getUiStage();
            stage.getGameUIGroup().showNotification(text);
        } else {
            Log.warn("Passed " + index + " wave index to notification. Ignored");
        }
    }

    /**
     * Player successfully clears the Level.
     */
    public boolean isVictorious() {
        return !game.getPlayer().isDead()
                && isCleared
                && !battleStage.hasEnemiesOnMap();
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

    public int getCurrentWaveIndex() {
        return currentWave != null ? currentWave.getIndex() : -1;
    }

    public int getMaxWaves() {
        return MAX_WAVES;
    }

    public Wave getWave() {
        return currentWave;
    }

    public BattleStage getBattleStage() {
        return battleStage;
    }

    public void setBattleStage(BattleStage stage) {
        this.battleStage = stage;
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

    public int getIndex() {
        return prototype.level;
    }
}
