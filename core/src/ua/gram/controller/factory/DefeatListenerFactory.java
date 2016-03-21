package ua.gram.controller.factory;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.listener.ContinueListener;
import ua.gram.controller.listener.RestartClickListener;
import ua.gram.model.prototype.ui.DefeatOptionPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatListenerFactory {

    public static ClickListener create(DDGame game, DefeatOptionPrototype prototype) {
        if (prototype.cost == 0) {
            return new RestartClickListener(game, game.getPlayer().getLevel());
        } else {
            return new ContinueListener(game, prototype.health, prototype.cost);
        }
    }
}
