package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;
import ua.gram.model.audio.AudioManager;
import ua.gram.model.prototype.AudioPreferencesPrototype;
import ua.gram.model.prototype.player.PlayerPreferences;
import ua.gram.model.ui.AudioSlider;
import ua.gram.utils.Log;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SettingsWindow extends WindowGroup {

    public SettingsWindow(final DDGame game) {
        super(game.getResources().getSkin());
        Skin skin = game.getResources().getSkin();

        Table nested = new Table();
        nested.setVisible(true);
        nested.setFillParent(true);

        PlayerPreferences preferences = game.getPlayer().getPreferences();
        final AudioPreferencesPrototype audioPreferences = preferences.audio;
        final AudioManager audioManager = game.getAudioManager();

        final Slider soundSlider = new AudioSlider(skin, "default", audioPreferences.sound);
        final Slider musicSlider = new AudioSlider(skin, "default", audioPreferences.music);
        final CheckBox soundBox = new CheckBox("", skin, "default");
        final CheckBox musicBox = new CheckBox("", skin, "default");

        soundSlider.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                audioManager.setSoundVolume(soundSlider.getValue() / 100);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                soundBox.setChecked(audioManager.canPlaySound());
                Log.info("New sound volume: " + audioPreferences.sound.volume);
            }
        });

        musicSlider.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                audioManager.setMusicVolume(musicSlider.getValue() / 100);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                musicBox.setChecked(audioManager.canPlayMusic());
                Log.info("New music volume: " + audioPreferences.music.volume);
            }
        });

        soundBox.setChecked(audioPreferences.sound.state);
        soundBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                AudioManager audioManager = game.getAudioManager();
                boolean state = audioManager.toggleSound();
                Log.info("Sound is " + (state ? "enabled" : "disabled"));
                soundSlider.setDisabled(!state);
            }
        });

        musicBox.setChecked(audioPreferences.music.state);
        musicBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                AudioManager audioManager = game.getAudioManager();
                boolean state = audioManager.toggleMusic();
                Log.info("Music is " + (state ? "enabled" : "disabled"));
                musicSlider.setDisabled(!state);
            }
        });

        Table soundTable = new Table();

        soundTable.add(new Label("SOUND", skin, "header2altwhite"))
                .expandX();
        soundTable.add(soundBox)
                .expandX()
                .row();

        soundTable.add().padTop(20).row();

        soundTable.add(new Label("SOUND VOLUME", skin, "header2altwhite"))
                .expandX()
                .colspan(2)
                .row();
        soundTable.add(soundSlider)
                .width(getWidth() * .4f)
                .pad(10, 0, 10, 0)
                .colspan(2)
                .row();

        Table musicTable = new Table();

        musicTable.add(new Label("MUSIC", skin, "header2altwhite"))
                .expandX();
        musicTable.add(musicBox)
                .expandX()
                .row();

        musicTable.add().padTop(20).row();

        musicTable.add(new Label("MUSIC VOLUME", skin, "header2altwhite"))
                .colspan(2)
                .expandX()
                .row();
        musicTable.add(musicSlider)
                .width(getWidth() * .4f)
                .pad(10, 0, 10, 0)
                .colspan(2);

        nested.add(soundTable).align(Align.center).width(getWidth() / 2 - 40);
        nested.add(musicTable).align(Align.center).width(getWidth() / 2 - 40);

        Button back = new Button(skin, "right-small");
        back.setVisible(true);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen(game));
            }
        });

        setActionRight(back);
        setTitle(new Label("SETTINGS", skin, "header2altwhite"));
        getContent().add(nested).fill().expandX();
    }
}
