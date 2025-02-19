module src {
    requires javafx.controls;
    requires javafx.fxml;


    opens part_1_Arrays to javafx.fxml;
    exports part_1_Arrays;
    opens part_2_layouts to javafx.fxml;
    exports part_2_layouts;
    opens part_3_application to javafx.fxml;
    exports part_3_application;
    opens part_4_fileApp to javafx.fxml;
    exports part_4_fileApp;
}