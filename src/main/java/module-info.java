module com.testeprotheralb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens model to org.hibernate.orm.core, javafx.base;
    opens application to javafx.fxml;

    exports application;
    exports model;
}