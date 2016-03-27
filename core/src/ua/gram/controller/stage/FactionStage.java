package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.Player;
import ua.gram.model.window.ConfirmWindow;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram gram7gram@gmail.com
 */
public class FactionStage extends AbstractStage {

    private final Skin skin;

    public FactionStage(final DDGame game) {
        super(game);
        skin = game.getResources().getSkin();

        Label header = new Label("Choose your fraction", skin, "header1white");
        header.setVisible(true);
        header.setPosition((DDGame.WORLD_WIDTH - header.getWidth()) / 2f,
                DDGame.WORLD_HEIGHT - header.getHeight() - 10);

        Button option1 = new Button(skin, "faction-1");
        option1.setBounds(0, 0, DDGame.WORLD_WIDTH / 2, DDGame.WORLD_HEIGHT);
        option1.setTouchable(Touchable.disabled);
        option1.setName(DDGame.FACTION2);

        Button lock = new Button(skin, "button-lock");
        lock.setTouchable(Touchable.disabled);
        lock.setPosition(
                option1.getX() + (option1.getWidth() - lock.getWidth()) / 2,
                option1.getY() + (option1.getHeight() - lock.getHeight()) / 2);
        option1.addActor(lock);

        Button option2 = new Button(skin, "faction-2");
        option2.setBounds(DDGame.WORLD_WIDTH / 2, 0,
                DDGame.WORLD_WIDTH / 2, DDGame.WORLD_HEIGHT);
        option2.setTouchable(Touchable.enabled);
        option2.setName(DDGame.FACTION1);
        option2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String fraction = event.getTarget().getName();
                if (fraction == null)
                    throw new GdxRuntimeException("Actor should have a name for this listener");
                game.getPlayer().setFraction(fraction);
                createDialog(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Log.info("Player's fraction: " + Player.PLAYER_FACTION);
                        game.setScreen(new MainMenuScreen(game));
                    }
                }, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        game.getPlayer().resetFraction();
                    }
                });
            }
        });

        Group fractions = new Group();

        option1.addAction(
                Actions.sequence(
                        Actions.parallel(
                                Actions.moveTo(-450, 0),
                                Actions.alpha(0)
                        ),
                        Actions.parallel(
                                Actions.fadeIn(0.5f),
                                Actions.moveTo(0, 0, 0.5f))
                )
        );
        option2.addAction(
                Actions.sequence(
                        Actions.parallel(
                                Actions.moveTo(1350, 0),
                                Actions.alpha(0)
                        ),
                        Actions.parallel(
                                Actions.fadeIn(0.5f),
                                Actions.moveTo(450, 0, 0.5f))
                )
        );

        fractions.addActor(option1);
        fractions.addActor(option2);
        fractions.addActor(header);

        addActor(fractions);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebugAll(DDGame.DEBUG);
    }

    private void createDialog(ClickListener onConfirm, ClickListener onCancel) {
        String msg = "You have chosen\n" + Player.PLAYER_FACTION + " faction";
        addActor(new ConfirmWindow(msg, skin, onConfirm, onCancel));
    }
}
