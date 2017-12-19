package com.paracorn;

import com.badlogic.gdx.audio.Music;

public class MusicPlayer implements Music.OnCompletionListener {

    private Music intro;
    private Music loop;
    private boolean isPlayingIntro = true;

    private float volume = 0.5f;

    public MusicPlayer(Music intro, Music loop) {
        this.intro = intro;
        this.loop = loop;
    }

    public void play() {
        loop.stop();
        intro.setVolume(volume);
        loop.setVolume(volume);
        loop.setLooping(true);
        intro.setOnCompletionListener(this);
        intro.play();
        isPlayingIntro = true;

    }

    public void pause() {
        intro.pause();
        loop.pause();
    }

    public void resume() {
        if(isPlayingIntro) { intro.play(); }
        else { loop.play(); }
    }

    public void mute() {
        intro.setVolume(0);
        loop.setVolume(0);
    }

    public void unmute() {
        intro.setVolume(volume);
        loop.setVolume(volume);
    }

    @Override
    public void onCompletion(Music music) {
        loop.play();
        isPlayingIntro = false;
    }
}
