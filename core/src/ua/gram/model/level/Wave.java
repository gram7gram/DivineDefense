package ua.gram.model.level;

import ua.gram.controller.event.LevelFinishedEvent;
import ua.gram.model.prototype.level.WavePrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Wave {

    private final Level level;
    private final String[] enemies;
    private final byte index;
    private boolean isStarted;

    public Wave(Level level, WavePrototype prototype) {
        this.level = level;
        isStarted = false;
        enemies = prototype.enemies;
        index = prototype.index;
        Log.info("Wave " + index + " is OK");
    }

    public void finish() {
        Log.info("Wave " + index + "/" + level.getMaxWaves() + " is finished");
        isStarted = false;
        if (isLastWave()) {
            level.getStageHolder().fire(new LevelFinishedEvent());
        }
    }

    private boolean isLastWave() {
        return index == level.getMaxWaves();
    }

    public byte getIndex() {
        return index;
    }

    public String[] getEnemies() {
        return enemies;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        this.isStarted = started;
    }
}
