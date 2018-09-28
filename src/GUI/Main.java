package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Controller;

public class Main extends Application {
    private static Controller controller;

    @Override
    public void stop() {
        controller.shutdownDataBase();
        System.out.println("Exit");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Data Store v4.0 Copyright 2018 Steven Taylor");
        primaryStage.setScene(new Scene(root));
        // Disable close window
        //primaryStage.setOnCloseRequest(e->e.consume());
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setController(Controller currentController){

        // Get Model Controller from GUIController so we can do a controlled shutdown from here!
        controller = currentController;
    }
}
