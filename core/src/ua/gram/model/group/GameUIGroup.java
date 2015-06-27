package ua.gram.model.group;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.model.Level;
import ua.gram.model.actor.misc.CounterButton;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameUIGroup extends Group {

    private final DDGame game;
    private final Level level;
    private final Label healthLabel;
    private final Label moneyLabel;
    private final Label waveLabel;
    private final Label gemsLabel;
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
                game.setGameSpeed(2);
            }
        });

        Vector2 pos = level.getMap().getSpawn().getPosition();
        Vector2 dir = level.getMap().getPath().getPath().get(0);

        counter = new CounterButton(game, level, new Vector2(pos.x + dir.x, pos.y + dir.y));

        healthLabel = new Label("HEALTH: " + game.getPlayer().getHealth(), skin, "small_tinted");
        gemsLabel = new Label("GEMS: " + game.getPlayer().getGems(), skin, "small_tinted");
        moneyLabel = new Label("MONEY: " + game.getPlayer().getCoins(), skin, "small_tinted");

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

        waveLabel = new Label("WAVE: "
                + (level.getCurrentWave() <= 0 ? "-" : level.getCurrentWave())
                + "/" + level.getMaxWaves(),
                skin, "small_tinted");
        waveLabel.setVisible(true);
        waveLabel.setPosition(
                DDGame.WORLD_WIDTH / 2f + gap + healthLabel.getWidth() + gap,
                DDGame.WORLD_HEIGHT - waveLabel.getHeight() - gap
        );

        this.addActor(pauseBut);
        this.addActor(speedBut);
        this.addActor(counter);
        this.addActor(gemsLabel);
        this.addActor(moneyLabel);
        this.addActor(healthLabel);
        this.addActor(waveLabel);
    }

    /**
     * Updates UI components.
     *
     * @param delta
     */
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

    public void updateHealthLabel() {
        healthLabel.setText("HEALTH: " + game.getPlayer().getHealth());
    }

    public void updateGemsLabel() {
        gemsLabel.setText("GEMS: " + game.getPlayer().getGems());
    }

    public void updateWaveLabel() {
        waveLabel.setText("WAVE: " + (level.getCurrentWave() <= 0 ? "-" : level.getCurrentWave()) + "/" + level.getMaxWaves());
    }

    public void updateMoneyLabel() {
        moneyLabel.setText("MONEY: " + game.getPlayer().getCoins());
    }

    public CounterButton getCounterBut() {
        return counter;
    }

}
