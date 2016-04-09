package ua.gram.model.actor.misc;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import ua.gram.DDGame;
import ua.gram.controller.factory.DefeatListenerFactory;
import ua.gram.model.Resetable;
import ua.gram.model.prototype.ui.DefeatOptionPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatOption extends Group implements Resetable {

    protected final DDGame game;
    protected final Button option;
    protected final DefeatOptionPrototype prototype;

    public DefeatOption(DDGame game, DefeatOptionPrototype prototype) {
        this.game = game;
        this.prototype = prototype;

        option = new Button(game.getResources().getSkin(), prototype.style);
        option.setSize(prototype.width, prototype.height);
        setSize(prototype.width, prototype.height);

        option.addListener(DefeatListenerFactory.create(game, prototype));

        addActor(option);
    }

    @Override
    public void resetObject() {
        if (prototype.cost > 0) {
            option.setDisabled(game.getPlayer().getGems() < prototype.cost);
            option.setTouchable(option.isDisabled() ? Touchable.disabled : Touchable.enabled);
        }
    }

    public Button getButton() {
        return option;
    }
}
