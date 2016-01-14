package ua.gram.model.prototype.market;

import ua.gram.model.prototype.PrototypeInterface;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class MarketItemPrototype implements PrototypeInterface {
    /**
     * Item header
     */
    public String name;
    /**
     * Style of the header
     */
    public String nameStyle;
    /**
     * Purchase button style
     */
    public String buttonStyle;
    /**
     * Purchase button text
     */
    public String button;
    /**
     * Styleof the description
     */
    public String description;
    /**
     * Item description
     */
    public String descriptionStyle;
    /**
     * Currency of the offer
     */
    public String currency;
    /**
     * Price of the offer
     */
    public byte price;
    /**
     * Icon of the item
     */
    public String icon;
    /**
     * Background of the tile
     */
    public String background;
}
