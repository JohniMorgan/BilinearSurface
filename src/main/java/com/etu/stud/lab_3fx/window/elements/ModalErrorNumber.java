package com.etu.stud.lab_3fx.window.elements;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/*------------------------------------------
Это класс модального окна, повяляющегося
при неверном значении в таблицу точек.
Вызывается автоматически и служит сигналом
Что пользователь ввёл некорректные данные
 --------------------------------------------*/
public class ModalErrorNumber extends Stage {

    PointTable parent;
    public ModalErrorNumber(PointTable parent) {
        super();
        this.parent = parent;
        //Установка размеров окна
        this.setMaxWidth(400);
        this.setMaxHeight(150);
        this.setMinHeight(150);
        this.setMinWidth(400);
        //Инициализация окна как модального
        this.initModality(Modality.APPLICATION_MODAL);
        BorderPane p = new BorderPane();
        p.setCenter(new Label("Введённое значение должно являтся вещественным числом"));
        this.setTitle("Ошибка записи числа");
        //Вывод окна пользователю
        this.setScene(new Scene(p));
    }
    public void activate() {
        //Обновить таблицу, для отображения актуальных данных
        //По сути - стереть новый ввод пользователя.
        parent.getTable().refresh();
        //Вызывать окно
        this.show();
    }
}
