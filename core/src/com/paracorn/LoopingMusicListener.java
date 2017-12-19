package com.paracorn;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

public class LoopingMusicListener implements OnCompletionListener  {

    Music music;

    LoopingMusicListener(Music music) {
        this.music = music;
    }

    @Override
    public void onCompletion(Music music) {
        this.music.play();
    }

}
