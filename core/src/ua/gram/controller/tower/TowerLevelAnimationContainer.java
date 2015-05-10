package ua.gram.controller.tower;

import com.badlogic.gdx.graphics.g2d.Animation;
import ua.gram.controller.pool.animation.AnimationController;

/**
 * Holds Pools of Animations for each level and state of the Tower.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerLevelAnimationContainer {

    private Animation idleAnimation;
//    private Animation shootAnimation = null;
//    private Animation buildAnimation = null;
//    private Animation sellAnimation = null;

    public TowerLevelAnimationContainer(TowerAnimationController towerAnimation, int level, int animationWidth, int animationHeight) {
        idleAnimation = new Animation(
                TowerAnimationController.DELAY,
                towerAnimation.setAnimationRegion(
                        AnimationController.Types.IDLE,
                        level, animationWidth, animationHeight)
        );
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Animation getAnimation(AnimationController.Types type) {
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
