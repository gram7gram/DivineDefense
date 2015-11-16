package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.gram.model.Animator;
import ua.gram.model.actor.GameActor;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AnimationControllerInterface<A extends GameActor> {

    float DELAY = 1 / 10f;

    /**
     * Gets corresponding Atlas region from
     * the texture and splits it, acording
     * to animationWidth and animationHeight.
     *
     * @param animationType type of the animation region to search for
     * @return splitted array of tiles
     */
    TextureRegion[] setAnimationRegion(Animator.Types type, Path.Types direction, Animator.Types ability) throws Exception;

    /**
     * Puts animation in corresponding pool.
     *
     * @param animation
     */
    void free(Animator.Types type, Path.Types direction, Animation animation);

    void init(A actor);

    Animation obtain(Animator.Types type, Path.Types direction) throws Exception;

}
