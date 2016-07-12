package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TargetPoints extends Group {
    private final Actor origin;
    private final Actor base;
    private ActorGroup group;

    public TargetPoints() {
        origin = new Actor();
        base = new Actor();
        origin.setSize(3, 3);
        origin.setVisible(true);
        addActor(origin);

        base.setSize(3, 3);
        base.setVisible(true);
        addActor(base);
    }

    public boolean hasActorGroup() {
        return group != null;
    }

    public void setGroup(ActorGroup group) {
        this.group = group;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && group != null) {
            float offset = group.getHeight() / 5;
            float half = group.getWidth() / 2;
            origin.setPosition(group.getX() + half - 1, group.getY() + (4 * offset) - 1);
            base.setPosition(group.getX() + half - 1, group.getY() + offset - 1);
        }
    }

    public Actor getOrigin() {
        return origin;
    }

    public Actor getBase() {
        return base;
    }
}
