package com.etu.stud.lab_3fx.window.elements;

import com.etu.stud.lab_3fx.transformations.RectangularTransformer;
import com.etu.stud.lab_3fx.transformations.RotationTransformer;
import com.etu.stud.lab_3fx.transformations.ScaleTransformer;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class PyromidPolygon extends Polygon {
    private Double Nz;
    private Line normal;
    public PyromidPolygon(Point2D center, Color color, Point3D[] realPoints, Double argX, Double argY, Double argZ, Double scale, Point3D mass) {
        super();
        Point3D A, B, C;
        A = ScaleTransformer.transform(
                RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(
                RotationTransformer.transformRotateZ(realPoints[0], argZ), argY), argX), scale);
        B = ScaleTransformer.transform(
                RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(
                RotationTransformer.transformRotateZ(realPoints[1], argZ), argY), argX), scale);
        C = ScaleTransformer.transform(
                RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(
                RotationTransformer.transformRotateZ(realPoints[2], argZ), argY), argX), scale);
        Nz = A.getX()*(B.getY() - C.getY()) + B.getX()*(C.getY() - A.getY()) +
                C.getX()*(A.getY() - B.getY());
        Double Nx, Ny;
        Nx = A.getY()*(B.getZ() - C.getZ()) +
                B.getY()*(C.getZ() - A.getZ()) +
                C.getY()*(A.getZ() - B.getZ());
        Ny = A.getZ()*(B.getX() - C.getX()) +
                B.getZ()*(C.getX() - A.getX()) +
                C.getZ()*(A.getX() - B.getX());

        Double modul = Math.sqrt(Nx*Nx + Ny*Ny + Nz*Nz);
        Nz = Nz / modul;
        Ny = Ny / modul;
        Nx = Nx / modul;
        Double d = -(Nx*A.getX() + Ny*A.getY() + Nz*A.getZ());
       if (Nx*(mass.getX()-A.getX()) + Ny*(mass.getY()-A.getY()) + Nz*(mass.getZ()-A.getZ()) > 0) {
            Nx = -Nx;
            Ny = -Ny;
            Nz = -Nz;
            d = -d;
        }
       Boolean visible = Nz*1000000 + Ny*1000000 + Nx*1000000 + d > 0;
       if (CoordinateArea.flag == 0) {
           setStroke(Color.BLACK);
           setFill(null);
           if (!visible) {
               getStrokeDashArray().addAll(5d, 10d);
               setStrokeDashOffset(0);
           }
       } else {
           setStroke(visible ? Color.BLACK : null);
           setFill(visible ? color : null);
       }
        A = RectangularTransformer.transform(A);
        B = RectangularTransformer.transform(B);
        C = RectangularTransformer.transform(C);
        Point3D normalVector = RectangularTransformer.transform(new Point3D(Nx*100, Ny*100, Nz*100));
        normal = new Line(A.getX() + center.getX(), -A.getY() + center.getY(),
                A.getX() + center.getX() + normalVector.getX(), -A.getY() + center.getY() - normalVector.getY());
        getPoints().addAll(A.getX() + center.getX(), -A.getY() + center.getY(),
                            B.getX() + center.getX(), -B.getY() + center.getY(),
                            C.getX() + center.getX(), -C.getY() + center.getY());
    }
    public Line getNormal() {
        return normal;
    }
}
