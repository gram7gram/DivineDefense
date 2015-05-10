package ua.gram.view.stage.group;

import com.badlogic.gdx.Gdx;
import ua.gram.view.AbstractGroup;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.Level;
import ua.gram.model.Wave;
import ua.gram.view.stage.GameUIStage;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameUIGroup extends AbstractGroup {

    private final DDGame game;
    private final Level level;
    private final Label healthLabel;
    private final Label moneyLabel;
    private final Label waveLabel;
    private final Label gemsLabel;
    private TextButton counterBut;

    public GameUIGroup(final DDGame game, final GameUIStage stage_ui, final Level level) {
        super();
        this.game = game;
        this.level = level;

        Button menu = new TextButton("S", game.getSkin(), "default");
        menu.setPosition(DDGame.WORLD_WIDTH - butHeight - gap, DDGame.WORLD_HEIGHT - butHeight - gap);
        menu.setSize(butHeight, butHeight);
        menu.setVisible(true);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage_ui.toggleWindow(stage_ui.getPauseWindow());
            }
        });

        Button speedBut = new TextButton("F", game.getSkin(), "default");
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

        counterBut = new TextButton("" + (int) (level.getWave().getCountdown()), game.getSkin(), "default");
        counterBut.setPosition((pos.x + dir.x) * DDGame.TILEHEIGHT + gap, (pos.y + dir.y) * DDGame.TILEHEIGHT + gap);
        counterBut.setSize(butHeight / 2, butHeight / 2);
        counterBut.setVisible(false);
        counterBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("INFO", "Countdown interrupted");
                if (level.getWave().getCountdown() > 0) {
                    int reward = Integer.parseInt(counterBut.getText().toString());
                    assert reward >= 0 && reward <= Wave.countdown : "Out of bounds reward: " + reward + " coins";
                    Gdx.app.log("INFO", "Player recieves " + reward + " coins as reward");
                    game.getPlayer().addCoins(reward);
                    //TODO Display profit Label
                }
                level.getWave().nextWave();
                counterBut.setVisible(false);
            }
        });

        healthLabel = new Label("HEALTH: " + game.getPlayer().getHealth(), game.getSkin(), "small_tinted");
        gemsLabel = new Label("GEMS: " + game.getPlayer().getGems(), game.getSkin(), "small_tinted");
        moneyLabel = new Label("MONEY: " + game.getPlayer().getCoins(), game.getSkin(), "small_tinted");

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
                game.getSkin(), "small_tinted");
        waveLabel.setVisible(true);
        waveLabel.setPosition(
                DDGame.WORLD_WIDTH / 2f + gap + healthLabel.getWidth() + gap,
                DDGame.WORLD_HEIGHT - waveLabel.getHeight() - gap
        );

        this.addActor(menu);
        this.addActor(speedBut);
        this.addActor(counterBut);
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
        super.act(delta);
        if (!DDGame.PAUSE && !(level.isCleared || game.getPlayer().isDead())) {
            updateHealthLabel();
            updateMoneyLabel();
            updateGemsLabel();
            updateWaveLabel();
            updateCounter();
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

    public void updateCounter() {
        if (!level.getWave().isStarted && level.getCurrentWave() <= level.getMaxWaves()) {
            if (!counterBut.isVisible()) {
                counterBut.setVisible(true);
                Gdx.app.log("INFO", "Countdown started");
            }
            updateCounterButton();
        }
    }

    public void updateCounterButton() {
        int i = (int) level.getWave().getCountdown();
        counterBut.setText(i + "");
        if (i <= 0) {
            Gdx.app.log("INFO", "Countdown finished");
            level.getWave().nextWave();
            counterBut.setVisible(false);
        }
    }

    public TextButton getCounterBut() {
        return counterBut;
    }

}
