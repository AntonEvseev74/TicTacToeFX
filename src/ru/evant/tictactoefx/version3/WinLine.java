package ru.evant.tictactoefx.version3;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class WinLine extends Pane {

    Line line;

    double[][] listLines = {
            {0, 0, 0, 0, 0},
            {20, 60, 310, 60, 10},
            {20, 165, 310, 165, 10},
            {20, 270, 310, 270, 10},
            {60, 20, 60, 310, 10},
            {165, 20, 165, 310, 10},
            {270, 20, 270, 310, 10},
            {20, 20, 310, 310, 10},
            {310, 20, 20, 310, 10}
    };

    double startX;
    double startY;
    double endX;
    double endY;
    double thickness; // толщина линии

    public WinLine(int indexWinLine) {
        startX = listLines[indexWinLine][0];
        startY = listLines[indexWinLine][1];
        endX = listLines[indexWinLine][2];
        endY = listLines[indexWinLine][3];
        thickness = listLines[indexWinLine][4];
        line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(thickness);
    }

    public Line get() {
        return line;
    }
}
