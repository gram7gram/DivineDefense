package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ua.gram.model.prototype.GameActorPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AnimationControllerInterface<A extends GameActorPrototype, T, D> {

    /**
     * Gets corresponding Atlas region from
     * the texture and split it, acording
     * to animationWidth and animationHeight, available for owner Actor
     *
     * @return splitted array of tiles
     */
    TextureRegion[] getAnimationRegion(A prototype, T type1, D type2);

    boolean init(A prototype);

    AnimationPool get(T type1, D type2);

}
