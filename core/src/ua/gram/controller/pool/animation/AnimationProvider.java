package ua.gram.controller.pool.animation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ua.gram.controller.Log;
import ua.gram.model.prototype.GameActorPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AnimationProvider<P extends GameActorPrototype, T, D> {

    private final Map<String, AnimationControllerInterface<P, T, D>> identityMap;
    private final Set<P> registeredTypes;
    private final Skin skin;

    public AnimationProvider(Skin skin, P[] set) {
        this.skin = skin;
        identityMap = Collections.synchronizedMap(new HashMap<>(set.length));
        registeredTypes = new HashSet<>(Arrays.asList(set));
        if (init()) Log.info(this.getClass().getSimpleName() + " is OK");
        else Log.crit(this.getClass().getSimpleName() + " IS NOT OK");
    }

    private boolean init() {
        if (registeredTypes.isEmpty()) return false;
        boolean initialized = true;

        for (P prototype : registeredTypes) {
            try {
                identityMap.put(prototype.name, getInstance(prototype));
            } catch (Exception e) {
                initialized = false;
                Log.exc("Error at loading " + prototype.name + " animation type", e);
            }
        }

        registeredTypes.clear();

        if (initialized)
            Log.info("Identity map for " + this.getClass().getSimpleName() + " is OK");

        return initialized;
    }

    protected abstract AnimationControllerInterface<P, T, D> getInstance(P prototype);

    public boolean init(P prototype) {
        if (identityMap.isEmpty()) return false;
        String type = prototype.name;
        return identityMap.containsKey(type)
                && identityMap.get(type).init(prototype);
    }

    public Skin getSkin() {
        return skin;
    }

    public AnimationPool get(P prototype, T type1, D type2) {
        return this.get(prototype.name).get(type1, type2);
    }

    public AnimationControllerInterface<P, T, D> get(P prototype) {
        return this.get(prototype.name);
    }

    public AnimationControllerInterface<P, T, D> get(String type) {
        if (identityMap.isEmpty())
            throw new NullPointerException("Put some pools to indentity map first to use getPool()");
        AnimationControllerInterface<P, T, D> pool = identityMap.get(type);
        if (pool == null)
            throw new NullPointerException("Identity map does not contain pool for type: " + type);
        return pool;
    }
}
