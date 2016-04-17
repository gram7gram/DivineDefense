package ua.gram.controller.pool.animation;

import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface DirectionPool {
    AnimationPool get(Path.Types type);
}
