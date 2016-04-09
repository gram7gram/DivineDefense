package ua.gram.model.level;

import ua.gram.model.prototype.WavePrototype;
import ua.gram.utils.Log;

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
        Log.info("Wave " + index + " is OK");
    }

    /**
     * Set flags for the finish wave. If wave was the last - the levelConfig is cleared.
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
