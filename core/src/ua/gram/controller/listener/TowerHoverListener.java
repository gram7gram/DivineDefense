package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerHoverListener extends InputListener {

    private final Tower tower;

    public TowerHoverListener(Tower tower) {
        this.tower = tower;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        tower.setIsAllowedShader(true);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.exit(event, x, y, pointer, fromActor);
        tower.setIsAllowedShader(false);
    }
}
