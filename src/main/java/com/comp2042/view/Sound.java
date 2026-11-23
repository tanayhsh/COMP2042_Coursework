package com.comp2042.view;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class Sound {

    private static final String coinSound = "/resources/views/res/coinSound.wav";

    private final AudioClip coinClip;
    private boolean muted = false;

    public Sound(){
        URL location = Sound.class.getResource(coinSound);
        if (location == null){
            throw new IllegalStateException("Coin sound not found at: " + coinSound);
        }
        coinClip = new AudioClip(location.toExternalForm());
    }

    public void playCoin(){
        if (!muted){
            coinClip.play();
        }
    }

    public boolean toggleMute(){
        muted = !muted;
        return muted;
    }
}
