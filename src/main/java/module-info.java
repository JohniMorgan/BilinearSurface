module com.etu.stud.lab_3fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.etu.stud.lab_3fx to javafx.fxml;
    exports com.etu.stud.lab_3fx;
}