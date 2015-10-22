package ua.gram.model.prototype;

import java.util.HashMap;

/**
 * TODO Split into Global and Game configuration
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class GamePrototype implements Prototype {

    public String id;
    public String configPath;
    public String gameModule;
    public String osName;
    public String deviceId;
    public String contact;
    public String token;
    public String client;
    public String author;
    public int maxLevels;
    public int maxRanking;

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
