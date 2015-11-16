package ua.gram.model.state.enemy.level1;

import ua.gram.DDGame;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.StateInterface;
import ua.gram.model.state.enemy.EnemyState;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Level1State extends EnemyState implements StateInterface<Enemy> {

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
