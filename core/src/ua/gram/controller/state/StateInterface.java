package ua.gram.controller.state;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Handles state-logic on Actor update.
 *
 * NOTE States should not contain Actor-dependant property!
 *
 * @author Gram <gram7gram@gmail.com>
 */
public interface StateInterface<T extends Actor> {

    /**
     * Execute before actual management:
     * load nessessary recources, objects etc.
     */
    void preManage(T actor);

    /**
     * Implement state logic
     */
    void manage(T actor, float delta);

    /**
     * Clean-un and reset state
     */
    void postManage(T actor);
}
