package ru.evant.tictactoefx.version2;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Game extends Pane {

    private GridPane root;

    static int emptyCellCount = 9;
    public static boolean gameOver = false;

    private final String wins = "Конец игры! Вы выиграли!";
    private final String loss = "Конец игры! Вы проиграли!";
    private final String draw = "Конец игры! Ничья!";
    private String gameState = "";

    public final String X = " X ";   // Крестик
    public final String O = " O ";   // Нолик

    private final String[] gameField = new String[9]; // Создать массив длинной 9. Это 9 ячеек для поля 3 на 3 ячейки.
    private final boolean[] isEmpty = new boolean[9];
    {
        Arrays.fill(isEmpty, true);
        Arrays.fill(gameField, "");
    }

    private final ArrayList<Button> buttons = new ArrayList<>();


    private Image[] imagesX;
    {
        try {
            imagesX = new Image[5];
            for (int i = 0; i < imagesX.length; i++) {
                imagesX[i] = new Image(new FileInputStream("assets/x.png"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Image[] imagesO;
    {
        try {
            imagesO = new Image[5];
            for (int i = 0; i < imagesO.length; i++) {
                imagesO[i] = new Image(new FileInputStream("assets/o.png"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    int countImgX = 4;
    int countImgO = 4;
    private final ImageView[] imgX = new ImageView[5];//(imageX);
    {
        for (int i = 0; i < imgX.length; i++) {
            imgX[i] = new ImageView(imagesX[i]);
            imgX[i].setFitWidth(80);
            imgX[i].setFitHeight(80);
        }
    }

    private final ImageView[] imgO = new ImageView[5];//(imageO);
    {
        for (int i = 0; i < imgO.length; i++) {
            imgO[i] = new ImageView(imagesO[i]);
            imgO[i].setFitWidth(80);
            imgO[i].setFitHeight(80);
        }
    }


    public Game() {
        start();
    }

    /* Начать игру. Рисует игровое поле и ходы игроков. */
    public void start() {
        newRoot();
        createButtons();
        gameMove();
        setButtons();
    } // Конец метода start()

    /* Создать кнопки */
    public void createButtons() {
        for (int i = 0; i < 9; i++) {
            Button btn = new Button();
            btn.setPrefWidth(100);
            btn.setPrefHeight(100);
            buttons.add(btn);
        }
    } // Конец метода createButtons()

    // Обработка ходов игроков */
    public void gameMove() {
        for (int i = 0; i < buttons.size(); i++) {
            // Слушатель нажатия кнопки
            int index = i;
            buttons.get(index).setOnAction(event -> {
                if (!gameOver) {
                    // Ход пользователя
                    moveUser(index);

                    //ход пк
                    moveAI();
                }
            });
        }
    } // Конец метода gameMove()

    /* Создать контейнер для игрового поля из 9ти кнопок */
    public void newRoot() {
        root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10)); // отступы
        root.setHgap(5); // горизонтальные отступы между строками
        root.setVgap(5); // вертикальные отступы между столбцами
    } // Конец метода newRoot()

    /* Установить кнопки в контейнер игрового поля */
    public void setButtons() {
        for (Button b : buttons) {
            root.getChildren().add(b); // добавить кнопки поле в таблицу
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
            GridPane.setConstraints(buttons.get(i), column, row); // установить позицию кнопки в контейнере(таблице)
        }
    } // Конец метода setButtons()

    /* Ход игрока */
    public void moveUser(int index) {
        checkGameOver();
        if (!gameOver) {
            if (isEmpty[index]) {
                buttons.get(index).setGraphic(imgX[countImgX]);//setText("X");
                gameField[index] = X;
                countImgX--;
                emptyCellCount--;
                isEmpty[index] = false;
            }
        } else {
            System.out.println(gameState);//(textLoss);
            showAlert(gameState);//(textLoss);
        }
    } // Конец метода moveUser()

    /* Ход компьютера */
    public void moveAI() {
        checkGameOver();
        if (!gameOver) {
            int randomNumber = (int) (Math.random() * 8); // Присвоить пременной случайное число от 0 до 8. Это индексы ячеек игрового поля.

            if (isEmpty[randomNumber]) {
                buttons.get(randomNumber).setGraphic(imgO[countImgO]);//.setText("O");
                gameField[randomNumber] = O;
                countImgO--;
                emptyCellCount--;
                isEmpty[randomNumber] = false;
                checkGameOver();

                if (gameOver) {
                    System.out.println(gameState);
                    showAlert(gameState);
                }
            } else moveAI(); // Иначе сново запустить ход компьютера
        } else {
            System.out.println(gameState);//(textWins);
            showAlert(gameState);//(textWins);
        }
    } // конец метода moveAI()

    /* Проверка выигрышных комбинаций, три в ряд */
    private void checkGameOver() {
        if (emptyCellCount == 0) gameOver(draw);

        if (gameField[0].equals(X) && gameField[1].equals(X) && gameField[2].equals(X)) gameOver(wins);
        if (gameField[0].equals(O) && gameField[1].equals(O) && gameField[2].equals(O)) gameOver(loss);

        if (gameField[3].equals(X) && gameField[4].equals(X) && gameField[5].equals(X)) gameOver(wins);
        if (gameField[3].equals(O) && gameField[4].equals(O) && gameField[5].equals(O)) gameOver(loss);

        if (gameField[6].equals(X) && gameField[7].equals(X) && gameField[8].equals(X)) gameOver(wins);
        if (gameField[6].equals(O) && gameField[7].equals(O) && gameField[8].equals(O)) gameOver(loss);

        if (gameField[0].equals(X) && gameField[3].equals(X) && gameField[6].equals(X)) gameOver(wins);
        if (gameField[0].equals(O) && gameField[3].equals(O) && gameField[6].equals(O)) gameOver(loss);

        if (gameField[1].equals(X) && gameField[4].equals(X) && gameField[7].equals(X)) gameOver(wins);
        if (gameField[1].equals(O) && gameField[4].equals(O) && gameField[7].equals(O)) gameOver(loss);

        if (gameField[2].equals(X) && gameField[5].equals(X) && gameField[8].equals(X)) gameOver(wins);
        if (gameField[2].equals(O) && gameField[5].equals(O) && gameField[8].equals(O)) gameOver(loss);

        if (gameField[0].equals(X) && gameField[4].equals(X) && gameField[8].equals(X)) gameOver(wins);
        if (gameField[0].equals(O) && gameField[4].equals(O) && gameField[8].equals(O)) gameOver(loss);

        if (gameField[2].equals(X) && gameField[4].equals(X) && gameField[6].equals(X)) gameOver(wins);
        if (gameField[2].equals(O) && gameField[4].equals(O) && gameField[6].equals(O)) gameOver(loss);
    } // конец метода checkGameOver()

    /* Установить конец игры и текст */
    private void gameOver(String text) {
        gameOver = true;
        gameState = text;
    }

    /* Возвращает контейнер типа GridPane */
    public GridPane getRoot() {
        return root;
    } // Конец метода getRoot()

    /* Создать окно с сообщением о победе/проигрыше и предложении начать новую игру */
    private void showAlert(String text) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Игра закончена");
        alert.setHeaderText(text);
        alert.setContentText("Начать сначала?");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) startNewGame(); // Начать игру сначала
        if (option.get() == ButtonType.CANCEL) closeGame(alert); // Закрыть программу

    } // Конец метода showAlert()

    /* Начать новую игру */
    private void startNewGame() {
        Arrays.fill(isEmpty, true);
        Arrays.fill(gameField, "");
        gameOver = false;
        emptyCellCount = gameField.length;
        Main.startGame();
    } // Конец метода startNewGame()

    /* Закрыть игру */
    private void closeGame(Alert alert){
        alert.close(); // Закрыть алерт
        Main.primaryStage.close(); // Закрыть игру
    }

}
