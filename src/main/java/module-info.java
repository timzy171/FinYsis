module com.example.finysis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;

    requires org.controlsfx.controls;
    requires org.hibernate.orm.core;
    requires jakarta.activation;
    requires persistence.api;
    requires jakarta.persistence;
    requires javax.mail.api;

//    opens hibernate.entity to javafx.fxml;
    exports hibernate.entity;

    opens hibernate.entity to org.hibernate.orm.core;

    opens com.example.finysis to javafx.fxml;
    exports com.example.finysis;
    exports services;
    opens services to javafx.fxml;
}