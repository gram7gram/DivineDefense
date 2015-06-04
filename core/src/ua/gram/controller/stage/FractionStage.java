package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
    private final Button option1;
    private ConfirmationGroup confirmationGroup;

    public FractionStage(final DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        this.game = game;

        option1 = new Button(game.getResources().getSkin(), "demon");
        option1.setPosition(0, 0);
        option1.setVisible(true);
        option1.setSize(DDGame.WORLD_WIDTH / 2, DDGame.WORLD_HEIGHT);
        option1.setTouchable(Touchable.disabled);
        option1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player.PLAYER_FRACTION = DDGame.DEMON;
                Player.SYSTEM_FRACTION = DDGame.ANGEL;
                if (confirmationGroup == null) createDialog();
                displayConfirmation();
            }
        });

        Button option2 = new Button(game.getResources().getSkin(), "angel");
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
                displayConfirmation();
            }
        });

        fractions = new Group();
        fractions.addActor(option1);
        fractions.addActor(option2);

        this.addActor(fractions);
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

    public Button getDemonButton() {
        return option1;
    }

    public ConfirmationGroup getGroup() {
        return confirmationGroup;
    }

}
