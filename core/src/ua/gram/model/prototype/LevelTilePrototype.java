package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelTilePrototype {
    /**
     * Map file to load
     */
    public String map;
    /**
     * Level preview in LevelSelectScreen
     */
    public String icon;
    /**
     * Level number
     */
    public byte level;
    /**
     * Level difficulty
     */
    public byte difficulty;
    /**
     * Players achievements on the level
     */
    public byte stars;
    /**
     * Essential resources to load for the level
     */
    public String[] resources;
}
