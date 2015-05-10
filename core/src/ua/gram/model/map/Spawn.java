package ua.gram.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Spawn {

    public Vector2 spawnPosition;

    public Spawn(Vector2 spawnPosition) {
        this.spawnPosition = spawnPosition;
        Gdx.app.log("INFO", "Spawn is OK");
    }

    public Vector2 getPosition() {
        return spawnPosition;
    }
}
