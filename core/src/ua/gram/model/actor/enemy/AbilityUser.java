package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.prototype.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbilityUser extends Enemy implements AbilityUserInterface {

    private final float abilityDelay;
    private final float abilityDuration;
    private float delayAbility;

    public AbilityUser(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);
        abilityDelay = prototype.abilityDelay;
        abilityDuration = prototype.abilityDuration;
    }

    @Override
    public float getAbilityDelay() {
        return abilityDelay;
    }

    public void setAbilityDelay(float delayAbility) {
        this.delayAbility = delayAbility;
    }

    @Override
    public float getAbilityDuration() {
        return abilityDuration;
    }

    @Override
    public boolean isAbilityPossible(float delta) {
        if (delayAbility >= this.getAbilityDelay()) {
            delayAbility = 0;
            return true;
        } else {
            delayAbility += delta;
            return false;
        }
    }
}
