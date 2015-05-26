package ua.gram.controller.tower;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.model.Player;
import ua.gram.model.actor.Tower;

/**
 * NOTE Don't create unnecessary LevelAnimation.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerAnimationController {

    public static final float DELAY = 1 / 20f;
    private final Skin skin;
    private TowerLevelAnimationContainer level1Animation;
    private TowerLevelAnimationContainer level2Animation;
    private TowerLevelAnimationContainer level3Animation;
    private TowerLevelAnimationContainer level4Animation;
    private Tower tower;

    public TowerAnimationController(Skin skin, Tower tower) {
        this.skin = skin;
        this.tower = tower;
        level1Animation = new TowerLevelAnimationContainer(this, 1);
        level2Animation = new TowerLevelAnimationContainer(this, 2);
        level3Animation = new TowerLevelAnimationContainer(this, 3);
        level4Animation = new TowerLevelAnimationContainer(this, 4);
    }

    public TextureRegion[] setAnimationRegion(AnimationController.Types animationType, int level) {
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
        TextureRegion[][] regions = skin.getRegion(region).split(tower.animationWidth, tower.animationHeight);
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
