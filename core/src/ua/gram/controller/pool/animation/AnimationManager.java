package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ua.gram.model.Initializer;
import ua.gram.model.prototype.GameActorPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AnimationManager<A extends GameActorPrototype, T, D> extends Initializer {

    /**
     * Gets corresponding Atlas region from
     * the texture and split it, acording
     * to animationWidth and animationHeight, available for owner Actor
     *
     * @return splitted array of tiles
     */
    TextureRegion[] getAnimationRegion(A prototype, T type1, D type2);

    String getAnimationName(A prototype, T type1, D type2);

    AnimationPool get(T type1, D type2);

}
