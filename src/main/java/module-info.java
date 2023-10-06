module nelson.inventorysystemproject {
    requires javafx.controls;
    requires javafx.fxml;

    opens nelson.inventorysystemproject to javafx.fxml;
    exports nelson.inventorysystemproject;

    opens model to javafx.base;
    exports model;

}