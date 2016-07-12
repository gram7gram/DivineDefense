package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Group;

import ua.gram.DDGame;
import ua.gram.model.actor.GameActor;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class ActorGroup<A extends GameActor> extends Group {

    protected final A root;
    protected final TargetPoints targetPoints;

    public ActorGroup(A root) {
        this.root = root;
        this.targetPoints = new TargetPoints();
        addActor(targetPoints);
    }

    public A getRootActor() {
        return root;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG, true);
        if (!targetPoints.hasActorGroup()) {
            targetPoints.setGroup(this);
        }
        setOrigin(root.getOriginX(), root.getOriginY());
    }

    public Layer getLayer() {
        return super.getParent() instanceof Layer ? (Layer) super.getParent() : null;
    }

    @Override
    public float getOriginX() {
        return targetPoints.getOrigin().getX();
    }

    @Override
    public float getOriginY() {
        return targetPoints.getOrigin().getY();
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

    public TargetPoints getTargetPoints() {
        return targetPoints;
    }
}