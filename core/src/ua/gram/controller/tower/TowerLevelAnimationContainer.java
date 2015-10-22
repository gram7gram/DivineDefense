package ua.gram.controller.tower;

import com.badlogic.gdx.graphics.g2d.Animation;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.model.Animator;

/**
 * Holds Pools of Animations for each level and state of the Tower.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerLevelAnimationContainer {

    private Animation idleAnimation;
//    private Animator shootAnimation = null;
//    private Animator buildAnimation = null;
//    private Animator sellAnimation = null;

    public TowerLevelAnimationContainer(TowerAnimationController towerAnimation, int level) {
        idleAnimation = new Animation(
                TowerAnimationController.DELAY,
                towerAnimation.setAnimationRegion(
                        Animator.Types.IDLE,
                        level)
        );
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Animation getAnimation(Animator.Types type) {
        switch (type) {
            case IDLE:
                return idleAnimation;
//            case SELL:
//                return sellAnimation;
//            case BUILD:
//                return buildAnimation;
//            case SHOOT:
//                return shootAnimation;
            default:
                throw new NullPointerException("Unknown animation type: " + type);
        }
    }

}
