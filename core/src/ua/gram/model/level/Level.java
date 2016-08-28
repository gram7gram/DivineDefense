package ua.gram.model.level;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.event.WaveStartedEvent;
import ua.gram.controller.factory.LevelFactory;
import ua.gram.controller.stage.StageHolder;
import ua.gram.controller.stage.UIStage;
import ua.gram.model.Initializer;
import ua.gram.model.map.Map;
import ua.gram.model.prototype.level.LevelPrototype;
import ua.gram.model.prototype.level.WavePrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Level implements Initializer, Disposable {

    public static int MAX_WAVES;
    protected final DDGame game;
    protected final LevelFactory.Type type;
    private final ArrayList<Wave> waves;
    private final LevelPrototype prototype;
    protected StageHolder stageHolder;
    private Wave currentWave;
    private Map map;
    private EnemySpawner spawner;
    private int currentLevel;
    private boolean isCleared;
    private boolean isInterrupted;

    public Level(DDGame game, LevelPrototype prototype, LevelFactory.Type type) {
        if (prototype.waves == null || prototype.waves.length == 0)
            throw new NullPointerException("Missing waves");
        this.game = game;
        this.prototype = prototype;
        this.type = type;
        currentLevel = prototype.level;
        MAX_WAVES = prototype.waves.length;
        isCleared = false;
        isInterrupted = false;
        waves = new ArrayList<Wave>(prototype.waves.length);
        map = new Map(game, prototype.map);
        Log.info("Level " + currentLevel + " " + type.name() + " is OK");
    }

    @Override
    public void init() {
        map.init();
        for (WavePrototype proto : prototype.waves) {
            waves.add(new Wave(this, proto));
        }
        spawner = new EnemySpawner(game, this, stageHolder.getBattleStage());
        spawner.init();
    }

    public void update(float delta) {
        if (!DDGame.PAUSE) {
            if (canUpdateSpawner()) {
                spawner.update(delta);
            }
        }
    }

    private boolean canUpdateSpawner() {
        return currentWave != null && currentWave.isStarted()
                && currentWave.getIndex() <= MAX_WAVES;
    }

    public boolean hasNextWave() {
        return waves.indexOf(currentWave) + 1 <= waves.size();
    }

    public void nextWave() throws IndexOutOfBoundsException {
        currentWave = waves.get(waves.indexOf(currentWave) + 1);
        spawner.setEnemiesToSpawn(currentWave.getEnemies());
        currentWave.setStarted(true);
        showNextWaveNotification();

        stageHolder.fire(new WaveStartedEvent());

        Log.info("Wave " + currentWave.getIndex()
                + "(" + currentWave.getEnemies().length + ") / " + MAX_WAVES
                + " has started");
    }

    private void showNextWaveNotification() {
        int index = getCurrentWaveIndex();
        if (index > 0) {
            UIStage stage = stageHolder.getUiStage();
            stage.getGameUIGroup().showNotification("WAVE " + index);
        } else {
            Log.warn("Passed " + index + " wave index to notification. Ignored");
        }
    }

    /**
     * Player successfully clears the Level.
     */
    public boolean isVictorious() {
        return (isCleared || isInterrupted)
                && !game.getPlayer().isDead()
                && !stageHolder.getBattleStage().hasEnemiesOnMap();
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

    public LevelPrototype getPrototype() {
        return prototype;
    }

    public boolean isActiveWave() {
        return currentWave != null && currentWave.isStarted();
    }

    public boolean isCleared() {
        return isCleared;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }

    public int getIndex() {
        return prototype.level;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    public void setInterrupted(boolean finished) {
        isInterrupted = finished;
        Log.warn("Level is interrupted");
    }

    @Override
    public void dispose() {
        isInterrupted = false;
        isCleared = false;
        currentLevel = prototype.level;
    }

    public void finish() {
        setCleared(true);
        Log.info("Level " + currentLevel + " is finished");
    }

    public StageHolder getStageHolder() {
        return stageHolder;
    }

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    public void propagateEvent(Event event) {

    }
}
