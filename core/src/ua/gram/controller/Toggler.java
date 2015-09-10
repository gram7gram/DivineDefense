package ua.gram.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Toggler implements Runnable {

    private Actor actor;

    public Toggler(Actor actor) {
        this.actor = actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public void run() {
        actor.setVisible(!actor.isVisible());
    }
}
