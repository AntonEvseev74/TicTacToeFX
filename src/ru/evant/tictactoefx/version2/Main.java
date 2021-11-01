package ru.evant.tictactoefx.version2;

// Проблемы:
// Реализовать цикл игры, чтобы была возможность начать игру сначала

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
       this.primaryStage = primaryStage;
        startGame();

    }

    public static void startGame(){
        primaryStage.setTitle("КрестикиНолики");
        primaryStage.setScene(new Scene(new Game().getRoot(), 330, 330));
        primaryStage.show();
    }

    public static Stage get(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
