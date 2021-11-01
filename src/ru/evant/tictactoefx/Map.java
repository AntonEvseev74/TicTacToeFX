package ru.evant.tictactoefx;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

public class Map extends Pane {

    private final boolean[] isEmpty = new boolean[9];

    {
        Arrays.fill(isEmpty, true);
    }

    static int emptyCellCount = 9;
    static boolean gameOver = false;

    private final ArrayList<Button> buttons = new ArrayList<>();

    public Map(GridPane pane) {
        // Создать кнопки
        for (int i = 0; i < 9; i++) {
            Button btn = new Button();
            btn.setPrefWidth(100);
            btn.setPrefHeight(100);
            buttons.add(btn);
        }

        for (int i = 0; i < buttons.size(); i++) {
            int column = 0;
            int row = 0;
            if (i <= 2) {
                column = i;
            }
            if (i >= 3 && i <= 5) {
                column = i - 3;
                row = 1;
            }
            if (i >= 6 && i <= 8) {
                column = i - 6;
                row = 2;
            }
            GridPane.setConstraints(buttons.get(i), column, row); // установить позицию текстового поля в таблице

            // Слушатель нажатия кнопки
            int index = i;
            buttons.get(index).setOnAction(event -> {

                if(!gameOver) {
                    if (isEmpty[index]) {
                        buttons.get(index).setText("X");
                        emptyCellCount--;
                        isEmpty[index] = false;
                        System.out.println("player" + emptyCellCount);
                        checkGameOver(buttons);
                    }

                    //ход пк
                    moveAI(isEmpty, buttons);
                } else {
                    System.out.println("Game Over");
                }
            });
        }

        for (Button b : buttons) {
            pane.getChildren().add(b); // добавить текстово поле в таблицу
        }
    }

    /* Ход компьютера */
    public static void moveAI(boolean[] isEmpty, ArrayList<Button> buttons) {
        //checkGameOver(buttons);

        if (!gameOver) {
            int randomNumber = (int) (Math.random() * 8); // Присвоить пременной случайное число от 0 до 8. Это индексы ячеек игрового поля.

            if (isEmpty[randomNumber]) {
                buttons.get(randomNumber).setText("O");
                emptyCellCount--;
                isEmpty[randomNumber] = false;
                System.out.println("bot" + emptyCellCount);
                checkGameOver(buttons);
            } else moveAI(isEmpty, buttons); // Иначе сново запустить ход компьютера
        } else {
            System.out.println("Game Over");
        }
    } // конец метода moveAI()

    /* Проверка выигрышных комбинаций, три в ряд */
    private static void checkGameOver(ArrayList<Button> buttons) {
        if (emptyCellCount == 0) gameOver = true;

        if (buttons.get(0).getText().equals("X") && buttons.get(1).getText().equals("X") && buttons.get(2).getText().equals("X"))
            gameOver = true;
        if (buttons.get(0).getText().equals("O") && buttons.get(1).getText().equals("O") && buttons.get(2).getText().equals("O"))
            gameOver = true;

        if (buttons.get(3).getText().equals("X") && buttons.get(4).getText().equals("X") && buttons.get(5).getText().equals("X"))
            gameOver = true;
        if (buttons.get(3).getText().equals("O") && buttons.get(4).getText().equals("O") && buttons.get(5).getText().equals("O"))
            gameOver = true;

        if (buttons.get(6).getText().equals("X") && buttons.get(7).getText().equals("X") && buttons.get(8).getText().equals("X"))
            gameOver = true;
        if (buttons.get(6).getText().equals("O") && buttons.get(7).getText().equals("O") && buttons.get(8).getText().equals("O"))
            gameOver = true;

        if (buttons.get(0).getText().equals("X") && buttons.get(3).getText().equals("X") && buttons.get(6).getText().equals("X"))
            gameOver = true;
        if (buttons.get(0).getText().equals("O") && buttons.get(3).getText().equals("O") && buttons.get(6).getText().equals("O"))
            gameOver = true;

        if (buttons.get(1).getText().equals("X") && buttons.get(4).getText().equals("X") && buttons.get(7).getText().equals("X"))
            gameOver = true;
        if (buttons.get(1).getText().equals("O") && buttons.get(4).getText().equals("O") && buttons.get(7).getText().equals("O"))
            gameOver = true;

        if (buttons.get(2).getText().equals("X") && buttons.get(5).getText().equals("X") && buttons.get(8).getText().equals("X"))
            gameOver = true;
        if (buttons.get(2).getText().equals("O") && buttons.get(5).getText().equals("O") && buttons.get(8).getText().equals("O"))
            gameOver = true;

        if (buttons.get(0).getText().equals("X") && buttons.get(4).getText().equals("X") && buttons.get(8).getText().equals("X"))
            gameOver = true;
        if (buttons.get(0).getText().equals("O") && buttons.get(4).getText().equals("O") && buttons.get(8).getText().equals("O"))
            gameOver = true;

        if (buttons.get(2).getText().equals("X") && buttons.get(4).getText().equals("X") && buttons.get(6).getText().equals("X"))
            gameOver = true;
        if (buttons.get(2).getText().equals("O") && buttons.get(4).getText().equals("O") && buttons.get(6).getText().equals("O"))
            gameOver = true;
    } // конец метода checkGameOver()
}
