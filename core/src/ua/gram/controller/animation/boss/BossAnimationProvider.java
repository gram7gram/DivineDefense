package ua.gram.controller.animation.boss;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.controller.animation.AnimationManager;
import ua.gram.controller.animation.AnimationProvider;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.boss.BossPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossAnimationProvider extends
        AnimationProvider<BossPrototype, Types.BossState, Path.Direction> {

    public BossAnimationProvider(Skin skin, BossPrototype[] prototypes) {
        super(skin, prototypes);
    }

    @Override
    protected AnimationManager<BossPrototype, Types.BossState, Path.Direction> getInstance(BossPrototype prototype) {
        return new BossAnimationManager(getSkin(), prototype);
    }
}
