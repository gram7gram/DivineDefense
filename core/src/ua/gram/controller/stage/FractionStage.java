package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.Player;
import ua.gram.model.group.ConfirmationGroup;
import ua.gram.view.screen.MainMenuScreen;

/**
 * NOTE Demon button is disabled
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class FractionStage extends AbstractStage {

    private final Group fractions;
    private final DDGame game;
    private ConfirmationGroup confirmationGroup;
    private ConfirmationGroup continueGroup;


    public FractionStage(final DDGame game) {
        super(game);
        this.game = game;
        final Skin skin = game.getResources().getSkin();

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
        option2.setTouchable(Touchable.enabled);
        option2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player.PLAYER_FRACTION = DDGame.ANGEL;
                Player.SYSTEM_FRACTION = DDGame.DEMON;
                if (confirmationGroup == null) createDialog();
                displayConfirmation(confirmationGroup);
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
        this.addActor(fractions);

        //TODO Display continueGroup only if Player has valid save
        Button button = new TextButton("?", skin, "green-button");
        button.setPosition(10, 10);
        button.setSize(60, 60);
        button.setVisible(true);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (continueGroup == null) {
                    continueGroup = new ConfirmationGroup(game,
                            (new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    displayConfirmation(continueGroup);
                                }
                            }),
                            (new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    game.setScreen(new MainMenuScreen(game));
                                }
                            }),
                            (new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    game.setScreen(new MainMenuScreen(game));
                                }
                            }),
                            "Continue", "Restart", "Continue playing");
                    addActor(continueGroup);
                }
                displayConfirmation(continueGroup);
            }
        });
        this.addActor(button);

        this.setDebugAll(DDGame.DEBUG);
    }

    private void createDialog() {
        confirmationGroup = new ConfirmationGroup(game,
                (new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        displayConfirmation(confirmationGroup);
                    }
                }),
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Log.info("Player Fraction set to " + Player.PLAYER_FRACTION);
                        Log.info("System Fraction set to " + Player.SYSTEM_FRACTION);
                        game.setScreen(new MainMenuScreen(game));
                    }
                }, null,
                "Confirm", null,
                "You have chosen\n" + Player.PLAYER_FRACTION + " fraction"
        );
        this.addActor(confirmationGroup);
    }

    private void displayConfirmation(Group group) {
        fractions.setTouchable(fractions.isTouchable() ? Touchable.disabled : Touchable.enabled);
        group.setVisible(!group.isVisible());
        Log.info(group.getClass().getSimpleName() + " is " + (group.isVisible() ? "" : "in") + "visible");
    }

    public ConfirmationGroup getGroup() {
        return confirmationGroup;
    }

}
