package com.etu.stud.lab_3fx.window.elements;

import com.etu.stud.lab_3fx.AccessPoint3D;
import com.etu.stud.lab_3fx.transformations.RectangularTransformer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
/*-------------------------------------------------------------
CoordinateArea - класс, наследующий сцену в JavaFX, а потому
Явлется самым главным. Именно здесь отражены все элементы программы
А также единожды статически определяется система координат.
Здесь находятся обработчики основных событий и именно содержимое
этого класса видит пользователь программы.
 --------------------------------------------------------------*/
public class CoordinateArea extends Scene {
    static final Integer WIDTH = 500; //Ширина области координат
    static final Integer HEIGHT = 500; //Высота области координат
    static final BorderPane root = new BorderPane(); //Корневая понель программы
    static final BorderPane area = new BorderPane(); //Координатная область
    static final Point2D center = new Point2D(WIDTH/2.0, HEIGHT/2.0); //Центр координатной области
    Point3D[] control = new Point3D[4]; //Контрольные точки в пространстве
    PointTable table; //Таблица с данными
    SurfaceArea actualSurface = null; //Непосредственно график с поверхностью
    CircleSlider xRotate; //Слайдер поворота вдоль X
    Double xAngle = 0.0; //Угол поворота вдоль X
    CircleSlider yRotate; //Слайдер поворота вдоль Y
    Double yAngle = 0.0; //Угол поворота вдоль Y
    Double scale = 1.0; // Масштаб картинки
    public CoordinateArea() {
        super(root, WIDTH + 200, HEIGHT + 200, Color.AZURE); //Иницализация сцены
        area.setMaxSize(WIDTH, HEIGHT); //Инициализация области координат
        Point3D xDirection = new Point3D(WIDTH/2.0, 0, 0); //Определяем точки лежащие на осях
        Point3D yDirection = new Point3D(0, WIDTH/2.0, 0); //Именно по их поворотам
        Point3D zDirection = new Point3D(0, 0, WIDTH/2.0); //Мы будем строить оси
        //Создаём оси координат
        createGuideArrow(xDirection, 120.0, Color.RED, "X");
        createGuideArrow(yDirection, -120.0, Color.BLUE, "Y");
        createGuideArrow(zDirection, 0.0, Color.DARKGREEN, "Z");

        //Верхняя часть оконного интерфейса
        BorderPane topToolBox = new BorderPane();
        table = new PointTable(this);//Инициализируем таблицу
        topToolBox.setCenter(table.getTable());//Устанавливаем таблице в центре верхней части
        //Создаём подписи для строк этой таблицы
        //Для точки P00
        Pane p00Pane = new Pane();
        Label p00Label = new Label("P00=");
        p00Label.setTranslateX(128);
        p00Label.setTranslateY(50);
        p00Pane.getChildren().add(p00Label);
        //Для точки P01
        Pane p01Pane = new Pane();
        Label p01Label = new Label("P01=");
        p01Label.setTranslateX(128);
        p01Label.setTranslateY(80);
        p01Pane.getChildren().add(p01Label);
        //Для точки P10
        Pane p10Pane = new Pane();
        Label p10Label = new Label("P10=");
        p10Label.setTranslateX(128);
        p10Label.setTranslateY(110);
        p10Pane.getChildren().add(p10Label);
        //Для точки P11
        Pane p11Pane = new Pane();
        Label p11Label = new Label("P11=");
        p11Label.setTranslateX(128);
        p11Label.setTranslateY(140);
        p11Pane.getChildren().add(p11Label);
        //Добавляем на основную сцену подписи строк
        root.getChildren().addAll(p00Pane, p01Pane, p10Pane, p11Pane);
        //Добавляем в центр оконного интерфейса наши координаты
        root.setCenter(area);
        //Инициализируем наши слайдеры
        xRotate = new CircleSlider(50.0, 50.0, Color.RED, "Угол поворота\nОсь X");
        //Обёртываем их в панель
        Pane p = new Pane(xRotate.getArea());
        p.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xAngle = xRotate.getAngle();
                printControlPoints(); //Как только пользователь изменил угол
                //необходимо перерисовать поверхность
            }
        });
        p.setMaxSize(80.0, 80.0);
        p.setMinSize(80.0, 80.0);
        //Тоже самое делаем со вторым слайдером
        yRotate = new CircleSlider(50.0, 50.0, Color.BLUE, "Угол поворота\nОсь Y");
        //Оборачиваем
        Pane p2 = new Pane(yRotate.getArea());
        p2.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                yAngle = yRotate.getAngle();
                printControlPoints();
                //Перерисовываем с новым углом
            }
        });
        //Ставим слайдеры по обе стороны от таблицы
        //Ближе к соответствующим осям
        topToolBox.setLeft(p2);
        topToolBox.setRight(p);

        //Закрепляем таблицу и слайдеры на оконном интерфейсе.
        root.setTop(topToolBox);
        //Устанавливаем отступы от краёв окна
        BorderPane.setMargin(p2, new Insets(30,0, 0, 25));
        BorderPane.setMargin(p, new Insets(30, 25, 0, 0));
        BorderPane.setMargin(table.getTable(),new Insets(20.0, 0, 0, 0));

        //Кнопка уменьшения масштаба
        Button scaleInc = new Button("+");
        scaleInc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scale /= 1.5;
                printControlPoints();
            }
        });

        //Кнопка увеличения масштаба
        Button scaleDec = new Button("-");
        scaleDec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scale *= 1.5;
                printControlPoints();
            }
        });
        Label scaleLabel = new Label("Масштаб");
        //Кнопки управления масштабом будут расположены под графиком
        TilePane bottom = new TilePane();
        bottom.getChildren().addAll(scaleDec, scaleLabel, scaleInc); //Добавляем их обёртку
        root.setBottom(bottom); //И в само окно
        BorderPane.setMargin(bottom, new Insets(0, 0.0, 20.0, 270)); //Ставим отступы снизу и слева
    }

    //Метод создания координатной оси
    /*Параметры:
    endPoint: Точка на координатной оси в пространстве
    argRotate: Угол, на который в итоге повернётся ось
    color: Цвет оси
    name: Подпись оси
     */
    protected void createGuideArrow(Point3D endPoint, Double argRotate, Color color, String name) {
        Point2D newEndPoint = RectangularTransformer.transform(endPoint); //Проецируем на новую систему
        Line guide = new Line(center.getX(), center.getY(), //Создаём линию в новой системе
                newEndPoint.getX() + center.getX(), -newEndPoint.getY() + center.getY());
        guide.setStroke(color); //Задаём её цвет
        //Формируем стрелку на конце линии, в виде треугольника
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(0.0, 0.0,
                5.0, 10.0,
                -5.0, 10.0);
        arrow.setFill(guide.getStroke()); //заполняем внутри цветом
        arrow.setStroke(guide.getStroke());//И обводим этим же цветом
        arrow.setRotate(argRotate); //Проворачиваем в соответствии с углом направления оси
        arrow.setTranslateX(newEndPoint.getX() + center.getX()); //И ставим её в конец прямой
        arrow.setTranslateY(-newEndPoint.getY() + center.getY() - 5); //По второй координате тоже
        //Формируем подпись
        Pane p = new Pane();
        Label title = new Label(name);
        title.setTextFill(color); //Пишем тем же цветом
        title.setTranslateX(newEndPoint.getX() + center.getX()); //И ставим ее
        title.setTranslateY(-newEndPoint.getY() + center.getY() - 25); //Чуть выше конца линии
        p.getChildren().add(title); //Добавляем на сцену подпись
        area.getChildren().addAll(p, guide, arrow); //И саму ось
    }
    //Функция рисования контрольных точек
    protected void printControlPoints() {
        //Получаем табличные значения как массив
        AccessPoint3D[] points = table.getTable().getItems().toArray(new AccessPoint3D[4]);
        //Если на графике присутствовала поверхность, то её нужно перерисовать
        if (area.getChildren().contains(actualSurface)) area.getChildren().remove(actualSurface);
        try {
            //Пытаемся получить из табличных точек реальные
            for (int i = 0; i < 4; i++) {
                control[i] = points[i].castToPoint();
            }
            //Если у нас получилось, формируем новую поверхность с полученными параметрами
            actualSurface = new SurfaceArea(control, center, scale, xAngle, yAngle);
            //И выводим её на координатную плоскость
            area.getChildren().add(actualSurface);
        } catch (Exception e) {
            //Если из таблицы какую-то точку получить не удалось, значит не все точки были заполнены.
        }
    }
}
