package ua.gram.model;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Speed {

    private float value = 1;

    public float increase() {
        value = .5f;
        return value;
    }

    public float decrease() {
        value = 1;
        return value;
    }

    public void reset() {
        this.value = 1;
    }

    public float getValue() {
        return value;
    }

    public boolean isIncreased() {
        return value < 1;
    }
}
