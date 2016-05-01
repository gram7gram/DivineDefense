package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import ua.gram.DDGame;
import ua.gram.model.actor.GameActor;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class ActorGroup<A extends GameActor> extends Group {

    protected final A root;
    protected Actor origin;

    public ActorGroup(A root) {
        this.root = root;
    }

    public A getRootActor() {
        return root;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG, true);
        setOrigin(root.getOriginX(), root.getOriginY());
        if (!DDGame.PAUSE) {
            if (DDGame.DEBUG) {
                if (origin == null) {
                    origin = new Actor();
                    origin.setSize(3, 3);
                    origin.setVisible(true);
                    addActor(origin);
                }
                origin.setVisible(true);
                origin.setPosition(root.getOriginX() - 1, root.getOriginY() - 1);
            } else {
                if (origin != null)
                    origin.setVisible(false);
            }
        }
    }

    public Layer getLayer() {
        return super.getParent() instanceof Layer ? (Layer) super.getParent() : null;
    }

    @Override
    public float getOriginX() {
        return root.getOriginX();
    }

    @Override
    public float getOriginY() {
        return root.getOriginY();
    }

    @Override
    public float getHeight() {
        return root.getHeight();
    }

    @Override
    public float getWidth() {
        return root.getWidth();
    }

    @Override
    public float getY() {
        return root.getY();
    }

    @Override
    public float getX() {
        return root.getX();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        getRootActor().setVisible(visible);
    }
}