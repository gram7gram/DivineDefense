package ua.gram.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractGroup extends Group {

    public byte butHeight = 60;
    public final byte gap = 5;

    public AbstractGroup() {
        this.setDebug(DDGame.DEBUG);
    }
}
