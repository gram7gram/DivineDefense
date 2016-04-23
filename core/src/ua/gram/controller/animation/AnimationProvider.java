package ua.gram.controller.animation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.Initializer;
import ua.gram.model.prototype.GameActorPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AnimationProvider<P extends GameActorPrototype, T, D> implements Initializer {

    private final Map<String, ua.gram.controller.animation.AnimationManager<P, T, D>> identityMap;
    private final Set<P> registeredTypes;
    private final Skin skin;

    public AnimationProvider(Skin skin, P[] prototypes) {
        this.skin = skin;
        identityMap = new HashMap<String, ua.gram.controller.animation.AnimationManager<P, T, D>>(prototypes.length);
        registeredTypes = new HashSet<P>(Arrays.asList(prototypes));
        Log.info(getClass().getSimpleName() + " is OK");
    }

    @Override
    public void init() {
        if (registeredTypes.isEmpty())
            throw new NullPointerException("Missing registered prototypes");

        for (P prototype : registeredTypes) {
            ua.gram.controller.animation.AnimationManager<P, T, D> controller = getInstance(prototype);
            controller.init();
            identityMap.put(prototype.name, controller);
        }

        registeredTypes.clear();

        Log.info("Identity map for " + this.getClass().getSimpleName() + " is OK");
    }

    protected abstract ua.gram.controller.animation.AnimationManager<P, T, D> getInstance(P prototype);

    public Skin getSkin() {
        return skin;
    }

    public AnimationPool get(P prototype, Animator<T, D> animator) {
        return get(prototype, animator.getPrimaryType(), animator.getSecondaryType());
    }

    public AnimationPool get(P prototype, T type1, D type2) {
        return this.get(prototype.name).get(type1, type2);
    }

    public ua.gram.controller.animation.AnimationManager<P, T, D> get(P prototype) {
        return get(prototype.name);
    }

    public ua.gram.controller.animation.AnimationManager<P, T, D> get(String type) {
        if (identityMap.isEmpty())
            throw new NullPointerException("Put some pools to indentity map first to use AnimationProvider.get()");

        ua.gram.controller.animation.AnimationManager<P, T, D> pool = identityMap.get(type);

        if (pool == null)
            throw new NullPointerException("Identity map does not contain pool for type: " + type);

        return pool;
    }
}
