package com.example.allocation_and_reclaim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class allocation extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(allocation.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 800);
        stage.setTitle("Allocation_and_Reclaim");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}