package ru.evant.tictactoefx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

public class Game extends Pane {

    private final GridPane root;

    private final boolean[] isEmpty = new boolean[9];

    {
        Arrays.fill(isEmpty, true);
    }

    static int emptyCellCount = 9;
    public static boolean gameOver = false;

    private final String textWin = "Конец игры! Вы выиграли!";
    private final String textLos = "Конец игры! Вы проиграли!";

    private final ArrayList<Button> buttons = new ArrayList<>();

    public Game() {
        root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10)); // отступы
        root.setHgap(5); // горизонтальные отступы между строками
        root.setVgap(5); // вертикальные отступы между столбцами

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
                    // Ход пользователя
                    moveUser(isEmpty, buttons, index);

                    //ход пк
                    moveAI(isEmpty, buttons);
                }
            });
        }

        for (Button b : buttons) {
            root.getChildren().add(b); // добавить кнопки поле в таблицу
        }
    }

    /* Ход игрока */
    public void moveUser(boolean[] isEmpty, ArrayList<Button> buttons, int index){
        checkGameOver(buttons);
        if (!gameOver) {
            if (isEmpty[index]) {
                buttons.get(index).setText("X");
                emptyCellCount--;
                isEmpty[index] = false;
            }
        } else {
            System.out.println(textLos);
            buttonsVisible(buttons);
            createLabel(textLos);
        }
    } // Конец метода moveUser()

    /* Ход компьютера */
    public void moveAI(boolean[] isEmpty, ArrayList<Button> buttons) {
        checkGameOver(buttons);
        if (!gameOver) {
            int randomNumber = (int) (Math.random() * 8); // Присвоить пременной случайное число от 0 до 8. Это индексы ячеек игрового поля.

            if (isEmpty[randomNumber]) {
                buttons.get(randomNumber).setText("O");
                emptyCellCount--;
                isEmpty[randomNumber] = false;
                checkGameOver(buttons);

                if (gameOver) {
                    System.out.println(textLos);
                    buttonsVisible(buttons);
                    createLabel(textLos);
                }
            } else moveAI(isEmpty, buttons); // Иначе сново запустить ход компьютера
        } else {
            System.out.println(textWin);
            buttonsVisible(buttons);
            createLabel(textWin);
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

    /* Возвращает контейнер типа GridPane */
    public GridPane getRoot(){
        return root;
    } // Конец метода getRoot()


    /* Сделать кнопки не видимыми */
    private void buttonsVisible(ArrayList<Button> buttons){
        for (Button b:buttons) {
            b.setVisible(false);
        }
    }

    /* Создать текстовое поле */
    // открыть новоеокно с текстом и кнопкой, начать новую игру !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void createLabel(String text){
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setTranslateX(70);
        label.setPrefWidth(200);
        root.getChildren().add(label);

        label.setOnMouseClicked(event -> {

        });
    }
}
