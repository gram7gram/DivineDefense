package ua.gram.model.actor.enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AbilityUserInterface {

    void ability();

    float getAbilityDelay();

    float getAbilityDuration();

    boolean isAbilityPossible();

    boolean isAbilityPossible(float delta);

    boolean isAbilityExecuted();
}
