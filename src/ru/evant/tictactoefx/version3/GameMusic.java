package ru.evant.tictactoefx.version3;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class GameMusic extends Pane {
    MediaPlayer mediaPlayer;

    public GameMusic(String musicPath) {
        Media music = new Media(Paths.get(musicPath).toUri().toString());
        mediaPlayer = new MediaPlayer(music);
    }

    public void play(){
        mediaPlayer.play();
    }
    public void stop(){
        mediaPlayer.stop();
        mediaPlayer.dispose();
    }
}
