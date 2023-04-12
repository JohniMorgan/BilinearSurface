package com.etu.stud.lab_3fx;

import javafx.geometry.Point3D;
/*----------------------------------------------
Т.н. "табличный" класс для Point3D. В таблице
хранится строковое представление координат точек
Этот класс затем приводится к реальным координатам
Этот класс лежит в основе таблицы данных
------------------------------------------------ */
public class AccessPoint3D {
    //Строкове представление координат
    String x, y, z;
    //Конструктор по умлочанию
    public AccessPoint3D() {
        this("","", "");
    }
    //Конструктор с параметрами
    public AccessPoint3D(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    //Сеттеры...
    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setZ(String z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "AccessPoint3D {x:" + x + ";y:"+y+";z:"+z+"}";
    }
    //Метод преобразования Строковых координат
    //В координаты реальные. Возвращает
    //Реальную точку в пространстве
    public Point3D castToPoint() {
        Double dx, dy, dz;
        dx = Double.valueOf(x);
        dy = Double.valueOf(y);
        dz = Double.valueOf(z);
        return new Point3D(dx, dy, dz);
    }
}
