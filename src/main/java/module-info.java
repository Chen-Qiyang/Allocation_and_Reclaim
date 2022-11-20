module com.example.allocation_and_reclaim {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.allocation_and_reclaim to javafx.fxml;
    exports com.example.allocation_and_reclaim;
    opens model.Bean to javafx.base;
}