package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.view.screen.LevelSelectScreen;

/**
 * Created by Gram on 9/1.
 */
public class MainMenuStage extends Stage {

    public MainMenuStage(final DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        int width = DDGame.WORLD_WIDTH;
        int height = DDGame.WORLD_HEIGHT;
        byte butHeight = 80;
        short butWidth = 300;
        byte gap = 5;

        Button quitBut = new TextButton("Q", game.getSkin(), "default");
        quitBut.setSize(butHeight * .75f, butHeight * .75f);
        quitBut.setPosition(width - quitBut.getWidth() - gap, height - quitBut.getHeight() - gap);
        quitBut.setVisible(true);
        quitBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        Button continueBut = new TextButton("CONTINUE", game.getSkin(), "main-menu1");
        continueBut.setPosition(width / 4f - butWidth / 2f, height / 2f + butHeight / 2f + gap);
        continueBut.setSize(butWidth, butHeight);
        continueBut.setVisible(true);
        continueBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        Button aboutBut = new TextButton("ABOUT", game.getSkin(), "main-menu2");
        aboutBut.setPosition(width / 4f - butWidth / 2f, height / 2f - butHeight / 2f);
        aboutBut.setSize(butWidth, butHeight);
        aboutBut.setVisible(true);
        aboutBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO Create AboutScreen
//                game.setScreen(new AboutScreen(game));
            }
        });
        Button settingsBut = new TextButton("SETTINGS", game.getSkin(), "main-menu3");
        settingsBut.setPosition(width / 4f - butWidth / 2f, height / 2f - butHeight * 3 / 2f - gap);
        settingsBut.setSize(butWidth, butHeight);
        settingsBut.setVisible(true);
        settingsBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO Create SettingsScreen
//                game.setScreen(new SettingsScreen(game));
            }
        });

        this.addActor(quitBut);
        this.addActor(continueBut);
        this.addActor(aboutBut);
        this.addActor(settingsBut);
    }

}
