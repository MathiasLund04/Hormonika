module org.example.hormonika {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.hormonika to javafx.fxml;
    exports org.example.hormonika;
}