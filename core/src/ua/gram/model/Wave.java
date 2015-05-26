package ua.gram.model;

import com.badlogic.gdx.Gdx;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.enemy.*;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Wave {

    public static float countdown;
    public static int maxWaves;
    public static int currentWave;
    private final Level level;
    private final ArrayList<ArrayList<Class<? extends Enemy>>> waves;
    public boolean isStarted;
    private EnemySpawner spawner;
    private ArrayList<Class<? extends Enemy>> enemies;

    public Wave(Level level, ArrayList<ArrayList<Class<? extends Enemy>>> waves) {
        this.level = level;
        this.waves = test();
        maxWaves = waves.size();
        currentWave = 0;
        countdown = 10;
        isStarted = false;
        Gdx.app.log("INFO", "Wave is OK");
    }

    /**
     * FIX Should be replaced by Json config file.
     *
     * @return
     */
    private ArrayList<ArrayList<Class<? extends Enemy>>> test() {
        Gdx.app.log("WARN", "Cannot create waves from empty array! Using test()");

        ArrayList<ArrayList<Class<? extends Enemy>>> _waves = new ArrayList<ArrayList<Class<? extends Enemy>>>();

        ArrayList<Class<? extends Enemy>> enemies1 = new ArrayList<Class<? extends Enemy>>();
        enemies1.add(EnemyRunner.class);
        enemies1.add(EnemyRunner.class);
        enemies1.add(EnemyRunner.class);
        enemies1.add(EnemySoldier.class);
        enemies1.add(EnemySoldier.class);
        enemies1.add(EnemySoldier.class);
        enemies1.add(EnemySoldierArmored.class);
        enemies1.add(EnemySoldier.class);
        enemies1.add(EnemyRunner.class);
        enemies1.add(EnemySoldierArmored.class);
        enemies1.add(EnemySoldierArmored.class);
        enemies1.add(EnemySoldierArmored.class);
        enemies1.add(EnemyRunner.class);
        enemies1.add(EnemySoldierArmored.class);
        enemies1.add(EnemySoldier.class);
        enemies1.add(EnemyRunner.class);

        ArrayList<Class<? extends Enemy>> enemies2 = new ArrayList<Class<? extends Enemy>>();
        enemies2.add(EnemyRunner.class);
        enemies2.add(EnemySoldier.class);

        ArrayList<Class<? extends Enemy>> enemies3 = new ArrayList<Class<? extends Enemy>>();
        enemies3.add(EnemyRunner.class);
        enemies3.add(EnemySoldier.class);
        enemies3.add(EnemySoldier.class);
        enemies3.add(EnemySummoner.class);
        enemies3.add(EnemyWarrior.class);
        enemies3.add(EnemyWarrior.class);
        enemies3.add(EnemySoldierArmored.class);
        enemies3.add(EnemySoldierArmored.class);
        enemies3.add(EnemySummoner.class);
        enemies3.add(EnemySoldierArmored.class);
        enemies3.add(EnemySoldierArmored.class);
        enemies3.add(EnemySummoner.class);
        enemies3.add(EnemyWarrior.class);
        enemies3.add(EnemySoldierArmored.class);
        enemies3.add(EnemySoldierArmored.class);
        enemies3.add(EnemySummoner.class);
        enemies3.add(EnemyWarrior.class);

        _waves.add(enemies1);
        _waves.add(enemies2);
        _waves.add(enemies3);
        return _waves;
    }

    /**
     * Prepares enemies for the following wave, if the countdown has finished.
     *
     * @param delta
     */
    public void update(float delta) {
        if (!isStarted && !level.isCleared) {
            if (countdown <= 0) {
                nextWave();
            } else {
                countdown -= delta;
            }
        }
    }

    public void nextWave() {
        countdown = 5;
        enemies = waves.get(currentWave);
        ++currentWave;
        spawner.setEnemiesToSpawn(enemies);
        isStarted = true;
        Gdx.app.log("INFO", "Wave " + currentWave + "/" + maxWaves + " has started");
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public int getMaxWaves() {
        return maxWaves;
    }

    public float getCountdown() {
        return countdown;
    }

    public boolean isFinished() {
        return !isStarted;
    }

    /**
     * Set flags for the finished wave. If wave was the last - the level is cleared.
     */
    public void finished() {
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
