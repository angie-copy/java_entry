module org.example.demo4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.demo4 to javafx.fxml;
    exports org.example.demo4;
}