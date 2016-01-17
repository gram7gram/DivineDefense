package ua.gram.model.actor.misc;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CustomLabel extends Label {

    private StringBuilder label;
    private CharSequence previousValue;

    public CustomLabel(String text, Skin skin, String styleName) {
        super(text.toUpperCase(), skin, styleName);
        label = new StringBuilder();
        label.append(text);
        previousValue = null;
    }

    @Override
    public void act(float delta) {
        this.setText(label);
        this.setDebug(DDGame.DEBUG);
        super.act(delta);
    }

    public void updateText(CharSequence text) {
        invalidate();
        if (!text.equals(previousValue)) {
            label.setLength(0);
            label = label.append(text);
            if (previousValue == null) setVisible(true);
            previousValue = text;
            setSize(getPrefWidth(), getPrefHeight());
            setY(DDGame.WORLD_HEIGHT - getHeight() - 5);
        }
    }

}
