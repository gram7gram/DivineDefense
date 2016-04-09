package ua.gram.model.actor.misc;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class UpgradeButton extends Button {

    private final CustomLabel label;

    public UpgradeButton(Skin skin, String style) {
        super(skin, style);
        label = new CustomLabel("", skin, "16_tinted");
        label.setVisible(true);
        label.setAlignment(Align.center);

        add(label).size(20).padTop(45 - 20).padLeft(45 - 20);
    }

    public void updatePrice(String price) {
        label.updateText(price);
    }

}
