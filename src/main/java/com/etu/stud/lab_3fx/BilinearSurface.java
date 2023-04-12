package com.etu.stud.lab_3fx;
import com.etu.stud.lab_3fx.window.elements.CoordinateArea;
import javafx.application.Application;


import javafx.stage.Stage;


public class BilinearSurface extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new CoordinateArea()); //Создать приложение
        stage.setResizable(false); //Запретить растягивать окно
        stage.show(); //И показать его пользователю.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
