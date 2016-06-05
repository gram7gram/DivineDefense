package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;

import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Base {

    public Vector2 basePosition;

    public Base(Vector2 basePosition) {
        this.basePosition = basePosition;
        Log.info("Base at " + basePosition);
        Log.info("Base is OK");
    }

    public Vector2 getPosition() {
        return basePosition;
    }

}
