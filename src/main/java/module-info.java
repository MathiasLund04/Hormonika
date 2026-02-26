module org.example.hormonika {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.example.hormonika;


    opens org.example.hormonika to javafx.fxml;
    exports org.example.hormonika;
    exports Model;
    opens Model to javafx.fxml;
    exports DAL;
    opens DAL to javafx.fxml;
}