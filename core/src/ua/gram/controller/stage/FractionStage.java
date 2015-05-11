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
import ua.gram.view.group.ConfirmationGroup;

/**
 * <pre>
 * TODO Create Unlock FRACTION dialog
 * TODO Do not show confirmation dialog if player is not new one.
 * NOTE Demon button is disabled
 * </pre>
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class FractionStage extends Stage {

    private final Group fractions;
    private final DDGame game;
    private final Button angelButton;
    private final Button demonButton;
    private ConfirmationGroup confirmationGroup;

    public FractionStage(final DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        this.game = game;

        angelButton = new Button(game.getSkin(), "angel");
        angelButton.setPosition(0, 0);
        angelButton.setVisible(true);
        angelButton.setSize(DDGame.WORLD_WIDTH / 2, DDGame.WORLD_HEIGHT);
        angelButton.setTouchable(Touchable.enabled);
        angelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player.PLAYER_FRACTION = DDGame.ANGEL;
                Player.SYSTEM_FRACTION = DDGame.DEMON;
                if (confirmationGroup == null) {
                    createDialog();
                }
                displayConfirmation();
            }
        });

        demonButton = new Button(game.getSkin(), "demon");
        demonButton.setPosition(DDGame.WORLD_WIDTH / 2, 0);
        demonButton.setVisible(true);
        demonButton.setSize(DDGame.WORLD_WIDTH / 2, DDGame.WORLD_HEIGHT);
        demonButton.setTouchable(Touchable.disabled);
        demonButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player.PLAYER_FRACTION = DDGame.DEMON;
                Player.SYSTEM_FRACTION = DDGame.ANGEL;
                if (confirmationGroup == null) {
                    createDialog();
                }
                displayConfirmation();
            }
        });

        fractions = new Group();
        fractions.addActor(angelButton);
        fractions.addActor(demonButton);

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

    public Button getAngelButton() {
        return angelButton;
    }

    public Button getDemonButton() {
        return demonButton;
    }

    public ConfirmationGroup getGroup() {
        return confirmationGroup;
    }

}
