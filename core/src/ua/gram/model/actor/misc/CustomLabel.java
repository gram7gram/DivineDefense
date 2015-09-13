package ua.gram.model.actor.misc;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CustomLabel extends Label {

    private StringBuilder label = new StringBuilder();

    public CustomLabel(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        label.append(text);
        this.setDebug(DDGame.DEBUG);
    }

    @Override
    public void act(float delta) {
        this.setText(label);
        super.act(delta);
    }

    public void updateText(CharSequence text) {
        invalidate();
        label.setLength(0);
        this.label = label.append(text);
    }

}
