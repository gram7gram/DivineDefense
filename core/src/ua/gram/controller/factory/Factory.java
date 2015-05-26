package ua.gram.controller.factory;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface Factory<V> {

    V create(Class<? extends V> type);
}
