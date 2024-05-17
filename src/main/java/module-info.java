module com.heshanthenura.easyserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;


    opens com.heshanthenura.easyserver to javafx.fxml;
    exports com.heshanthenura.easyserver;
}