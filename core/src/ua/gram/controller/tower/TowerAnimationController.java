package ua.gram.controller.tower;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.model.Player;
import ua.gram.model.actor.Tower;

/**
 * TODO Don't create unnecessary LevelAnimation.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerAnimationController {

    public static final float DELAY = 1 / 20f;
    private final TextureAtlas atlas;
    private TowerLevelAnimationContainer level1Animation;
    private TowerLevelAnimationContainer level2Animation;
    private TowerLevelAnimationContainer level3Animation;
    private TowerLevelAnimationContainer level4Animation;
    private Tower tower;

    public TowerAnimationController(TextureAtlas atlas, Tower tower) {
        this.atlas = atlas;
        this.tower = tower;
        int animationWidth = (int) tower.getWidth();
        int animationHeight = (int) tower.getHeight();
        level1Animation = new TowerLevelAnimationContainer(this, 1, animationWidth, animationHeight);
        level2Animation = new TowerLevelAnimationContainer(this, 2, animationWidth, animationHeight);
        level3Animation = new TowerLevelAnimationContainer(this, 3, animationWidth, animationHeight);
        level4Animation = new TowerLevelAnimationContainer(this, 4, animationWidth, animationHeight);
    }

    public TextureRegion[] setAnimationRegion(AnimationController.Types animationType, int level, int animationWidth, int animationHeight) {
        String region = tower.getClass().getSimpleName()
                + "_" + Player.PLAYER_FRACTION
                + "_Lvl" + level;
        switch (animationType) {
            case IDLE:
                region += "_Idle";
                break;
            case BUILD:
                region += "_Build";
                break;
            case SELL:
                region += "_Sell";
                break;
            case SHOOT:
                region += "_Shoot";
                break;
            default:
                throw new NullPointerException("Unknown Enemy Animation id: " + animationType);
        }
        TextureRegion[][] regions = atlas.findRegion(region).split(animationWidth, animationHeight);
        return regions[0];
    }

    public TowerLevelAnimationContainer getLevelAnimationContainer(int level) {
        switch (level) {
            case 1:
                return level1Animation;
            case 2:
                return level2Animation;
            case 3:
                return level3Animation;
            case 4:
                return level4Animation;
            default:
                throw new NullPointerException("Unknown Tower level: " + level);
        }
    }

}
