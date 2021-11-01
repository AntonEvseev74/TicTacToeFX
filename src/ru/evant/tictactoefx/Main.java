package ru.evant.tictactoefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Game game = new Game();

        Scene scene = new Scene(game.getRoot(), 330, 330);
        primaryStage.setTitle("КрестикиНолики");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
