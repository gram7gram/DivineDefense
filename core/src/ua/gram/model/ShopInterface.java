package ua.gram.model;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface ShopInterface<A> {
    A preorder(String type, float x, float y);

    void buy(A item, float x, float y);

    void refund(A item);

    void sell(A item);
}
