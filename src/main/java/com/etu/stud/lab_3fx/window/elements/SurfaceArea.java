package com.etu.stud.lab_3fx.window.elements;

import com.etu.stud.lab_3fx.transformations.RectangularTransformer;
import com.etu.stud.lab_3fx.transformations.RotationTransformer;
import com.etu.stud.lab_3fx.transformations.ScaleTransformer;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/*---------------------------------------------------
Один из самых важных классов. SurfaceArea представляет
из себя непосредственно график поверхности.
Этот класс отвечает за отрисовку поверхности на экран.
 ----------------------------------------------------*/
public class SurfaceArea extends BorderPane {
    //Набор контрольных точек
    Circle P00; //Такие контрольные точки...
    Circle P01; //Представлены на экране...
    Circle P10; //Окружностями с очень..
    Circle P11; //Маленьким радиусом.
    Point2D realCenter; //Точка центра (!)на Экране(!)
    static final Double WIDTH = 500.0; //Ширина координатной области
    static final Double HEIGHT = 500.0; //Высота коорднатной области
    Double scale; //Масштаб графика
    Double xAngle; //Угол поворота вдоль оси X
    Double yAngle; //Угол поворота вдоль оси Y
    Double zAngle;
    //Конструктор с параметрами:
    /*
    points: Список контрольных точек, которые ввёл пользователь
    realCenter: Точка центра на экране, от которой идёт начало координат
    scale: Масштаб рисуемой поверхности
    xAngle: Угол поворота вдоль оси X
    yAngle: Угол поворота вдоль оси Y
     */
    public SurfaceArea(Point3D[] points, Point2D realCenter, Double scale, Double xAngle, Double yAngle, Double zAngle) {
        super();
        this.realCenter = realCenter;
        this.setPrefSize(WIDTH, HEIGHT);
        this.scale = scale;
        this.xAngle = xAngle;
        this.yAngle = yAngle;
        this.zAngle = zAngle;
        printPoints(points); //Нарисовать Контрольные точки (1*)
    }
    //(1*) Метод рисования контрольных точек
    protected void printPoints(Point3D[] points) {
        //Каждая точка из массива контрольных точек проходит ряд преобразований
        //Порядок выполнения - от самой глубокой до верхней
        //Проецирование в нашу систему координат
        Point3D realP00 =
                //Масштабирование повёрнутой точки
                ScaleTransformer.transform(
                        //Поворот на угол xAngle
                        RotationTransformer.transformRotateX(
                                //Поворот на угол yAngle
                                RotationTransformer.transformRotateY(
                                        RotationTransformer.transformRotateZ(points[0], zAngle), yAngle), xAngle), scale);
        Point3D realP01 = ScaleTransformer.transform(RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(
                        RotationTransformer.transformRotateZ(points[1], zAngle), yAngle), xAngle), scale);
        Point3D realP10 = ScaleTransformer.transform(RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(
                        RotationTransformer.transformRotateZ(points[2], zAngle), yAngle), xAngle), scale);
        Point3D realP11 = ScaleTransformer.transform(RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(RotationTransformer.transformRotateZ(points[3], zAngle), yAngle), xAngle), scale);
        Double massX, massY, massZ;
        massX = realP00.getX() + realP01.getX() + realP10.getX() + realP11.getX();
        massX = massX/4;
        massY = realP00.getY() + realP01.getY() + realP10.getY() + realP11.getY();
        massY = massY/4;
        massZ = realP00.getZ() + realP01.getZ() + realP10.getZ() + realP11.getZ();
        massZ = massZ/4;
        Point3D centerMass = new Point3D(massX, massY, massZ);
        PyromidPolygon first = new PyromidPolygon(realCenter,Color.RED, new Point3D[]{
                points[0], points[1], points[2]
        }, xAngle, yAngle, zAngle, scale, centerMass);
        PyromidPolygon second = new PyromidPolygon(realCenter, Color.BLUE, new Point3D[]{
                points[0], points[1], points[3]
        }, xAngle, yAngle,zAngle, scale, centerMass);
        PyromidPolygon third = new PyromidPolygon(realCenter, Color.GREEN, new Point3D[]{
                points[1], points[2], points[3]
        }, xAngle, yAngle, zAngle, scale, centerMass);
        PyromidPolygon forth = new PyromidPolygon(realCenter, Color.CYAN, new Point3D[]{
                points[0], points[2], points[3]
        }, xAngle, yAngle, zAngle, scale, centerMass);
        centerMass = RectangularTransformer.transform(centerMass);
        Circle massa = new Circle(centerMass.getX() + realCenter.getX(), -centerMass.getY() + realCenter.getY(), 2);
        getChildren().add(massa);
        massa.setFill(Color.PURPLE);
        realP00 = RectangularTransformer.transform(realP00);
        realP01 = RectangularTransformer.transform(realP01);
        realP10 = RectangularTransformer.transform(realP10);
        realP11 = RectangularTransformer.transform(realP11);
        //Формируем подпись для точки
        Pane labels = new Pane();
        Label l00 = new Label("P00");
        l00.setTranslateX(realP00.getX() + realCenter.getX() + 2); //Ставим её чуть правее...
        l00.setTranslateY(-realP00.getY() + realCenter.getY() - 4); //...И чуть выше самой точки
        //В конце концов формируем в этой точке окружность, представляющую эту точку
        P00 = new Circle(realP00.getX() + realCenter.getX(), -realP00.getY() + realCenter.getY(), 2);
        //Тот же цикл операций с точкой P01
        Label l01 = new Label("P01");
        l01.setTranslateX(realP01.getX() + realCenter.getX() + 2);
        l01.setTranslateY(-realP01.getY() + realCenter.getY() - 4);
        P01 = new Circle(realP01.getX() + realCenter.getX(), -realP01.getY() + realCenter.getY(), 2);
        //Тот же цикл операций с точкой Р10
        Label l10 = new Label("P10");
        l10.setTranslateX(realP10.getX() + realCenter.getX() + 2);
        l10.setTranslateY(-realP10.getY() + realCenter.getY() - 4);
        P10 = new Circle(realP10.getX() + realCenter.getX(), -realP10.getY() + realCenter.getY(), 2);
        //Тот же цикл операций с точкой Р11
        Label l11 = new Label("P11");
        l11.setTranslateX(realP11.getX() + realCenter.getX() + 2);
        l11.setTranslateY(-realP11.getY() + realCenter.getY() - 4);
        P11 = new Circle(realP11.getX() + realCenter.getX(), -realP11.getY() + realCenter.getY(), 2);
        //Добавляем подписи
        labels.getChildren().addAll(l00, l01, l10, l11);
        //И добавляем сами точки на наш график
        this.getChildren().addAll(P00, P01, P10, P11, labels);
        getChildren().addAll(first, second, third, forth);


        printSurface();//И для второй пары направляющих
    }
    /*
    Рисование первой пары направляющих.
    Принцип: При афинном преобразовании прямая проецируется в прямую.
    Суть: Билинейная поверхность образована образующими прямыми
    движущимеся вдоль двух направляющих прямых.
    А следовательно для рисования в новой системе координат
    Достаточно найти концы образующей на новом шаге.
     */
    protected void printSurface() {
        /*Polygon first = new Polygon();
        first.getPoints().addAll(P00.getCenterX(), P00.getCenterY(),
                               P01.getCenterX(), P01.getCenterY(),
                                P10.getCenterX(), P10.getCenterY());
        first.setStroke(Color.BLACK);
        first.setFill(null);
        Polygon second = new Polygon();
        second.getPoints().addAll(P00.getCenterX(), P00.getCenterY(),
                P01.getCenterX(), P01.getCenterY(),
                P11.getCenterX(), P11.getCenterY());
        second.setStroke(Color.BLACK);
        second.setFill(null);
        Polygon third = new Polygon();
        third.getPoints().addAll(P00.getCenterX(), P00.getCenterY(),
                P10.getCenterX(), P10.getCenterY(),
                P11.getCenterX(), P11.getCenterY());
        third.setStroke(Color.BLACK);
        third.setFill(null);
        Polygon forth = new Polygon();
        forth.getPoints().addAll(P10.getCenterX(), P10.getCenterY(),
                P01.getCenterX(), P01.getCenterY(),
                P11.getCenterX(), P11.getCenterY());
        forth.setStroke(Color.BLACK);
        forth.setFill(null);
        getChildren().addAll(first, second, third, forth);*/
    }
    //Меняем пару направляющих

}
