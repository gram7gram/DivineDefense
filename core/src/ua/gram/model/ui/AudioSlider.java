package ua.gram.model.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import ua.gram.model.prototype.AudioPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AudioSlider extends Slider {

    public AudioSlider(Skin skin, String name, AudioPrototype prototype) {
        super(0, 100, 1, false, skin, name);

        setDisabled(!prototype.state);
        setValue(prototype.volume * 100);

//        addListener(new InputListener() {
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                event.stop();
//                return false;
//            }
//        });
    }
}
