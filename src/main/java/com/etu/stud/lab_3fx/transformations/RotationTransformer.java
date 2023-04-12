package com.etu.stud.lab_3fx.transformations;

import javafx.geometry.Point3D;

//Класс поворота вдоль осей X и Y
public class RotationTransformer {
    //Поворот вдоль оси X
    static final public Point3D transformRotateX(Point3D point, Double arg) {
        Double realArg = Math.PI/180*arg; //Угол в радианы...
        //Матрица поворота вдоль оси X
        Double [][] rotateXMatrix = {
                {1.0, 0.0, 0.0},
                {0.0, Math.cos(realArg), -Math.sin(realArg)},
                {0.0, Math.sin(realArg), Math.cos(realArg)}
        };
        Double[] vector = {point.getX(), point.getY(), point.getZ()};
        Double[] rotated = {0.0, 0.0, 0.0};
        //Перемножаем матрицы...
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotated[i] += rotateXMatrix[i][j]*vector[j];
            }
        }
        //Получаем новые координаты повёрнутой точки
        return new Point3D(rotated[0], rotated[1], rotated[2]);
    }
    //Поворот вдоль оси Y
    static final public Point3D transformRotateY(Point3D point, Double arg) {
        Double realArg = Math.PI/180*arg; //Угол в радианы...
        Double [][] rotateXMatrix = { //Матрица поворота вдоль оси Y
                {Math.cos(realArg), 0.0, Math.sin(realArg)},
                {0.0, 1.0, 0.0},
                {-Math.sin(realArg),0.0 , Math.cos(realArg)}
        };
        Double[] vector = {point.getX(), point.getY(), point.getZ()};
        Double[] rotated = {0.0, 0.0, 0.0};
        //Перемножаем матрицы...
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotated[i] += rotateXMatrix[i][j]*vector[j];
            }
        }
        //Получаем новую повёрнутую точку.
        return new Point3D(rotated[0], rotated[1], rotated[2]);
    }


}
