package ua.gram.model.state;

import ua.gram.model.actor.GameActor;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface State<T extends GameActor> {

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
