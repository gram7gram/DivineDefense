package ua.gram.controller.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.controller.pool.animation.EnemyAnimationPool;
import ua.gram.model.Player;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationController implements AnimationController {

    public static final float DELAY = 1 / 10f;
    private final Skin skin;
    private final Animation upAnimation;
    private final Animation downAnimation;
    private final Animation leftAnimation;
    private final Animation rightAnimation;
    private Enemy enemy;
    private EnemyAnimationPool leftPool;
    private EnemyAnimationPool rightPool;
    private EnemyAnimationPool upPool;
    private EnemyAnimationPool downPool;

    public EnemyAnimationController(Skin skin, Enemy enemy) {
        this.skin = skin;
        this.enemy = enemy;
        upPool = new EnemyAnimationPool(setAnimationRegion(Types.UP));
        leftPool = new EnemyAnimationPool(setAnimationRegion(Types.LEFT));
        rightPool = new EnemyAnimationPool(setAnimationRegion(Types.RIGHT));
        downPool = new EnemyAnimationPool(setAnimationRegion(Types.DOWN));
        upAnimation = upPool.obtain();
        leftAnimation = leftPool.obtain();
        rightAnimation = rightPool.obtain();
        downAnimation = downPool.obtain();
    }

    public EnemyAnimationController(Skin skin) {
        this.skin = skin;
        upPool = new EnemyAnimationPool(setAnimationRegion(Types.UP));
        leftPool = new EnemyAnimationPool(setAnimationRegion(Types.LEFT));
        rightPool = new EnemyAnimationPool(setAnimationRegion(Types.RIGHT));
        downPool = new EnemyAnimationPool(setAnimationRegion(Types.DOWN));
        upAnimation = upPool.obtain();
        leftAnimation = leftPool.obtain();
        rightAnimation = rightPool.obtain();
        downAnimation = downPool.obtain();
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public TextureRegion[] setAnimationRegion(Types animationType) {
        String region = enemy.getClass().getSimpleName()
                + "_" + Player.SYSTEM_FRACTION;
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
            case SPAWN:
                region += "_Spawn";
                break;
            default:
                throw new NullPointerException("Unknown Enemy Animation id: " + animationType);
        }
        TextureRegion[][] regions = skin.getRegion(region).split(enemy.animationWidth, enemy.animationHeight);
        return regions[0];
    }

    @Override
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
