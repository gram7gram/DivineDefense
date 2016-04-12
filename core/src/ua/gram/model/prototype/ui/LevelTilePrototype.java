package ua.gram.model.prototype.ui;

import ua.gram.model.prototype.Prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class LevelTilePrototype extends Prototype {
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
     * Players achievements on the levelConfig
     */
    public byte stars;
    /**
     * Essential resources to load for the levelConfig
     */
    public String[] resources;
}
