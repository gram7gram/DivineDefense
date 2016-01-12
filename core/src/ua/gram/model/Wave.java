package ua.gram.model;

import ua.gram.controller.Log;
import ua.gram.model.prototype.WavePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Wave {

    private final Level level;
    private final String[] enemies;
    private final byte index;
    public boolean isStarted;

    public Wave(Level level, WavePrototype prototype) {
        this.level = level;
        isStarted = false;
        enemies = prototype.enemies;
        index = prototype.index;
        Log.info("Wave is OK");
    }

    /**
     * Set flags for the finish wave. If wave was the last - the level is cleared.
     */
    public void finish() {
        Log.info("Wave " + index + "/" + level.getMaxWaves() + " is finished");
        isStarted = false;
        if (index == level.getMaxWaves()) {
            level.isCleared = true;
            Log.info("Level " + level.getCurrentLevel() + " is cleared");
        }
    }

    public byte getIndex() {
        return index;
    }

    public String[] getEnemies() {
        return enemies;
    }
}
