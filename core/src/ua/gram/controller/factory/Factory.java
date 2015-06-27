package ua.gram.controller.factory;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface Factory<V> {

    V create(DDGame game);

    V create(DDGame game, Class<? extends V> type);
}
