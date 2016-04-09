package ua.gram.utils;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Vector4 {
    public float x;
    public float y;
    public float z;
    public float t;

    public Vector4(float x) {
        this.x = x;
        this.y = x;
        this.z = x;
        this.t = x;
    }

    public Vector4(float x, float y, float z, float t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getT() {
        return t;
    }

    public void setT(float t) {
        this.t = t;
    }
}
