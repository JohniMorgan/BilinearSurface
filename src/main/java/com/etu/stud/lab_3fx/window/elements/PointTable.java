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
        table.setEditable(true);
        table.setDisable(false);
        xColumn.setPrefWidth(130);
        xColumn.setSortable(false);
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        xColumn.setCellFactory(TextFieldTableCell.<AccessPoint3D>forTableColumn());
        xColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AccessPoint3D, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AccessPoint3D, String> event) {
                try {
                    Double.valueOf(event.getNewValue());
                    ((AccessPoint3D) event.getTableView().getItems().get(
                            event.getTablePosition().getRow()
                    )).setX(event.getNewValue());
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
        yColumn.setPrefWidth(130);
        yColumn.setSortable(false);
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
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
        zColumn.setPrefWidth(130);
        zColumn.setSortable(false);
        zColumn.setCellValueFactory(new PropertyValueFactory<>("z"));
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
        table.setFixedCellSize(30);
        table.setMaxSize(395, 147);
        table.setMinSize(395, 147);
        table.setItems(data);
        table.getColumns().addAll(xColumn, yColumn, zColumn);

        SimpleDoubleProperty cell = new SimpleDoubleProperty(0.0);
    }

    public TableView<AccessPoint3D> getTable() {
        return table;
    }
}
