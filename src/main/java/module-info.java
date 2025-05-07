module sample.hustbookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.j;
    requires cloudinary.http5;
    requires cloudinary.core;
    requires dotenv.java;
    requires fontawesomefx;

    opens sample.hustbookstore to javafx.fxml;
    exports sample.hustbookstore;
    exports sample.hustbookstore.models;
    opens sample.hustbookstore.models to javafx.fxml;
    exports sample.hustbookstore.utils;
    opens sample.hustbookstore.utils to javafx.fxml;
    exports sample.hustbookstore.controllers.admin;
    opens sample.hustbookstore.controllers.admin to javafx.fxml;
    exports sample.hustbookstore.controllers.user;
    opens sample.hustbookstore.controllers.user to javafx.fxml;
}