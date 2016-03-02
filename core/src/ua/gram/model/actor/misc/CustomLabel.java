package ua.gram.model.actor.misc;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CustomLabel extends Label {

    private final StringBuilder labelText;
    private CharSequence previousText;

    public CustomLabel(CharSequence text, Skin skin, String styleName) {
        super(text, skin, styleName);
        labelText = new StringBuilder();
        previousText = null;
        updateText(text);
        setWrap(false);
    }

    @Override
    public void act(float delta) {
        setText(labelText);
        setDebug(DDGame.DEBUG);
        super.act(delta);
    }

    public void updateText(CharSequence text) {
        if (text.equals(previousText)) return;
        invalidate();
        labelText.setLength(0);
        labelText.append(text);
        previousText = text;
        setSize(getPrefWidth(), getPrefHeight());
    }


}
