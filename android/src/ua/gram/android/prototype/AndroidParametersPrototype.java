package ua.gram.android.prototype;

import ua.gram.model.prototype.ParametersPrototype;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AndroidParametersPrototype extends ParametersPrototype {

    public String osCodename;
    public String osRelease;
    public String apiVersion;

    @Override
    public HashMap<String, Object> toMap() {
        super.toMap();
        getConfig().put("osCodename", osCodename);
        getConfig().put("osRelease", osRelease);
        getConfig().put("apiVersion", apiVersion);
        return getConfig();
    }
}
