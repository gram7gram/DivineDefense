package ua.gram.model;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface ShopInterface<A extends Actor> {
    A preorder(String type, float x, float y);

    void buy(A item, float x, float y);

    void refund(A item);

    void sell(A item);
}
