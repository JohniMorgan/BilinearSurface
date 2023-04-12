package com.etu.stud.lab_3fx.transformations;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public class RectangularTransformer {
//Класс преобразования в прямоугольную изометрическую проекцию
//на плоскость экрана.

    //Матрица поворота в прямоугольную изометрическую проекцию
    //Необходимы только координаты Х и Y
    static double[][] TransformerMatrix = {
            {Math.sqrt(3), -Math.sqrt(3), 0},
            {-1, -1, 2}
    };
    public static Point2D transform(Point3D point) {
        double[] vector = {point.getX(), point.getY(), point.getZ()};
        double[] c2d = {0, 0};
        //Перемножаем матрицы друг на друга.
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                c2d[i] += TransformerMatrix[i][j]*vector[j];
            }
            c2d[i] *= 1/Math.sqrt(6);
        }
        return new Point2D(c2d[0], c2d[1]); //Полученная точка на экране.
    }
}
