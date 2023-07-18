// module
module wgu {
    requires javafx.controls;
    requires javafx.fxml;

    opens wgu to javafx.fxml;
    exports wgu;

    opens wgu.controller to javafx.fxml;
    exports wgu.controller;

    opens wgu.model to javafx.fxml;
    exports wgu.model;

}
