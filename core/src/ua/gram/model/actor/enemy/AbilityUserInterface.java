package ua.gram.model.actor.enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AbilityUserInterface {

    boolean ability();

    float getAbilityDelay();

    float getAbilityDuration();

    float getAbilityDurationCount();

    void setAbilityDurationCount(float duration);

    void addAbilityDurationCount(float duration);

    boolean isAbilityPossible();

    boolean isAbilityPossible(float delta);

    boolean isAbilityExecuted();

    void setAbilityExecuted(boolean executed);

    boolean isInterrupted();
}
