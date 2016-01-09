package ua.gram.model.state;

import ua.gram.model.actor.GameActor;

/**
 * Handles state-logic on Actor update.
 *
 * NOTE States should not contain Actor-dependant properties!
 *
 * @author Gram <gram7gram@gmail.com>
 */
public interface StateInterface<T extends GameActor> {

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
