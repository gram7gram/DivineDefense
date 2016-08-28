package ua.gram.model.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ua.gram.DDGame;
import ua.gram.model.prototype.AudioPreferencesPrototype;
import ua.gram.model.prototype.SoundStatePrototype;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AudioManager {

    public static final String BACKGROUND_GLOBAL_MUSIC_KEY = "BACKGROUND_GLOBAL";
    private final DDGame game;
    private final Resources resources;
    private final AudioPreferencesPrototype prototype;
    private final Map<String, Long> activeSounds;
    private final Set<String> activeMusic;

    public AudioManager(DDGame game) {
        this.game = game;
        resources = game.getResources();
        prototype = game.getPlayerPreferences().audio;
        activeSounds = new HashMap<>();
        activeMusic = new TreeSet<>();
    }

    public void startGlobalBackgroundMusic() {
        SoundStatePrototype prototype = game.getPrototype().ui
                .getMusicByState(BACKGROUND_GLOBAL_MUSIC_KEY);
        loopMusic(prototype.sound);
    }

    public void loopMusic(String s) {
        playMusic(s, true);
    }

    public void loopSound(String s) {
        playSound(s, true);
    }

    public void playMusic(String s) {
        playMusic(s, false);
    }

    public void playSound(String s) {
        playSound(s, false);
    }

    public void playMusic(String s, boolean loop) {
        if (!canPlayMusic()) return;

        Music music = resources.getRegisteredMusic(s);
        if (music.isPlaying()) return;

        music.setLooping(loop);
        music.setVolume(prototype.music.volume);
        music.play();

        synchronized (activeMusic) {
            if (!activeMusic.contains(s)) {
                activeMusic.add(s);
            }
        }
    }

    public void playSound(String s, boolean loop) {
        if (!canPlaySound()) return;

        Sound sound = resources.getRegisteredSound(s);
        long id = sound.play();
        sound.setLooping(id, loop);
        sound.setVolume(id, prototype.sound.volume);

        synchronized (activeSounds) {
            if (!activeSounds.containsKey(s)) {
                activeSounds.put(s, id);
            }
        }
    }

    public void stopMusic(String s) {
        if (!canPlayMusic()) return;

        Music music = resources.getRegisteredMusic(s);
        music.stop();
    }

    public void stopSound(String s) {
        if (!canPlaySound()) return;

        long id = activeSounds.get(s);
        Sound sound = resources.getRegisteredSound(s);
        sound.stop(id);
    }

    public void pauseMusic(String s) {
        if (!canPlayMusic()) return;

        if (!activeMusic.contains(s)) {
            throw new NullPointerException("Music \"" + "\" was not found in registry");
        }

        Music music = resources.getRegisteredMusic(s);
        music.pause();

        synchronized (activeMusic) {
            activeMusic.remove(s);
        }
    }

    public void pauseSound(String s) {
        if (!canPlaySound()) return;

        if (!activeSounds.containsKey(s)) {
            throw new NullPointerException("Sound \"" + "\" was not found in registry");
        }

        long id = activeSounds.get(s);

        Sound sound = resources.getRegisteredSound(s);
        sound.pause(id);

        synchronized (activeSounds) {
            activeSounds.remove(s);
        }
    }

    public boolean canPlayMusic() {
        return prototype.music.state;
    }

    public boolean canPlaySound() {
        return prototype.sound.state;
    }

    public boolean toggleSound(boolean state) {
        prototype.sound.state = state;

        soundStateChanged();

        return prototype.sound.state;
    }

    public boolean toggleMusic(boolean state) {
        prototype.music.state = state;

        musicStateChanged();

        return prototype.music.state;
    }

    public boolean toggleMusic() {
        return toggleMusic(!prototype.music.state);
    }

    public boolean enableMusic() {
        return toggleMusic(true);
    }

    public boolean disableMusic() {
        return toggleMusic(false);
    }

    public boolean toggleSound() {
        return toggleSound(!prototype.sound.state);
    }

    public boolean enableSound() {
        return toggleSound(true);
    }

    public boolean disableSound() {
        return toggleSound(false);
    }

    public void setMusicVolume(float volume) {
        if (!isValidVolume(volume)) {
            throw new IllegalArgumentException("Invalid music volume: " + volume);
        }

        if (volume != prototype.music.volume) {
            prototype.music.volume = volume;
            musicVolumeChanged();
        }
    }

    public void setSoundVolume(float volume) {
        if (!isValidVolume(volume)) {
            throw new IllegalArgumentException("Invalid sound volume: " + volume);
        }

        if (volume != prototype.sound.volume) {
            prototype.sound.volume = volume;
            soundVolumeChanged();
        }
    }

    protected boolean isValidVolume(float number) {
        number = (int) number;
        return number == 0 || number == 1;
    }

    protected void soundVolumeChanged() {

        for (String name : activeSounds.keySet()) {
            long id = activeSounds.get(name);
            Sound sound = resources.getRegisteredSound(name);
            sound.setVolume(id, prototype.sound.volume);
        }

        if (prototype.sound.volume == 0) {
            disableSound();
        } else {
            enableSound();
        }
    }

    protected void musicVolumeChanged() {
        for (String name : activeMusic) {
            Music music = resources.getRegisteredMusic(name);
            music.setVolume(prototype.music.volume);
        }

        if (prototype.music.volume == 0) {
            disableMusic();
        } else {
            enableMusic();
        }
    }

    protected void soundStateChanged() {

        if (prototype.sound.state) return;

        for (String name : activeSounds.keySet()) {
            long id = activeSounds.get(name);
            Sound sound = resources.getRegisteredSound(name);
            sound.stop(id);
        }

        activeSounds.clear();
    }

    protected void musicStateChanged() {

        if (prototype.music.state) {
            startGlobalBackgroundMusic();
            return;
        }

        for (String name : activeMusic) {
            Music music = resources.getRegisteredMusic(name);
            music.stop();
        }

        activeMusic.clear();
    }
}
