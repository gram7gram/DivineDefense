package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.List;

import ua.gram.DDGame;
import ua.gram.controller.factory.DefeatOptionFactory;
import ua.gram.controller.stage.StageHolder;
import ua.gram.model.Initializer;
import ua.gram.model.Resetable;
import ua.gram.model.actor.misc.DefeatOption;
import ua.gram.model.prototype.ui.DefeatOptionPrototype;
import ua.gram.model.prototype.ui.window.DefeatWindowPrototype;
import ua.gram.model.prototype.ui.window.WindowPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatWindow extends AbstractWindow implements Initializer, Resetable {

    private final DDGame game;
    private final DefeatWindowPrototype prototype;
    private StageHolder stageHolder;

    public DefeatWindow(final DDGame game, WindowPrototype proto) {
        super(game, proto);

        if (!(proto instanceof DefeatWindowPrototype))
            throw new IllegalArgumentException("Prototype is not instance of DefeatWindowPrototype");

        this.game = game;
        this.prototype = (DefeatWindowPrototype) proto;

        Skin skin = game.getResources().getSkin();

        Label title1 = new Label(prototype.headerText, skin, prototype.headerTextStyle);
        Label title2 = new Label(prototype.subHeaderText, skin, prototype.subHeaderTextStyle);

        title1.setVisible(true);
        title2.setVisible(true);

        padLeft(40).padRight(40).padTop(15).padBottom(15);
        add(title1).expandX().fillX().colspan(3).padTop(5)
                .width(title1.getWidth())
                .height(title1.getHeight())
                .center().row();
        add(title2).expandX().fillX().colspan(3).padTop(10).padBottom(10)
                .width(title2.getWidth())
                .height(title2.getHeight())
                .center().row();

        addAction(
                Actions.parallel(
                        Actions.alpha(0),
                        Actions.alpha(1, .8f)
                )
        );

        Log.info("DefeatWindow is OK");
    }

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    @Override
    public void init() {
        if (stageHolder == null) throw new NullPointerException("Missing stage holder");

        List<DefeatOptionPrototype> options = new ArrayList<>(prototype.options.length);
        for (DefeatOptionPrototype p : prototype.options) {
            options.add(p.order, p);
        }

        for (DefeatOptionPrototype optionPrototype : options) {
            final DefeatOption option = DefeatOptionFactory.create(game, optionPrototype);
            add(option)
                    .width(option.getWidth())
                    .height(option.getHeight())
                    .center();

            option.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (!option.getButton().isDisabled()) {
                        stageHolder.getUiStage().hideDefeatWindow();
                    }
                }
            });
        }
    }

    @Override
    public void resetObject() {
        resetActors(getChildren());
    }

    private void resetActors(SnapshotArray<Actor> actors) {
        for (Actor actor : actors) {
            if (actor instanceof Group) {
                resetActors(((Group) actor).getChildren());
            } else if (actor instanceof Resetable) {
                ((Resetable) actor).resetObject();
            }
        }
    }
}