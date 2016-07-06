package ua.gram.model.prototype.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ua.gram.model.prototype.Prototype;
import ua.gram.model.prototype.ui.ImagePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class LevelPrototype extends Prototype {
    public byte level;
    public ImagePrototype preview;
    public WavePrototype[] waves;
    public MapPrototype map;
    public Vector2 initialDirection;
    public LevelAssetPrototype assets;
    public String[] bosses;
    public String type;
    public Color backgroundColor;
}
