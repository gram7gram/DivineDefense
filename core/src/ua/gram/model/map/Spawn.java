package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;

import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Spawn {

    public Vector2 spawnPosition;

    public Spawn(Vector2 spawnPosition) {
        this.spawnPosition = spawnPosition;
        Log.info("Spawn at " + spawnPosition);
        Log.info("Spawn is OK");
    }

    public Vector2 getPosition() {
        return spawnPosition;
    }
}
