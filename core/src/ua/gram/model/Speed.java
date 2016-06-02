package ua.gram.model;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Speed {

    private final float defaultSpeed;
    private float value;

    public Speed(float speed) {
        value = speed;
        defaultSpeed = speed;
    }

    public Speed() {
        this(1);
    }

    public float increase() {
        value = defaultSpeed / 2;
        return value;
    }

    public float decrease() {
        value = defaultSpeed;
        return value;
    }

    public void reset() {
        value = defaultSpeed;
    }

    public float getValue() {
        return value;
    }

    public boolean isIncreased() {
        return value < defaultSpeed;
    }
}
