package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class LevelPrototype implements Prototype {
    public byte difficulty;
    public byte level;
    public byte ranking;
    public boolean locked;
    public String preview;
    public WavePrototype[] waves;
    public MapPrototype map;
}
