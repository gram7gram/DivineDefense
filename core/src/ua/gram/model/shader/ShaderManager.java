package ua.gram.model.shader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import ua.gram.model.prototype.ShaderPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ShaderManager {

    private final HashMap<ShaderPrototype, AbstractShader> identityMap;
    private final ShaderPrototype[] prototypes;

    public ShaderManager(ShaderPrototype[] prototypes) {
        identityMap = new HashMap<>(prototypes.length);
        this.prototypes = prototypes;
        ShaderFactory factory = new ShaderFactory();
        for (ShaderPrototype prototype : prototypes) {
            identityMap.put(prototype, factory.create(prototype));
        }
    }

    public AbstractShader get(String name) {
        Optional<ShaderPrototype> optional = Arrays.asList(prototypes).stream()
                .filter(prototype -> prototype.name.equals(name))
                .findFirst();

        if (!optional.isPresent())
            throw new NullPointerException("No shader registered by name " + name);

        return identityMap.get(optional.get());
    }
}
