package ua.gram.model.prototype;

import ua.gram.model.prototype.progress.ProgressPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class PlayerPrototype extends Prototype {
    public long id;
    public int health;
    public int coins;
    public int gems;
    public String fraction;
    public ProgressPrototype progress;
}
