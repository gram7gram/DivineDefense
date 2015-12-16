package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.gram.model.Animator;
import ua.gram.model.PollableAnimation;
import ua.gram.model.actor.GameActor;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AnimationControllerInterface<A extends GameActor> {

    /**
     * Gets corresponding Atlas region from
     * the texture and splits it, acording
     * to animationWidth and animationHeight.
     *
     * @return splitted array of tiles
     */
    TextureRegion[] setAnimationRegion(Animator.Types type, Path.Types direction, Animator.Types ability) throws Exception;

    void init(A actor);

    PollableAnimation obtain(Animator.Types type, Path.Types direction) throws Exception;

}
