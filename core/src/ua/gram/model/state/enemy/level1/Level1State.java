package ua.gram.model.state.enemy.level1;

import ua.gram.DDGame;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.AbstractState;
import ua.gram.model.state.State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Level1State extends AbstractState<Enemy> implements State<Enemy> {

    private Animator.Types animationType;

    public Level1State(DDGame game) {
        super(game);
    }

    public Animator.Types getAnimationType() {
        return animationType;
    }

    public void setAnimationType(Animator.Types animationType) {
        this.animationType = animationType;
    }
}
