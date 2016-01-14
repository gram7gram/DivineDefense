package ua.gram.model.prototype.market;

import ua.gram.model.prototype.PrototypeInterface;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class MarketCategoryPrototype implements PrototypeInterface {
    /**
     * Market header label
     */
    public String category;
    /**
     * Icon in sidebar
     */
    public String icon;
    /**
     * Items for this category.
     * Will be toggled on click
     */
    public MarketItemPrototype[] items;
}
