package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.controller.Toggler;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.model.Level;
import ua.gram.model.actor.misc.CounterButton;
import ua.gram.model.actor.misc.CustomLabel;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameUIGroup extends Group {

    private final DDGame game;
    private final Level level;
    private final CustomLabel healthLabel;
    private final CustomLabel moneyLabel;
    private final CustomLabel waveLabel;
    private final CustomLabel gemsLabel;
    private final CustomLabel notificationLabel;
    private final Toggler toggler;
    private CounterButton counter;

    public GameUIGroup(final DDGame game, final GameUIStage stage_ui, final Level level) {
        super();
        this.game = game;
        this.level = level;
        byte gap = 5;
        int butHeight = DDGame.DEFAULT_BUTTON_HEIGHT;
        Skin skin = game.getResources().getSkin();

        Button pauseBut = new Button(skin, "pause-button");
        pauseBut.setPosition(DDGame.WORLD_WIDTH - butHeight - gap, DDGame.WORLD_HEIGHT - butHeight - gap);
        pauseBut.setSize(butHeight, butHeight);
        pauseBut.setVisible(true);
        pauseBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DDGame.PAUSE = true;
                stage_ui.toggleWindow(stage_ui.getPauseWindow());
            }
        });

        Button speedBut = new Button(skin, "speed-button");
        speedBut.setPosition(gap, DDGame.WORLD_HEIGHT - butHeight - gap);
        speedBut.setSize(butHeight, butHeight);
        speedBut.setVisible(true);
        speedBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setGameSpeed(game.getGameSpeed() > 1 ? 1 : 1.5f);
            }
        });
        Vector2 pos = level.getMap().getSpawn().getPosition();

        counter = new CounterButton(game, level, new Vector2(pos.x, pos.y));

        healthLabel = new CustomLabel("HEALTH: " + game.getPlayer().getHealth(), skin, "small_tinted");
        gemsLabel = new CustomLabel("GEMS: " + game.getPlayer().getGems(), skin, "small_tinted");
        moneyLabel = new CustomLabel("MONEY: " + game.getPlayer().getCoins(), skin, "small_tinted");

        moneyLabel.setVisible(true);
        moneyLabel.setPosition(
                DDGame.WORLD_WIDTH / 2f - gap - gemsLabel.getWidth() - gap - moneyLabel.getWidth() - gap,
                DDGame.WORLD_HEIGHT - moneyLabel.getHeight() - gap
        );

        gemsLabel.setVisible(true);
        gemsLabel.setPosition(
                DDGame.WORLD_WIDTH / 2f - gap - gemsLabel.getWidth(),
                DDGame.WORLD_HEIGHT - gemsLabel.getHeight() - gap
        );

        healthLabel.setVisible(true);
        healthLabel.setPosition(
                DDGame.WORLD_WIDTH / 2f + gap,
                DDGame.WORLD_HEIGHT - healthLabel.getHeight() - gap
        );

        waveLabel = new CustomLabel("WAVE: "
                + (level.getCurrentWave() <= 0 ? "-" : level.getCurrentWave())
                + "/" + level.getMaxWaves(),
                skin, "small_tinted");
        waveLabel.setVisible(true);
        waveLabel.setPosition(
                DDGame.WORLD_WIDTH / 2f + gap + healthLabel.getWidth() + gap,
                DDGame.WORLD_HEIGHT - waveLabel.getHeight() - gap
        );

        notificationLabel = new CustomLabel("", skin, "header1white");
        notificationLabel.setVisible(false);


        toggler = new Toggler(notificationLabel);

        Group labels = new Group();
        labels.addActor(gemsLabel);
        labels.addActor(moneyLabel);
        labels.addActor(healthLabel);
        labels.addActor(waveLabel);
        labels.addAction(
                Actions.sequence(
                        Actions.parallel(
                                Actions.alpha(0),
                                Actions.moveBy(0, gemsLabel.getHeight())
                        ),
                        Actions.delay(.5f),
                        Actions.parallel(
                                Actions.alpha(1, .2f),
                                Actions.moveBy(0, -gemsLabel.getHeight(), .2f)
                        )
                )
        );

        this.addActor(pauseBut);
        this.addActor(speedBut);
        this.addActor(notificationLabel);
        this.addActor(counter);
        this.addActor(labels);
    }

    @Override
    public void act(float delta) {
        if (!DDGame.PAUSE && !(level.isCleared | game.getPlayer().isDead())) {
            super.act(delta);
            updateHealthLabel();
            updateMoneyLabel();
            updateGemsLabel();
            updateWaveLabel();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (DDGame.DEBUG) {
            game.getInfo().draw(batch,
                    "FPS: " + Gdx.graphics.getFramesPerSecond(),
                    10, DDGame.WORLD_HEIGHT - 12);
        }
    }

    public void showNotification(String message) {
        notificationLabel.updateText(message);
        notificationLabel.setPosition(
                (DDGame.WORLD_WIDTH - notificationLabel.getWidth()) / 2f,
                (DDGame.WORLD_HEIGHT - notificationLabel.getHeight()) / 2f);
        notificationLabel.addAction(
                Actions.sequence(
                        Actions.run(toggler),
                        Actions.alpha(0),
                        Actions.alpha(1, .6f),
                        Actions.delay(1),
                        Actions.alpha(0, .6f),
                        Actions.run(toggler)
                ));
        Gdx.app.log("INFO", "Showed notification: " + message);
    }

    public void updateHealthLabel() {
        healthLabel.updateText("HEALTH: " + game.getPlayer().getHealth());
    }

    public void updateGemsLabel() {
        gemsLabel.updateText("GEMS: " + game.getPlayer().getGems());
    }

    public void updateWaveLabel() {
        waveLabel.updateText("WAVE: " + (level.getCurrentWave() <= 0 ? "-" : level.getCurrentWave()) + "/" + level.getMaxWaves());
    }

    public void updateMoneyLabel() {
        moneyLabel.updateText("MONEY: " + game.getPlayer().getCoins());
    }

    public CounterButton getCounterBut() {
        return counter;
    }

}
