package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.listener.ContinueListener;
import ua.gram.controller.listener.RestartClickListener;
import ua.gram.model.Initializer;
import ua.gram.model.Player;
import ua.gram.model.stage.StageHolder;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatWindow extends Window implements Initializer {

    private final DDGame game;
    private final Button option2;
    private final Button option3;
    private StageHolder stageHolder;

    public DefeatWindow(final DDGame game) {
        super("", game.getResources().getSkin(), "window-pretty-header");
        this.game = game;
        Skin skin = game.getResources().getSkin();

        this.setSize(760, 520);
        this.setPosition(DDGame.WORLD_WIDTH / 2f - this.getWidth() / 2f, DDGame.WORLD_HEIGHT / 2f - this.getHeight() / 2f);
        this.setVisible(true);
        this.setMovable(false);

        Label title1 = new Label("You were defeated...", skin, "header1altblack");
        Label title2 = new Label("Get the revenge and be victorious :", skin, "header2black");

        Button option1 = new Button(skin, "button-defeat-restart");
        option1.setSize(200, 335);
        option1.addListener(new RestartClickListener(game, game.getPlayer().getLevel()));

        option2 = new Button(skin, "button-defeat-continue-part");
        option2.setSize(200, 335);

        option3 = new Button(skin, "button-defeat-continue-full");
        option3.setSize(200, 335);

        title1.setVisible(true);
        title2.setVisible(true);
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);

        padLeft(40).padRight(40).padTop(15).padBottom(15);
        add(title1).expandX().fillX().colspan(3).padTop(5)
                .width(title1.getWidth())
                .height(title1.getHeight())
                .center().row();
        add(title2).expandX().fillX().colspan(3).padTop(10).padBottom(10)
                .width(title2.getWidth())
                .height(title2.getHeight())
                .center().row();
        add(option1)
                .width(option1.getWidth())
                .height(option1.getHeight())
                .center();
        add(option2)
                .width(option2.getWidth())
                .height(option2.getHeight())
                .center();
        add(option3)
                .width(option3.getWidth())
                .height(option3.getHeight())
                .center().row();
        update();
        addAction(
                Actions.parallel(
                        Actions.alpha(0),
                        Actions.alpha(1, .8f)
                )
        );
        Log.info(this.getClass().getSimpleName() + " is OK");
    }

    public void update() {
        option2.setDisabled(game.getPlayer().getGems() < 2);
        option3.setDisabled(game.getPlayer().getGems() < 3);
        Log.info(this.getClass().getSimpleName() + " is updated");
    }

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    @Override
    public void init() {
        if (stageHolder == null) throw new NullPointerException("StageHolder is not initialized");
        option2.addListener(new ContinueListener(game, 3, 2, this, stageHolder.getUiStage()));
        option3.addListener(new ContinueListener(game, Player.DEFAULT_HEALTH, 3, this, stageHolder.getUiStage()));
    }
}