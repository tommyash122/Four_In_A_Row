module com.example.fourinarow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.fourinarow to javafx.fxml;
    exports com.example.fourinarow;
}