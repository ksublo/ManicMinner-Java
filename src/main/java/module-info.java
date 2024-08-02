module java1.blo0021 {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    opens lab to javafx.fxml;
    exports lab;
}