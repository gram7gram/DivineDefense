package ua.gram.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Base {

    public Vector2 basePosition;

    public Base(Vector2 basePosition) {
        this.basePosition = basePosition;
        Gdx.app.log("INFO", "Base is OK");
    }

    public Vector2 getPosition() {
        return basePosition;
    }

}
