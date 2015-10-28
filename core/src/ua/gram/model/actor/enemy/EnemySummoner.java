package ua.gram.model.actor.enemy;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import ua.gram.DDGame;
import ua.gram.model.prototype.EnemyPrototype;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends Enemy implements Cloneable, AbilityUser {

    private final float abilityDelay;
    private final float abilityDuration;
    private float delayAbility;
    private Runnable level3Swapper;

    public EnemySummoner(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);
        abilityDelay = prototype.abilityDelay;
        abilityDuration = prototype.abilityDuration;
    }

    @Override
    public void ability(SequenceAction actions) {
        if (delayAbility == this.getAbilityDelay()) {
            if (level3Swapper == null) {
                final EnemyStateManager stateManager = this.getSpawner().getStateManager();
                final Enemy enemy = this;
                level3Swapper = new Runnable() {
                    @Override
                    public void run() {
                        stateManager.swapLevel3State(enemy, stateManager.getAbilityState());
                    }
                };
            }

            int index1 = actions.getActions().indexOf(actions.getActions().peek(), true);

            actions.addAction(
                    Actions.parallel(
                            Actions.run(level3Swapper),
                            Actions.moveBy(0, 0, abilityDuration)));

            int index2 = actions.getActions().indexOf(actions.getActions().peek(), true);

            actions.getActions().swap(index1, index2);

            delayAbility = 0;
        } else
            ++delayAbility;
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }

    @Override
    public float getAbilityDelay() {
        return abilityDelay;
    }

    @Override
    public float getAbilityDuration() {
        return abilityDuration;
    }
}
