package ua.gram.android.prototype;

import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AndroidGamePrototype extends GamePrototype<AndroidParametersPrototype> {

    public AndroidParametersPrototype parameters;
    public AndroidConfigPrototype config;

    @Override
    public String getConfigPath() {
        return parameters.configPath;
    }

    @Override
    public AndroidParametersPrototype getParameters() {
        return parameters;
    }
}
