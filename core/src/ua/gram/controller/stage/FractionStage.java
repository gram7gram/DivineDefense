package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.Player;
import ua.gram.model.group.ConfirmationGroup;
import ua.gram.view.screen.MainMenuScreen;

/**
 * NOTE Demon button is disabled
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class FractionStage extends Stage {

    private final Group fractions;
    private final DDGame game;
    private ConfirmationGroup confirmationGroup;

    public FractionStage(final DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        this.game = game;
        Skin skin = game.getResources().getSkin();

        Label header = new Label("Choose your fraction", skin, "header1white");
        header.setVisible(true);
        header.setPosition((DDGame.WORLD_WIDTH - header.getWidth()) / 2f, DDGame.WORLD_HEIGHT - header.getHeight() - 10);

        Button option1 = new Button(skin, "demon");
        option1.setPosition(0, 0);
        option1.setVisible(true);
        option1.setSize(DDGame.WORLD_WIDTH / 2, DDGame.WORLD_HEIGHT);
        option1.setTouchable(Touchable.disabled);

        Button option2 = new Button(skin, "angel");
        option2.setPosition(DDGame.WORLD_WIDTH / 2, 0);
        option2.setVisible(true);
        option2.setSize(DDGame.WORLD_WIDTH / 2, DDGame.WORLD_HEIGHT);
        option2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player.PLAYER_FRACTION = DDGame.ANGEL;
                Player.SYSTEM_FRACTION = DDGame.DEMON;
                if (confirmationGroup == null) createDialog();
                displayConfirmation();
            }
        });


        fractions = new Group();

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

        if ((!game.getPlayer().isDefault() && Player.PLAYER_FRACTION != null) || DDGame.DEBUG) {
//            option2.setTouchable(Touchable.disabled);
            Button cont = new TextButton("Continue", skin, "green-button");
            cont.setSize(240, 80);
            float x1 = "Angel".equals(Player.PLAYER_FRACTION) ?
                    option2.getX() + (option2.getWidth() - cont.getWidth()) / 2f
                    : option1.getX() + (option1.getWidth() + cont.getWidth()) / 2f;
            float y1 = 20 + cont.getHeight();
            cont.setPosition(x1, y1);
            cont.setTouchable(Touchable.enabled);
            cont.setVisible(true);

            Button rest = new TextButton("Restart", skin, "blue-button");
            rest.setSize(240, 80);
            float x2 = "Angel".equals(Player.PLAYER_FRACTION) ?
                    option2.getX() + (option2.getWidth() - rest.getWidth()) / 2f
                    : option1.getX() + (option1.getWidth() + rest.getWidth()) / 2f;
            float y2 = 10;
            rest.setPosition(x2, y2);
            rest.setTouchable(Touchable.enabled);
            rest.setVisible(true);

            fractions.addActor(rest);
            fractions.addActor(cont);
        } else {
            option2.setTouchable(Touchable.enabled);
        }
        this.addActor(fractions);
//        DefeatWindow window = new DefeatWindow(game, this);
//        window.setVisible(true);
//        this.addActor(window);
    }

    private void createDialog() {
        confirmationGroup = new ConfirmationGroup(game,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        displayConfirmation();
                    }
                },
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.app.log("INFO", "Player Fraction set to " + Player.PLAYER_FRACTION);
                        Gdx.app.log("INFO", "System Fraction set to " + Player.SYSTEM_FRACTION);
                        game.setScreen(new MainMenuScreen(game));
                    }
                },
                "CONFIRM",
                "You have chosen\n" + Player.PLAYER_FRACTION + " fraction"
        );
        this.addActor(confirmationGroup);
    }

    private void displayConfirmation() {
        fractions.setTouchable(fractions.isTouchable() ? Touchable.disabled : Touchable.enabled);
        confirmationGroup.setVisible(!confirmationGroup.isVisible());
        Gdx.app.log("INFO", "Confirmation window is " + (confirmationGroup.isVisible() ? "" : "in") + "visible");
    }

    public ConfirmationGroup getGroup() {
        return confirmationGroup;
    }

}
