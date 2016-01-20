package ua.gram.model.shader;

import ua.gram.model.prototype.ShaderPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ShaderFactory {

    public AbstractShader create(ShaderPrototype prototype) {
        switch (prototype.name.toUpperCase()) {
            case "OUTLINE":
                return new OutlineShader(prototype);
            default:
                throw new NullPointerException("Shader not found by name: " + prototype.name);
        }
    }

}
