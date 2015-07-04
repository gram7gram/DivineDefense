package ua.gram.model;

import com.badlogic.gdx.Gdx;
import ua.gram.controller.enemy.EnemySpawner;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Wave {

    public static int maxWaves;
    public static int currentWave;
    private final Level level;
    private final ArrayList<String[]> waves;
    public boolean isStarted;
    private EnemySpawner spawner;

    public Wave(Level level, ArrayList<String[]> waves) {
        this.level = level;
        this.waves = waves;
        maxWaves = waves.size();
        currentWave = 0;
        isStarted = false;
        Gdx.app.log("INFO", "Wave is OK");
    }

    public void nextWave() throws IndexOutOfBoundsException {
        spawner.setEnemiesToSpawn(waves.get(currentWave));
        ++currentWave;
        isStarted = true;
        Gdx.app.log("INFO", "Wave " + currentWave + "/" + maxWaves + " has started");
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public int getMaxWaves() {
        return maxWaves;
    }

    /**
     * Set flags for the finish wave. If wave was the last - the level is cleared.
     */
    public void finish() {
        Gdx.app.log("INFO", "Wave " + currentWave + "/" + maxWaves + " is finished");
        isStarted = false;
        if (currentWave == maxWaves) {
            level.isCleared = true;
            Gdx.app.log("INFO", "Level " + level.getCurrentLevel() + " is cleared");
        }
    }

    public void setSpawner(EnemySpawner spawner) {
        this.spawner = spawner;
    }
}
