package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AnimationController {

    /**
     * Gets corresponding Atlas region form the texture and splits it, acording to animationWidth and animationHeight.
     *
     * @param animationType type of the animation region to search for
     * @return splitted array of tiles
     */
    TextureRegion[] setAnimationRegion(Types animationType, int animationWidth, int animationHeight);

    /**
     * Puts animation in corresponding pool.
     *
     * @param animation
     */
    void free(Animation animation);

    /**
     * The states of Actors in the game, including Enemy and Tower.
     */
    enum Types {

        //For Tower
        IDLE, SHOOT, BUILD, SELL, LAND, AIR, LANDAIR,
        //For Enemy
        LEFT, RIGHT, DOWN, UP, DEAD, SPAWN, CLEAR
    }
}
