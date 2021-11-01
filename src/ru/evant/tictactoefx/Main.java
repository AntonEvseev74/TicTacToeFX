package ru.evant.tictactoefx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    GridPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10)); // отступы
        root.setHgap(5); // горизонтальные отступы между строками
        root.setVgap(5); // вертикальные отступы между столбцами

        Map map = new Map(root);

        Scene scene = new Scene(root, 330, 330);
        primaryStage.setTitle("КрестикиНолики");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
