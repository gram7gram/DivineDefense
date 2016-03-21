package ua.gram.model.actor.misc;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;
import ua.gram.model.prototype.ui.DefeatOptionPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DelayedDefeatOption extends DefeatOption {

    private final CustomLabel countdownLabel;
    private float count;

    public DelayedDefeatOption(DDGame game, DefeatOptionPrototype prototype) {
        super(game, prototype);

        option.setDisabled(true);
        option.setTouchable(Touchable.disabled);

        countdownLabel = new CustomLabel(
                getText(),
                game.getResources().getSkin(),
                "small_tinted");
        countdownLabel.setAlignment(Align.center);
        countdownLabel.setFillParent(true);

        addActor(countdownLabel);

        resetObject();
    }

    private String getText() {
        return "" + (int) count;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getParent().isVisible()) {
            if (count > 0) {
                countdownLabel.updateText(getText());
                count -= delta;
                if (count <= 1) {
                    countdownLabel.setVisible(false);
                    option.setDisabled(false);
                    option.setTouchable(Touchable.enabled);
                }
            }
        } else {
            resetObject();
        }
    }

    @Override
    public void resetObject() {
        super.resetObject();
        count = prototype.delay;
        countdownLabel.setVisible(true);
    }
}
