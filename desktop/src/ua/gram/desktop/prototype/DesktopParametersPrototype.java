package ua.gram.desktop.prototype;

import ua.gram.model.prototype.ParametersPrototype;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DesktopParametersPrototype extends ParametersPrototype {

    public String osVersion;
    public String osName;

    @Override
    public HashMap<String, Object> toMap() {
        super.toMap();
        getConfig().put("osName", osName);
        getConfig().put("osVersion", osVersion);
        return getConfig();
    }
}
