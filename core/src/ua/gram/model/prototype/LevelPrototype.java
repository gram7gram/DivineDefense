package ua.gram.model.prototype;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class LevelPrototype extends Prototype {
    public byte level;
    public String preview;
    public WavePrototype[] waves;
    public MapPrototype map;
    public Vector2 initialDirection;
}
