package ua.gram.controller.factory;

import ua.gram.DDGame;
import ua.gram.model.actor.misc.DefeatOption;
import ua.gram.model.actor.misc.DelayedDefeatOption;
import ua.gram.model.prototype.ui.DefeatOptionPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatOptionFactory {

    public static DefeatOption create(DDGame game, DefeatOptionPrototype prototype) {
        if (prototype.delay > 0) {
            return new DelayedDefeatOption(game, prototype);
        } else {
            return new DefeatOption(game, prototype);
        }
    }
}
