package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;
import ua.gram.controller.stage.StageHolder;
import ua.gram.controller.stage.UIStage;
import ua.gram.model.Initializer;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.level.Level;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameUIGroup extends Table implements Initializer {

    private final DDGame game;
    private final Level level;
    private final CustomLabel healthLabel;
    private final CustomLabel moneyLabel;
    private final CustomLabel waveLabel;
    private final CustomLabel gemsLabel;
    private final CustomLabel notificationLabel;
    private final Button pauseBut;
    private StageHolder stageHolder;

    public GameUIGroup(final DDGame game, final Level level) {
        super();
        this.game = game;
        this.level = level;

        byte gap = 5;
        int butHeight = DDGame.DEFAULT_BUTTON_HEIGHT;
        Skin skin = game.getResources().getSkin();

        pauseBut = new Button(skin, "pause-big");
//        pauseBut.setPosition(DDGame.WORLD_WIDTH - butHeight - gap, DDGame.WORLD_HEIGHT - butHeight - gap);
//        pauseBut.setSize(butHeight, butHeight);
        pauseBut.setVisible(true);

        healthLabel = new CustomLabel("HEALTH: " + game.getPlayer().getHealth(), skin, "small_tinted");
        gemsLabel = new CustomLabel("GEMS: " + game.getPlayer().getGems(), skin, "small_tinted");
        moneyLabel = new CustomLabel("MONEY: " + game.getPlayer().getCoins(), skin, "small_tinted");

        moneyLabel.setVisible(true);
//        moneyLabel.setPosition(
//                DDGame.WORLD_WIDTH / 2f - gap - gemsLabel.getWidth() - gap - moneyLabel.getWidth() - gap,
//                DDGame.WORLD_HEIGHT - moneyLabel.getHeight() - gap
//        );

        gemsLabel.setVisible(true);
//        gemsLabel.setPosition(
//                DDGame.WORLD_WIDTH / 2f - gap - gemsLabel.getWidth(),
//                DDGame.WORLD_HEIGHT - gemsLabel.getHeight() - gap
//        );

        healthLabel.setVisible(true);
//        healthLabel.setPosition(
//                DDGame.WORLD_WIDTH / 2f + gap,
//                DDGame.WORLD_HEIGHT - healthLabel.getHeight() - gap
//        );

        waveLabel = new CustomLabel("", skin, "small_tinted");
        waveLabel.setVisible(false);
//        waveLabel.setPosition(
//                DDGame.WORLD_WIDTH / 2f + gap + healthLabel.getWidth() + gap,
//                DDGame.WORLD_HEIGHT - waveLabel.getHeight() - gap
//        );

        //NOTE Workaround about 0 width of the notification label at first launch
        //notificationLabel = new CustomLabel("", skin, "header1white");
        notificationLabel = new CustomLabel("LEVEL " + level.getIndex(), skin, "header1white");
        notificationLabel.setVisible(false);

        Table nested = new Table();
        nested.add(gemsLabel).pad(5);
        nested.add(moneyLabel).pad(5);
        nested.add(healthLabel).pad(5);
        nested.add(waveLabel).pad(5);
        nested.addAction(
                Actions.sequence(
                        Actions.parallel(
                                Actions.alpha(0)
//                                Actions.moveBy(0, gemsLabel.getPrefHeight())
                        ),
                        Actions.delay(.5f),
                        Actions.parallel(
                                Actions.alpha(1, .2f)
//                                Actions.moveBy(0, -gemsLabel.getPrefHeight(), .2f)
                        )
                )
        );

        add().pad(5)
                .size(DDGame.DEFAULT_BUTTON_HEIGHT);
        add(nested).expandX()
                .height(DDGame.DEFAULT_BUTTON_HEIGHT)
                .align(Align.top);
        add(pauseBut).pad(5)
                .size(DDGame.DEFAULT_BUTTON_HEIGHT).row();

        setFillParent(true);
    }

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    @Override
    public void init() {
        if (stageHolder == null) throw new NullPointerException("Missing UIStage");
        pauseBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                notificationLabel.remove();
                DDGame.PAUSE = true;
                UIStage uiStage = stageHolder.getUiStage();
                uiStage.toggleWindow(uiStage.getPauseWindow());
            }
        });
    }

    @Override
    public void act(float delta) {
        if (canBeUpdated()) {
            super.act(delta);
            updateHealthLabel();
            updateMoneyLabel();
            updateGemsLabel();
            updateWaveLabel();
        }
    }

    private boolean canBeUpdated() {
        return !DDGame.PAUSE && !(level.isCleared | game.getPlayer().isDead());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        setDebug(DDGame.DEBUG, true);
        if (DDGame.DEBUG) {
            game.getInfo().draw(batch,
                    "FPS: " + Gdx.graphics.getFramesPerSecond(),
                    10, DDGame.WORLD_HEIGHT - 12);
        }
    }

    public void showNotification(String message) {
        notificationLabel.remove();
        notificationLabel.clearActions();
        notificationLabel.updateText(message);
        float width = notificationLabel.getPrefWidth();
        float height = notificationLabel.getPrefHeight();
        notificationLabel.setPosition(
                (DDGame.WORLD_WIDTH - width) / 2f,
                (DDGame.WORLD_HEIGHT - height) / 2f);
        notificationLabel.setVisible(true);
        notificationLabel.addAction(
                Actions.sequence(
                        Actions.alpha(0),
                        Actions.alpha(1, .6f),
                        Actions.delay(1),
                        Actions.alpha(0, .6f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                notificationLabel.remove();
                            }
                        })
                ));
        getStage().addActor(notificationLabel);
        notificationLabel.toFront();
        Log.info("Showed notification: " + message);
    }

    public void updateHealthLabel() {
        healthLabel.updateText("HEALTH: " + game.getPlayer().getHealth());
    }

    public void updateGemsLabel() {
        gemsLabel.updateText("GEMS: " + game.getPlayer().getGems());
    }

    public void updateWaveLabel() {
        int index = level.getCurrentWaveIndex();
        waveLabel.setVisible(index > 0);
        waveLabel.updateText("WAVE: " + (index > 0 ? index : "-") + "/" + level.getMaxWaves());
    }

    public void updateMoneyLabel() {
        moneyLabel.updateText("MONEY: " + game.getPlayer().getCoins());
    }

}
