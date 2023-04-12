package com.etu.stud.lab_3fx.transformations;

import javafx.geometry.Point3D;

public class ScaleTransformer {
    //Обычный преобразователь масштаба
    static public Point3D transform(Point3D point, Double scale) {
        return new Point3D(point.getX()/scale, point.getY()/scale, point.getZ()/scale);
    }

}
