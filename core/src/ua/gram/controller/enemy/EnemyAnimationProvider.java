package ua.gram.controller.enemy;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.controller.pool.animation.AnimationControllerInterface;
import ua.gram.controller.pool.animation.AnimationProvider;
import ua.gram.controller.pool.animation.EnemyAnimationController;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.enemy.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationProvider extends AnimationProvider<EnemyPrototype, Types.EnemyState, Path.Types> {

    public EnemyAnimationProvider(Skin skin, EnemyPrototype[] registeredTypes) {
        super(skin, registeredTypes);
    }

    @Override
    protected AnimationControllerInterface<EnemyPrototype, Types.EnemyState, Path.Types> getInstance(EnemyPrototype prototype) {
        return new EnemyAnimationController(getSkin(), prototype);
    }
}
