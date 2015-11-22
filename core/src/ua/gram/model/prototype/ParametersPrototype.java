package ua.gram.model.prototype;

import java.util.HashMap;

/**
 * TODO Split into Global and Game configuration
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class ParametersPrototype implements Prototype {

    public String[] consoleHello;
    public String[] consoleBye;
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
    public MapPrototype map;

    private HashMap<String, Object> config;

    public HashMap<String, Object> toMap() {
        config = new HashMap<String, Object>();
        config.put("id", id);
        config.put("maxLevels", maxLevels);
        config.put("maxRanking", maxRanking);
        config.put("configPath", configPath);
        config.put("gameModule", gameModule);
        config.put("osName", osName);
        config.put("deviceId", deviceId);
        return config;
    }

    protected HashMap<String, Object> getConfig() {
        return config;
    }
}
