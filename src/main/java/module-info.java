module org.example.hormonika {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.hormonika to javafx.fxml;
    exports org.example.hormonika;
}