package ua.gram.model.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import ua.gram.DDGame;
import ua.gram.model.prototype.AudioPreferencesPrototype;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AudioManager {

    private final Resources resources;
    private final AudioPreferencesPrototype prototype;

    public AudioManager(DDGame game) {
        resources = game.getResources();
        prototype = game.getPlayerPreferences().audio;
    }

    public void loopMusic(String s) {
        if (!canPlayMusic()) return;

        playMusic(s, true);
    }

    public void playMusic(String s) {
        if (!canPlayMusic()) return;

        playMusic(s, false);
    }

    public void playMusic(String s, boolean loop) {
        if (!canPlayMusic()) return;

        Music music = resources.getRegisteredMusic(s);
        music.setLooping(loop);
        music.setVolume(prototype.music.volume);
        music.play();
    }

    public void loopSound(String s) {
        if (!canPlaySound()) return;

        playSound(s, true);
    }

    public void playSound(String s) {
        if (!canPlaySound()) return;

        playSound(s, false);
    }

    public void playSound(String s, boolean loop) {
        if (!canPlaySound()) return;

        Sound sound = resources.getRegisteredSound(s);
        long id = sound.play();
        sound.setLooping(id, loop);
        sound.setVolume(id, prototype.sound.volume);
    }

    public void stopMusic(String s) {
        if (!canPlayMusic()) return;

        Music music = resources.getRegisteredMusic(s);
        music.stop();
    }

    public void stopSound(String s) {
        if (!canPlaySound()) return;

        Sound sound = resources.getRegisteredSound(s);
        sound.stop();
    }

    public void pauseMusic(String s) {
        if (!canPlayMusic()) return;

        Music music = resources.getRegisteredMusic(s);
        music.pause();
    }

    public void pauseSound(String s) {
        if (!canPlaySound()) return;

        Sound sound = resources.getRegisteredSound(s);
        sound.pause();
    }

    private boolean canPlayMusic() {
        return prototype.music.state;
    }

    private boolean canPlaySound() {
        return prototype.sound.state;
    }

    public boolean toggleSound() {
        prototype.sound.state = !prototype.sound.state;

        return prototype.sound.state;
    }

    public boolean toggleMusic() {
        prototype.music.state = !prototype.music.state;

        return prototype.music.state;
    }

    public void setMusicVolume(float volume) {
        if (!isValidVolume(volume)) {
            throw new IllegalArgumentException("Invalid music volume: " + volume);
        }
        prototype.music.volume = volume;
    }

    public void setSoundVolume(float volume) {
        if (!isValidVolume(volume)) {
            throw new IllegalArgumentException("Invalid sound volume: " + volume);
        }
        prototype.sound.volume = volume;
    }

    public boolean isValidVolume(float number) {
        number = (int) number;
        return number == 0 || number == 1;
    }
}
