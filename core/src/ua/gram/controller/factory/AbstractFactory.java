package ua.gram.controller.factory;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface AbstractFactory<V> {

    V create(Class<? extends V> type);
}
