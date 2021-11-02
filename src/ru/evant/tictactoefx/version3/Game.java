package ru.evant.tictactoefx.version3;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Game extends Pane {

    private Pane rootMain;
    private GridPane root;
    private GameMusic music;

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

    /* Возвращает контейнер типа GridPane */
    public Pane getRoot() {
        return rootMain;
    } // Конец метода getRoot()

    /* Начать игру. Рисует игровое поле и ходы игроков. */
    private void start() {
        String musicPath = "assets/music.mp3";
        music = new GameMusic(musicPath);
        music.play();

        newMainRoot();
        newRoot();
        createButtons();
        gameMove();
        setButtons();
    } // Конец метода start()

    /* Создать кнопки */
    private void createButtons() {
        for (int i = 0; i < 9; i++) {
            Button btn = new Button();
            btn.setPrefWidth(100);
            btn.setPrefHeight(100);
            buttons.add(btn);
        }
    } // Конец метода createButtons()

    // Обработка ходов игроков */
    private void gameMove() {
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
    private void newMainRoot() {
        rootMain = new Pane();
        rootMain.setPadding(new Insets(10, 10, 10, 10)); // отступы
    } // Конец метода newRoot()

    /* Создать контейнер для игрового поля из 9ти кнопок */
    private void newRoot() {
        root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10)); // отступы
        root.setHgap(5); // горизонтальные отступы между строками
        root.setVgap(5); // вертикальные отступы между столбцами

        rootMain.getChildren().add(root);
    } // Конец метода newRoot()

    /* Установить кнопки в контейнер игрового поля */
    private void setButtons() {
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
    private void moveUser(int index) {
        checkGameOver();
        if (!gameOver) {
            if (isEmpty[index]) {
                buttons.get(index).setGraphic(imgX[countImgX]);
                buttons.get(index).setDisable(true);
                gameField[index] = X;
                countImgX--;
                emptyCellCount--;
                isEmpty[index] = false;
            }
        } else {
            showAlert(gameState);
        }
    } // Конец метода moveUser()

    /* Ход компьютера */
    private void moveAI() {
        checkGameOver();
        if (!gameOver) {
            int randomNumber = (int) (Math.random() * 8); // Присвоить пременной случайное число от 0 до 8. Это индексы ячеек игрового поля.

            if (isEmpty[randomNumber]) {
                buttons.get(randomNumber).setGraphic(imgO[countImgO]);
                buttons.get(randomNumber).setDisable(true);
                gameField[randomNumber] = O;
                countImgO--;
                emptyCellCount--;
                isEmpty[randomNumber] = false;
                checkGameOver();

                if (gameOver) {
                    showAlert(gameState);
                }
            } else moveAI(); // Иначе сново запустить ход компьютера
        } else {
            showAlert(gameState);
        }
    } // конец метода moveAI()

    /* Проверка выигрышных комбинаций, три в ряд */
    private void checkGameOver() {
        if (emptyCellCount == 0) gameOver(draw, 0);

        if (gameField[0].equals(X) && gameField[1].equals(X) && gameField[2].equals(X)) gameOver(wins,1);
        if (gameField[0].equals(O) && gameField[1].equals(O) && gameField[2].equals(O)) gameOver(loss,1);

        if (gameField[3].equals(X) && gameField[4].equals(X) && gameField[5].equals(X)) gameOver(wins,2);
        if (gameField[3].equals(O) && gameField[4].equals(O) && gameField[5].equals(O)) gameOver(loss,2);

        if (gameField[6].equals(X) && gameField[7].equals(X) && gameField[8].equals(X)) gameOver(wins,3);
        if (gameField[6].equals(O) && gameField[7].equals(O) && gameField[8].equals(O)) gameOver(loss,3);

        if (gameField[0].equals(X) && gameField[3].equals(X) && gameField[6].equals(X)) gameOver(wins,4);
        if (gameField[0].equals(O) && gameField[3].equals(O) && gameField[6].equals(O)) gameOver(loss,4);

        if (gameField[1].equals(X) && gameField[4].equals(X) && gameField[7].equals(X)) gameOver(wins,5);
        if (gameField[1].equals(O) && gameField[4].equals(O) && gameField[7].equals(O)) gameOver(loss,5);

        if (gameField[2].equals(X) && gameField[5].equals(X) && gameField[8].equals(X)) gameOver(wins,6);
        if (gameField[2].equals(O) && gameField[5].equals(O) && gameField[8].equals(O)) gameOver(loss,6);

        if (gameField[0].equals(X) && gameField[4].equals(X) && gameField[8].equals(X)) gameOver(wins,7);
        if (gameField[0].equals(O) && gameField[4].equals(O) && gameField[8].equals(O)) gameOver(loss,7);

        if (gameField[2].equals(X) && gameField[4].equals(X) && gameField[6].equals(X)) gameOver(wins,8);
        if (gameField[2].equals(O) && gameField[4].equals(O) && gameField[6].equals(O)) gameOver(loss,8);
    } // конец метода checkGameOver()

    /* Установить конец игры и текст */
    private void gameOver(String text, int indexLine) {
        gameOver = true;
        gameState = text;

        //нарисовать линию (зачеркнуть поля)
        drawWinLine(indexLine);
    }

    /* Зачеркнуть выигрышную комбинацию */
    private void drawWinLine(int indexLine){
        WinLine wl = new WinLine(indexLine);
        Line line = wl.get();
        rootMain.getChildren().add(line);
    }

    /* Создать окно с сообщением о победе/проигрыше и предложении начать новую игру */
    private void showAlert(String text) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Игра закончена");
        alert.setHeaderText(text);
        alert.setContentText("Начать сначала?");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            music.stop();
            startNewGame(); // Начать игру сначала
        }
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
