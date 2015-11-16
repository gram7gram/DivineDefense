package ua.gram.controller.factory;

import ua.gram.DDGame;
import ua.gram.model.prototype.Prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface FactoryInterface<A, P extends Prototype> {
    A create(DDGame game, P prototype);
}
