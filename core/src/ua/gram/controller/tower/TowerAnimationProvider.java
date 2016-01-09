package ua.gram.controller.tower;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.controller.pool.animation.AnimationControllerInterface;
import ua.gram.controller.pool.animation.AnimationProvider;
import ua.gram.controller.pool.animation.TowerAnimationController;
import ua.gram.model.enums.Types;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerAnimationProvider extends AnimationProvider<TowerPrototype, Types.TowerState, Types.TowerLevels> {

    public TowerAnimationProvider(Skin skin, TowerPrototype[] registeredTypes) {
        super(skin, registeredTypes);
    }

    @Override
    protected AnimationControllerInterface<TowerPrototype, Types.TowerState, Types.TowerLevels> getInstance(TowerPrototype prototype) {
        return new TowerAnimationController(getSkin(), prototype);
    }

}
