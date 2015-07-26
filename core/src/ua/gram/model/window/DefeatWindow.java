package ua.gram.model.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatWindow extends Window {

    private DDGame game;
    private Button option2;
    private Button option3;
    private Button optionPurchase;

    public DefeatWindow(DDGame game, final Stage stage_ui) {
        super("", game.getResources().getSkin(), "window-pretty-header");
        this.game = game;
        Skin skin = game.getResources().getSkin();
        int but_height = 200;

        this.setSize(760, 520);
        this.setPosition(DDGame.WORLD_WIDTH / 2f - this.getWidth() / 2f, DDGame.WORLD_HEIGHT / 2f - this.getHeight() / 2f);
        this.setVisible(true);
        this.setMovable(false);

        Label title1 = new Label("You were defeated...", skin, "header1altblack");
        Label title2 = new Label("Get the revenge and be victorious :", skin, "header2black");

        Button option1 = new Button(skin, "button-defeat-restart");
        option1.setSize(200, 335);
//        option1.addListener(new RestartListener(game, game.getPlayer().getLevel()));

        option2 = new Button(skin, "button-defeat-continue-part");
        option2.setSize(200, 335);
//        option2.addListener(new ContinueListener(game, 3, 2, this, stage_ui));
        option2.setDisabled(game.getPlayer().getGems() < 2);

        option3 = new Button(skin, "button-defeat-continue-full");
        option3.setSize(200, 335);
//        option3.addListener(new ContinueListener(game, Player.DEFAULT_HEALTH, 3, this, stage_ui));
        option3.setDisabled(game.getPlayer().getGems() < 3);

        optionPurchase = new Button(skin, "button-goto-shop");
        optionPurchase.setSize(645, 55);//x57.5,y55
//        optionPurchase.addListener(new MarketScreen);

        title1.setVisible(true);
        title2.setVisible(true);
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);

        //TODO Configure Defeat window
        this.padLeft(40).padRight(40).padTop(15).padBottom(15);
        this.add(title1).expandX().fillX().colspan(3).padTop(5)
                .width(title1.getWidth())
                .height(title1.getHeight())
                .center().row();
        this.add(title2).expandX().fillX().colspan(3).padTop(10).padBottom(10)
                .width(title2.getWidth())
                .height(title2.getHeight())
                .center().row();
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
                .center().row();
        this.add(optionPurchase)
                .width(optionPurchase.getWidth())
                .height(optionPurchase.getHeight())
                .colspan(3)
                .center().padTop(10)
                .expandX();
        update();
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is OK");
    }

    /**
     * Displays 'doors' to Market if Player has not enough gems to continue level.
     */
    public void update() {
        option2.setDisabled(game.getPlayer().getGems() < 2);
        option3.setDisabled(game.getPlayer().getGems() < 3);
        optionPurchase.setVisible(option2.isDisabled() && option3.isDisabled());
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is updated");
    }
}