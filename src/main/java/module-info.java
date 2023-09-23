module org.purejava.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.slf4j;
    requires org.purejava.appindicator;

    opens org.purejava.demo to javafx.fxml;
    exports org.purejava.demo;
}