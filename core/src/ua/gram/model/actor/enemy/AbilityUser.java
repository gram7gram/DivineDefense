package ua.gram.model.actor.enemy;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AbilityUser {

    void ability(SequenceAction actions);

    float getAbilityDelay();

    float getAbilityDuration();
}
