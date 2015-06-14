package ua.gram.model.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import ua.gram.DDGame;
import ua.gram.controller.listener.ContinueListener;
import ua.gram.controller.listener.RestartListener;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.model.Player;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatWindow extends Window {

    private DDGame game;
    private Button option2;
    private Button option3;
    private Button optionPurchase;

    public DefeatWindow(DDGame game, final GameUIStage stage_ui) {
        super("", game.getResources().getSkin(), "default");
        this.game = game;
        Skin skin = game.getResources().getSkin();
        int but_height = 200;

        this.setSize(680, 400);
        this.setPosition(DDGame.WORLD_WIDTH / 2f - this.getWidth() / 2f, DDGame.WORLD_HEIGHT / 2f - this.getHeight() / 2f);
        this.setVisible(true);
        this.setMovable(false);

        Label title1 = new Label("You were defeated...", skin, "tusj64black");
        Label title2 = new Label("Get the revenge and be victorious:", skin, "archery32black");
        Label header1 = new Label("Restart", skin, "archery16black");
        Label header2 = new Label("Continue", skin, "archery16black");
        Label header3 = new Label("Continue", skin, "archery16black");

        Button option1 = new Button(skin, "default");
        option1.setSize(but_height, but_height);
        option1.addListener(new RestartListener(game, 1));

        option2 = new Button(skin, "default");
        option2.setSize(but_height, but_height);
        option2.addListener(new ContinueListener(game, 3, 2, this, stage_ui));
        option2.setDisabled(game.getPlayer().getGems() < 2);

        option3 = new Button(skin, "default");
        option3.setSize(but_height, but_height);
        option3.addListener(new ContinueListener(game, Player.DEFAULT_HEALTH, 3, this, stage_ui));
        option3.setDisabled(game.getPlayer().getGems() < 3);

        optionPurchase = new TextButton("BUY", skin, "default");
        optionPurchase.setSize(100, 40);
//        optionPurchase.addListener(new MarketScreen);

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
                .center();
        this.add(option2)
                .width(option2.getWidth())
                .height(option2.getHeight())
                .center();
        this.add(option3)
                .width(option3.getWidth())
                .height(option3.getHeight())
                .center();

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
            Gdx.app.log("INFO", "Displaying purchase button");
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