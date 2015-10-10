package ua.gram.model.prototype;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class GamePrototype extends Prototype {

    public String id;
    public byte maxLevels;
    public byte maxRanking;
    public String configPath;
    public String gameModule;
    public String osName;
    public String deviceId;

    private HashMap<String, Object> map;

    public HashMap<String, Object> toMap() {
        map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("maxLevels", maxLevels);
        map.put("maxRanking", maxRanking);
        map.put("configPath", configPath);
        map.put("gameModule", gameModule);
        map.put("osName", osName);
        map.put("deviceId", deviceId);
        return map;
    }

    protected HashMap<String, Object> getMap() {
        return map;
    }
}
