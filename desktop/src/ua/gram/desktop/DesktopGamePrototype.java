package ua.gram.desktop;

import ua.gram.model.prototype.GamePrototype;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DesktopGamePrototype extends GamePrototype {

    public String osVersion;
    public String osName;

    @Override
    public HashMap<String, Object> toMap() {
        super.toMap();
        getMap().put("osName", osName);
        getMap().put("osVersion", osVersion);
        return getMap();
    }
}
