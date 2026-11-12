module br.dev.eloisasantos.dukemarket.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    requires java.sql;

    opens br.dev.eloisasantos.dukemarket.javafx to javafx.fxml;
    opens br.dev.eloisasantos.dukemarket.javafx.controller to javafx.fxml;
    exports br.dev.eloisasantos.dukemarket.javafx;
    
    exports br.dev.eloisasantos.dukemarket.javafx.controller;
    exports br.dev.eloisasantos.dukemarket.javafx.DAO;
    exports br.dev.eloisasantos.dukemarket.javafx.model;
    exports connection;

    requires mysql.connector.java;
}
