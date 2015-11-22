package ua.gram.desktop.prototype;

import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DesktopGamePrototype extends GamePrototype {
    public DesktopParametersPrototype parameters;
    public DesktopConfigPrototype config;

    @Override
    public String getConfigPath() {
        return parameters.configPath;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DesktopParametersPrototype getParameters() {
        return parameters;
    }
}
