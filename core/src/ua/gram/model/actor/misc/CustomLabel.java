package ua.gram.model.actor.misc;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CustomLabel extends Label {

    private String text;

    public CustomLabel(String text, Skin skin) {
        super(text, skin);
        this.text = text;
        this.setDebug(DDGame.DEBUG);
        this.setSize(150, 60);//better then (0,0)
    }

    public CustomLabel(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        this.text = text;
        this.setDebug(DDGame.DEBUG);
        this.setSize(150, 60);//better then (0,0)
    }

    @Override
    public void act(float delta) {
        this.setText(text);
        super.act(delta);
    }

    @Override
    public void setText(CharSequence text) {
        this.text = text.toString();
    }
}
