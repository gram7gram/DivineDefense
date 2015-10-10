package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class MarketCategoryPrototype extends Prototype {
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
