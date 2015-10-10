package ua.gram.model;

import com.badlogic.gdx.Gdx;
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
        Gdx.app.log("INFO", "Wave is OK");
    }

    /**
     * Set flags for the finish wave. If wave was the last - the level is cleared.
     */
    public void finish() {
        Gdx.app.log("INFO", "Wave " + (index + 1) + "/" + level.getMaxWaves() + " is finished");
        isStarted = false;
        if (index == level.getMaxWaves()) {
            level.isCleared = true;
            Gdx.app.log("INFO", "Level " + level.getCurrentLevel() + " is cleared");
        }
    }

    public byte getIndex() {
        return index;
    }

    public String[] getEnemies() {
        return enemies;
    }
}
