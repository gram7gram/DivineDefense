package ua.gram.controller.pool.animation;

import com.badlogic.gdx.Gdx;
import ua.gram.model.Animator;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbilityUserDirectionAnimationPool extends EnemyDirectionAnimationPool {

    public AbilityUserDirectionAnimationPool(Animator.Types type, EnemyAnimation provider) {
//        super(type,provider);
        leftPool = new AnimationPool(provider.setAnimationRegion(Animator.Types.WALKING, Path.Types.LEFT, type));
        rightPool = new AnimationPool(provider.setAnimationRegion(Animator.Types.WALKING, Path.Types.RIGHT, type));
        downPool = new AnimationPool(provider.setAnimationRegion(Animator.Types.WALKING, Path.Types.DOWN, type));
        upPool = new AnimationPool(provider.setAnimationRegion(Animator.Types.WALKING, Path.Types.UP, type));
        Gdx.app.log("INFO", "DirectionPool for " + type + " is OK");
    }
}
