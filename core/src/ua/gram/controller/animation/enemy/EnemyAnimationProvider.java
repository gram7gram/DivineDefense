package ua.gram.controller.animation.enemy;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.controller.animation.AnimationManager;
import ua.gram.controller.animation.AnimationProvider;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.enemy.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationProvider extends AnimationProvider<EnemyPrototype, Types.EnemyState, Path.Direction> {

    public EnemyAnimationProvider(Skin skin, EnemyPrototype[] registeredTypes) {
        super(skin, registeredTypes);
    }

    @Override
    protected AnimationManager<EnemyPrototype, Types.EnemyState, Path.Direction> getInstance(EnemyPrototype prototype) {
        return new EnemyAnimationManager(getSkin(), prototype);
    }
}
