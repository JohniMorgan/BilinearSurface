package com.etu.stud.lab_3fx.window.elements;

import com.etu.stud.lab_3fx.transformations.RectangularTransformer;
import com.etu.stud.lab_3fx.transformations.RotationTransformer;
import com.etu.stud.lab_3fx.transformations.ScaleTransformer;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    //Конструктор с параметрами:
    /*
    points: Список контрольных точек, которые ввёл пользователь
    realCenter: Точка центра на экране, от которой идёт начало координат
    scale: Масштаб рисуемой поверхности
    xAngle: Угол поворота вдоль оси X
    yAngle: Угол поворота вдоль оси Y
     */
    public SurfaceArea(Point3D[] points, Point2D realCenter, Double scale, Double xAngle, Double yAngle) {
        super();
        this.realCenter = realCenter;
        this.setPrefSize(WIDTH, HEIGHT);
        this.scale = scale;
        this.xAngle = xAngle;
        this.yAngle = yAngle;
        printPoints(points); //Нарисовать Контрольные точки (1*)
    }
    //(1*) Метод рисования контрольных точек
    protected void printPoints(Point3D[] points) {
        //Каждая точка из массива контрольных точек проходит ряд преобразований
        //Порядок выполнения - от самой глубокой до верхней
                          //Проецирование в нашу систему координат
        Point2D realP00 = RectangularTransformer.transform(
                //Масштабирование повёрнутой точки
                ScaleTransformer.transform(
                        //Поворот на угол xAngle
                        RotationTransformer.transformRotateX(
                                //Поворот на угол yAngle
                                RotationTransformer.transformRotateY(points[0], yAngle), xAngle), scale));
        //Формируем подпись для точки
        Pane labels = new Pane();
        Label l00 = new Label("P00");
        l00.setTranslateX(realP00.getX() + realCenter.getX() + 2); //Ставим её чуть правее...
        l00.setTranslateY(-realP00.getY() + realCenter.getY() - 4); //...И чуть выше самой точки
        //В конце концов формируем в этой точке окружность, представляющую эту точку
        P00 = new Circle(realP00.getX() + realCenter.getX(), -realP00.getY() + realCenter.getY(), 2);
        //Тот же цикл операций с точкой P01
        Point2D realP01 = RectangularTransformer.transform(ScaleTransformer.transform(RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(points[1], yAngle), xAngle), scale));
        Label l01 = new Label("P01");
        l01.setTranslateX(realP01.getX() + realCenter.getX() + 2);
        l01.setTranslateY(-realP01.getY() + realCenter.getY() - 4);
        P01 = new Circle(realP01.getX() + realCenter.getX(), -realP01.getY() + realCenter.getY(), 2);
        //Тот же цикл операций с точкой Р10
        Point2D realP10 = RectangularTransformer.transform(ScaleTransformer.transform(RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(points[2], yAngle), xAngle), scale));
        Label l10 = new Label("P10");
        l10.setTranslateX(realP10.getX() + realCenter.getX() + 2);
        l10.setTranslateY(-realP10.getY() + realCenter.getY() - 4);
        P10 = new Circle(realP10.getX() + realCenter.getX(), -realP10.getY() + realCenter.getY(), 2);
        //Тот же цикл операций с точкой Р11
        Point2D realP11 = RectangularTransformer.transform(ScaleTransformer.transform(RotationTransformer.transformRotateX(
                RotationTransformer.transformRotateY(points[3], yAngle), xAngle), scale));
        Label l11 = new Label("P11");
        l11.setTranslateX(realP11.getX() + realCenter.getX() + 2);
        l11.setTranslateY(-realP11.getY() + realCenter.getY() - 4);
        P11 = new Circle(realP11.getX() + realCenter.getX(), -realP11.getY() + realCenter.getY(), 2);
        //Добавляем подписи
        labels.getChildren().addAll(l00, l01, l10, l11);
        //И добавляем сами точки на наш график
        this.getChildren().addAll(P00, P01, P10, P11, labels);
        printSurfaceFirstStep(); //Рисование поверхности для первой пары направляющих
        printSurfaceSecondStep(); //И для второй пары направляющих
    }
    /*
    Рисование первой пары направляющих.
    Принцип: При афинном преобразовании прямая проецируется в прямую.
    Суть: Билинейная поверхность образована образующими прямыми
    движущимеся вдоль двух направляющих прямых.
    А следовательно для рисования в новой системе координат
    Достаточно найти концы образующей на новом шаге.
     */
    protected void printSurfaceFirstStep() {
        //Здесь параметр W фикисруется и меняет между 0 и 1
        //Рисуем с шагом параметра 0.1 (Таким образом получается 10 линий)
        for (Double u = 0.0; (u < 1.0) || (Math.abs(u - 1.0) < 0.0000001); u += 0.1) {
            Double startX = P00.getCenterX()*(1.0-u) + P01.getCenterX()*u; //Нахождение координат...
            Double startY = P00.getCenterY()*(1.0-u) + P01.getCenterY()*u; //Начала линии...
            Double endX = P10.getCenterX()*(1.0-u) + P11.getCenterX()*u; //И координат...
            Double endY = P10.getCenterY()*(1.0-u) + P11.getCenterY()*u; //Её конца
            this.getChildren().add(new Line(startX, startY,
                                            endX, endY)); //Рисуем полученную линию.
        }
    }
    //Меняем пару направляющих
    protected void printSurfaceSecondStep() {
        //Теперь мы фиксируем параметр U и меняем его между 0 и 1
        //Рисуем с тем же шагом параметра 0.1
        for (Double w = 0.0; (w < 1.0) || (Math.abs(w - 1.0) < 0.0000001); w += 0.1) {
            Double startX = P00.getCenterX()*(1.0-w) + P10.getCenterX()*w; //Находим начало
            Double startY = P00.getCenterY()*(1.0-w) + P10.getCenterY()*w; //Представленной лиинии
            Double endX = P01.getCenterX()*(1.0-w) + P11.getCenterX()*w; //И координаты
            Double endY = P01.getCenterY()*(1.0-w) + P11.getCenterY()*w; //Её конца
            this.getChildren().add(new Line(startX, startY, //рисуем линию
                    endX, endY));
        }
        /*
        Таким образом мы получаем сетку из 10x10 контрольных точек.
        Такое построение можно считать вполне деталезированным.
         */
    }
}
