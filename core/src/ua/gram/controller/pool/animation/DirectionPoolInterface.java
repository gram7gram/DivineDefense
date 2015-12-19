package ua.gram.controller.pool.animation;

import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface DirectionPoolInterface {
    AnimationPool get(Path.Types type);
}
