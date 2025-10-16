module org.example.finaldivar {
    requires javafx.controls;
    requires javafx.fxml;
    opens org.example.finaldivar.controller to javafx.fxml;
    exports org.example.finaldivar;
}