package ua.gram.controller.voter;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface Voter {

    boolean isGranted(int x, int y, String property);
}
