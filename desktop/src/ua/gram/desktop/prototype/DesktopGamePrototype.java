package ua.gram.desktop.prototype;

import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DesktopGamePrototype extends GamePrototype<DesktopParametersPrototype> {

    public DesktopParametersPrototype parameters;
    public DesktopConfigPrototype gameConfig;

    @Override
    public String getConfigPath() {
        return parameters.configPath;
    }

    @Override
    public DesktopParametersPrototype getParameters() {
        return parameters;
    }
}
