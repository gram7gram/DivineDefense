package ua.gram.android;

import ua.gram.model.prototype.GamePrototype;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AndroidGamePrototype extends GamePrototype {

    public String osCodename;
    public String osRelease;
    public String apiVersion;

    @Override
    public HashMap<String, Object> toMap() {
        super.toMap();
        getMap().put("osCodename", osCodename);
        getMap().put("osRelease", osRelease);
        getMap().put("apiVersion", apiVersion);
        return getMap();
    }
}
