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
    requires java.net.http;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires VnCoreNLP;
    requires org.apache.lucene.queryparser;
    requires org.apache.lucene.core;

    opens sample.hustbookstore to javafx.fxml;
    exports sample.hustbookstore;
    exports sample.hustbookstore.models;
    opens sample.hustbookstore.models to javafx.fxml;

    exports sample.hustbookstore.controllers.admin;
    opens sample.hustbookstore.controllers.admin to javafx.fxml;
    exports sample.hustbookstore.controllers.user;
    opens sample.hustbookstore.controllers.user to javafx.fxml;
    exports sample.hustbookstore.controllers.base;
    opens sample.hustbookstore.controllers.base to javafx.fxml;
    exports sample.hustbookstore.models.address;
    exports sample.hustbookstore.utils.cloud;
    opens sample.hustbookstore.utils.cloud to javafx.fxml;
    exports sample.hustbookstore.utils.recommendSystem;
    opens sample.hustbookstore.utils.recommendSystem to javafx.fxml;

}