package ua.gram.model.prototype.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ua.gram.model.prototype.Prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class LevelPrototype extends Prototype {
    public byte level;
    public String preview;
    public WavePrototype[] waves;
    public MapPrototype map;
    public Vector2 initialDirection;
    public LevelAssetPrototype assets;
    public Color backgroundColor;
}
