package ua.gram.controller.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.gram.controller.pool.animation.AnimationController.Types;
import ua.gram.controller.pool.animation.EnemyAnimationPool;
import ua.gram.model.Player;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationController {

    public static final float DELAY = 1 / 10f;
    private final TextureAtlas atlas;
    private final Enemy enemy;
    private final Animation upAnimation;
    private final Animation downAnimation;
    private final Animation leftAnimation;
    private final Animation rightAnimation;
    private EnemyAnimationPool leftPool;
    private EnemyAnimationPool rightPool;
    private EnemyAnimationPool upPool;
    private EnemyAnimationPool downPool;

    public EnemyAnimationController(TextureAtlas atlas, Enemy enemy) {
        this.atlas = atlas;
        this.enemy = enemy;
        int animationWidth = (int) enemy.getWidth();
        int animationHeight = (int) enemy.getHeight();
        upPool = new EnemyAnimationPool(setAnimationRegion(Types.UP, animationWidth, animationHeight));
        leftPool = new EnemyAnimationPool(setAnimationRegion(Types.LEFT, animationWidth, animationHeight));
        rightPool = new EnemyAnimationPool(setAnimationRegion(Types.RIGHT, animationWidth, animationHeight));
        downPool = new EnemyAnimationPool(setAnimationRegion(Types.DOWN, animationWidth, animationHeight));
        upAnimation = upPool.obtain();
        leftAnimation = leftPool.obtain();
        rightAnimation = rightPool.obtain();
        downAnimation = downPool.obtain();
    }

    public TextureRegion[] setAnimationRegion(Types animationType, int animationWidth, int animationHeight) {
        String region = enemy.getClass().getSimpleName() + "_" + Player.SYSTEM_FRACTION;
        switch (animationType) {
            case LEFT:
                region += "_Left";
                break;
            case RIGHT:
                region += "_Right";
                break;
            case DOWN:
                region += "_Down";
                break;
            case UP:
                region += "_Up";
                break;
            case DEAD:
                region += "_Dead";
                break;
            case CLEAR:
                region += "_Clear";
                break;
            default:
                throw new NullPointerException("Unknown Enemy Animation id: " + animationType);
        }
        TextureRegion[][] regions = atlas.findRegion(region).split(animationWidth, animationHeight);
        return regions[0];
    }

    public void free(Animation animation) {
        if (animation == upAnimation) {
            upPool.free(animation);
        } else if (animation == downAnimation) {
            downPool.free(animation);
        } else if (animation == rightAnimation) {
            rightPool.free(animation);
        } else if (animation == leftAnimation) {
            leftPool.free(animation);
        } else {
            throw new NullPointerException("Unknown animation. Cannot free it");
        }
    }

    public EnemyAnimationPool getLeftPool() {
        return leftPool;
    }

    public EnemyAnimationPool getRightPool() {
        return rightPool;
    }

    public EnemyAnimationPool getUpPool() {
        return upPool;
    }

    public EnemyAnimationPool getDownPool() {
        return downPool;
    }

    public Animation getUpAnimation() {
        return upAnimation;
    }

    public Animation getDownAnimation() {
        return downAnimation;
    }

    public Animation getLeftAnimation() {
        return leftAnimation;
    }

    public Animation getRightAnimation() {
        return rightAnimation;
    }

}
