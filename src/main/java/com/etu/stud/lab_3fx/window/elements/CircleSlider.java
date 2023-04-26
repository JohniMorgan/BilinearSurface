package com.etu.stud.lab_3fx.window.elements;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Collection;
/*------------------------------------------------------
CircleSlider - кастомный класс кругового слайдера.
Позволяет выбрать значение в диапазоне от 0 до 360
Используется для выбора угла поворота вдоль осей
 -------------------------------------------------------*/
public class CircleSlider {
    Group area; //Полученный слайдер
    private static final Double RADIUS = 40.0; //Радиус слайдера
    private Point2D center;//Центр слайдера
    private Circle circle; //"Полоска" слайдера
    private Arc arc;// Индикатор выбранного угла
    private Line line; //Бегунок по слайдеру
    private Double angle = 0.0; // Угол поворота
    String name; //Подпись слайдера как строка
    Label title; //Подпись слайдера как надпись интерфейса

    //Инициализация слайдера центром, цветом индикатора и подписью
    public CircleSlider(Double centerX, Double centerY, Color color, String name) {
        this.name = name;
        center = new Point2D(centerX, centerY);
        //Создаём невыделенную область слайдера
        circle = new Circle(center.getX(), center.getY(), RADIUS, Color.TRANSPARENT);
        circle.setStroke(Color.DARKGREY);
        circle.setStrokeWidth(4.0);
        //Создаём индикатор
        arc = new Arc(center.getX(), center.getY(), RADIUS, RADIUS, 0, 0);
        arc.setStroke(color);
        arc.setStrokeWidth(4.0);
        arc.setStrokeLineCap(StrokeLineCap.ROUND);
        arc.setFill(Color.AZURE);
        //Создаём бегунок
        line = new Line();
        line.setStroke(Color.DARKGREY);
        line.setStrokeWidth(5);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        setLinePosition(0);
        //Создаём подпись
        title = new Label(name);
        title.setTranslateX(center.getX() - RADIUS);
        title.setTranslateY(center.getY() - (RADIUS + 35));
        //Формируем готовый слайдер
        area = new Group(circle, arc, line, title);
        area.setOnMouseDragged(this::handleMouseDragged);
    }
    //Как только пользователь двигает мышью при зажатой ЛКМ...
    private void handleMouseDragged(MouseEvent event) {
        //Посчитать угол на который пользователь подвинул
        angle = Math.atan2(event.getY() - center.getY(), event.getX() - center.getX());
        angle = Math.toDegrees(angle);
        angle = (angle + 360) % 360;
        setLinePosition(angle); //Установить бегунок в это место
    }

    private void setLinePosition(double angle) {
        arc.setLength(-angle);
        double x = center.getX() + RADIUS * Math.cos(Math.toRadians(angle));
        double y = center.getY() + RADIUS * Math.sin(Math.toRadians(angle));
        line.setStartX(center.getX());
        line.setStartY(center.getY());
        line.setEndX(x);
        line.setEndY(y);
    }

    public Group getArea() {
        return area;
    }

    public Double getAngle() {
        return angle;
    }
}
