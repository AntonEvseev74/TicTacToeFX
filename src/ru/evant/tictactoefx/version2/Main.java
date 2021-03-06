package ru.evant.tictactoefx.version2;

// Игра: крестики нолики

// Крестики-нолики — логическая игра между двумя противниками на квадратном поле 3 на 3 клетки.
// Пользователь играет играет «крестиками», AI — «ноликами».

// Правила:
// Игроки по очереди ставят на свободные клетки поля 3×3 знаки (один всегда крестики, другой всегда нолики).
// Первый, выстроивший в ряд 3 своих фигуры по вертикали, горизонтали или диагонали, выигрывает.
// Первый ход делает игрок, ставящий крестики.
// Обычно по завершении партии выигравшая сторона зачёркивает чертой свои три знака (нолика или крестика),
// составляющих сплошной ряд. !!!!!!!!!!!!!!!!(не реализовано)!!!!!!!!!!!!!

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
       Main.primaryStage = primaryStage;
        startGame();

    }

    public static void startGame(){
        primaryStage.setTitle("КрестикиНолики");
        primaryStage.setScene(new Scene(new Game().getRoot(), 330, 330));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
