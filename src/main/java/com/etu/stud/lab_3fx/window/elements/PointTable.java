package com.etu.stud.lab_3fx.window.elements;

import com.etu.stud.lab_3fx.AccessPoint3D;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/*----------------------------------------------------------
Таблица данных точек. В неё пользователь
Вводит данные четырех опорных точек
Изменение данных можно осуществить по двойному
Нажатию на ячейку таблицы.
                !!!Важно!!!
Редактирование необходимо подтвердить нажатием
Клавиши Enter. В противном случае данные
 не будут записаны
 -----------------------------------------------------------*/
public class PointTable {
    //Представление таблицы
    TableView<AccessPoint3D> table = new TableView<>();
    //Данные внутри таблицы
    ObservableList<AccessPoint3D> data = FXCollections.observableArrayList(
            new AccessPoint3D(),
            new AccessPoint3D(),
            new AccessPoint3D(),
            new AccessPoint3D()
    );
    ModalErrorNumber errMsg;
    //Колонки нашей будущей таблицы
    TableColumn<AccessPoint3D, String> xColumn = new TableColumn<>("Координата X");
    TableColumn<AccessPoint3D, String> yColumn = new TableColumn<>("Координата Y");
    TableColumn<AccessPoint3D, String> zColumn = new TableColumn<>("Координата Z");

    public PointTable(CoordinateArea parent) {
        //Инициализовать и подготовить окно об ошибке
        errMsg = new ModalErrorNumber(this);
        table.setEditable(true); //Разрешить редактировать таблицу
        table.setDisable(false); //Убрать ограничение
        xColumn.setPrefWidth(130); //Определить размеры столбца
        xColumn.setSortable(false); //Отключить сортировку таблицы
        /* !!!---------------------------------------!!!
            Отключение сортировки необходимо, т.к.
            порядок точек в таблице строго определён
           !!!---------------------------------------!!!*/
        //Инициализация столбца координаты X...
        xColumn.setCellValueFactory(new PropertyValueFactory<AccessPoint3D, String>("x"));
        //Сделать ячейки изменяемыми....
        xColumn.setCellFactory(TextFieldTableCell.<AccessPoint3D>forTableColumn());
        //Подписка на событие подтверждения изменения
        xColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AccessPoint3D, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AccessPoint3D, String> event) {
                try { //Попытаться подставить значение в данные
                    Double.valueOf(event.getNewValue());
                    ((AccessPoint3D) event.getTableView().getItems().get(
                            event.getTablePosition().getRow()
                    )).setX(event.getNewValue());
                } catch (NumberFormatException e) {
                    //Неудача - пользователь ввёл НЕ число
                    //Добавление модального окна с предупреждением о типе данных
                    errMsg.activate();
                }
                //Попытаемся нарисовать плоскость
                //Т.н. реактивность данных
                try {
                    parent.printControlPoints();
                } catch (Exception e) {
                    System.out.println(e.getCause());
                }
            }
        });
        //Аналогичная инициализация столбца Y
        yColumn.setPrefWidth(130);
        yColumn.setSortable(false);
        yColumn.setCellValueFactory(new PropertyValueFactory<AccessPoint3D, String>("y"));
        yColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AccessPoint3D, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AccessPoint3D, String> event) {
                try {
                    Double.valueOf(event.getNewValue());
                    ((AccessPoint3D) event.getTableView().getItems().get(
                            event.getTablePosition().getRow()
                    )).setY(event.getNewValue());
                    table.refresh();
                } catch (NumberFormatException e) {
                    //Добавление модального окна с предупреждением о типе данных
                    errMsg.activate();
                }
                try {
                    parent.printControlPoints();
                } catch (Exception e) {
                    System.out.println(e.getCause());
                }
            }
        });
        //Аналогичная настройка столбца Z...
        zColumn.setPrefWidth(130);
        zColumn.setSortable(false);
        zColumn.setCellValueFactory(new PropertyValueFactory<AccessPoint3D, String>("z"));
        zColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        zColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AccessPoint3D, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AccessPoint3D, String> event) {
                try {
                    Double.valueOf(event.getNewValue());
                    ((AccessPoint3D) event.getTableView().getItems().get(
                            event.getTablePosition().getRow()
                    )).setZ(event.getNewValue());
                    table.refresh();
                } catch (NumberFormatException e) {
                    //Добавление модального окна с предупреждением о типе данных
                    errMsg.activate();
                }
                try {
                    parent.printControlPoints();
                } catch (Exception e) {
                    System.out.println(e.getCause());
                }
            }
        });
        //Устанавливаем размеры строчек таблицы
        table.setFixedCellSize(30);
        //Устанавливаем размеры таблицы
        table.setMaxSize(395, 147);
        table.setMinSize(395, 147);
        //Объявляем данные таблицы
        table.setItems(data);
        //Объявляем столбцы таблицы
        table.getColumns().addAll(xColumn, yColumn, zColumn);
    }

    public TableView<AccessPoint3D> getTable() {
        return table;
    }
}
