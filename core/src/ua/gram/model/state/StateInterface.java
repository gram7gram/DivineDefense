package ua.gram.model.state;

import ua.gram.model.actor.GameActor;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface StateInterface<T extends GameActor> {

    /**
     * Execute before actual management:
     * load nessessary recources, objects etc.
     */
    void preManage(T actor) throws Exception;

    /**
     * Implement state logic
     */
    void manage(T actor, float delta) throws Exception;

    /**
     * Clean-un and reset state
     */
    void postManage(T actor) throws Exception;
}
