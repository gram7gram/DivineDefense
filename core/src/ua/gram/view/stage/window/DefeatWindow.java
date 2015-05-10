package ua.gram.view.stage.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import ua.gram.DDGame;
import ua.gram.controller.listener.ContinueListener;
import ua.gram.controller.listener.RestartListener;
import ua.gram.model.Player;
import ua.gram.view.stage.GameUIStage;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatWindow extends Window {

    private final DDGame game;
    private final Button option2;
    private final Button option3;
    private final Button optionPurchase;

    public DefeatWindow(DDGame game, final GameUIStage stage_ui) {
        super("", game.getSkin(), "menu");
        this.game = game;
        this.setSize(650, 380);
        this.setPosition(DDGame.WORLD_WIDTH / 2f - this.getWidth() / 2f, DDGame.WORLD_HEIGHT / 2f - this.getHeight() / 2f);
        this.setVisible(true);
        this.setMovable(false);

        Label title1 = new Label("You were defeated...", game.getSkin(), "tusj64black");
        Label title2 = new Label("Get the revenge and be victorious:", game.getSkin(), "archery32black");
        Label header1 = new Label("Restart", game.getSkin(), "archery16black");
        Label header2 = new Label("Continue", game.getSkin(), "archery16black");
        Label header3 = new Label("Continue", game.getSkin(), "archery16black");

        Button option1 = new Button(game.getSkin(), "default");
        option1.setSize(160, 200);
        option1.addListener(new RestartListener(game, 1));

        option2 = new Button(game.getSkin(), "default");
        option2.setSize(160, 200);
        option2.addListener(new ContinueListener(game, 3, 2, this, stage_ui));
        option2.setDisabled(game.getPlayer().getGems() < 2);

        option3 = new Button(game.getSkin(), "default");
        option3.setSize(160, 200);
        option3.addListener(new ContinueListener(game, Player.DEFAULT_HEALTH, 3, this, stage_ui));
        option3.setDisabled(game.getPlayer().getGems() < 3);

        optionPurchase = new TextButton("BUY", game.getSkin(), "default");
        optionPurchase.setSize(100, 40);
//        option3.addListener(new MarketScreen);

        title1.setVisible(true);
        title2.setVisible(true);
        header1.setVisible(true);
        header2.setVisible(true);
        header3.setVisible(true);
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);

        this.pad(5);
        this.add(title1).expandX().fillX().colspan(3)
                .width(title1.getWidth())
                .height(title1.getHeight())
                .center().row();
        this.add(title2).expandX().fillX().colspan(3)
                .width(title2.getWidth())
                .height(title2.getHeight())
                .center().row();
        this.add(header1)
                .width(header1.getWidth())
                .height(header1.getHeight())
                .center().padTop(5).padBottom(5);
        this.add(header2)
                .width(header2.getWidth())
                .height(header2.getHeight())
                .center().padTop(5).padBottom(5);
        this.add(header3)
                .width(header3.getWidth())
                .height(header3.getHeight())
                .center().padTop(5).padBottom(5).row();
        this.add(option1)
                .width(option1.getWidth())
                .height(option1.getHeight())
                .center().right().padLeft(40);
        this.add(option2)
                .width(option2.getWidth())
                .height(option2.getHeight())
                .center().right().padLeft(40);
        this.add(option3)
                .width(option3.getWidth())
                .height(option3.getHeight())
                .center().right().padLeft(40).padRight(40);

        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is OK");
    }

    /**
     * Displays 'doors' to Market if Player has not enough gems to continue level.
     */
    public void update() {
        option2.setDisabled(game.getPlayer().getGems() < 2);
        option3.setDisabled(game.getPlayer().getGems() < 3);
        optionPurchase.setVisible(option2.isDisabled() && option3.isDisabled());
        if (optionPurchase.isVisible()) {
            Gdx.app.log("INFO", "All options are disabled. Displaying purchase button");
            this.row();
            this.add(optionPurchase)
                    .width(optionPurchase.getWidth())
                    .height(optionPurchase.getHeight())
                    .colspan(3)
                    .center().padTop(5)
                    .expandX();
        }
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is updated");
    }
}