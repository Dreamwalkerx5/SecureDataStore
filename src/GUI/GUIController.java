package GUI;

import Utilities.FXDialogs;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Controller;
import model.Model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class GUIController implements Initializable {
    private Preferences prefs;
    private Controller controller;
    private boolean firstRun;
    private List<Model> database;
    private ObservableList<String> list;
    private boolean update = false;
    private int selection = 0;
    @FXML
    private TextField textField;
    @FXML
    private ListView dataList;
    @FXML
    private Button button;

    public void exitApplication() {
        Platform.exit();
    }

    public void eraseDatabase() {
        boolean option = FXDialogs.confirmationDialog("Delete the current Database!!", "Are you sure you want to do this?");
        if (option) {

            // Clear Database
            controller.deleteDataBase();
            controller.firstRun();
            controller.loadDataBase();
            updateDisplay();
        }

    }

    public void about() {
        FXDialogs.informationDialog("About", "Data Store v4.0", "JavaFX version of Data Store. Copyright 2018 Steven Taylor.\n" + "Database size: " + controller.getSize());
    }

    public void addNew() {
        // Check if it's an update or new
        if (update) {
            controller.updateModel(selection, textField.getText());
            update = false;
            button.setText("Add New");
        } else {
            // Add a new model to the database
            controller.addModel(textField.getText());
        }

        // Clear the textField
        textField.setText("");

        // Refresh the display
        controller.loadDataBase();
        updateDisplay();
    }

    public void editEntry() {
        // Check if we are in edit mode
        if (!update) {
            int choice = FXDialogs.twoOptionDialog("Edit or Delete", "Do you want to Delete or Edit the selected entrie?",
                    "Please choose an option", "Edit", "Delete!");
            selection = dataList.getSelectionModel().getSelectedIndex();
            switch (choice) {
                case 2:
                    controller.deleteModel(selection);

                    // Refresh the display
                    controller.loadDataBase();
                    updateDisplay();
                    break;
                case 1:
                    update = true;
                    button.setText("Update");
                    textField.setText(controller.getText(database.get(selection)));
                    break;
                default:
                    break;
            }
        }
    }

    private void updateDisplay() {
        database = controller.getDataBase();

        list.clear();

        for (Model element : database) {
            list.add(controller.getText(element));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create a Preferences Object
        prefs = Preferences.userRoot().node("DataStore v4.0");

        // Load preferences
        firstRun = prefs.getBoolean("firstRun", true);
        // firstRun = true;

        // Create a database controller
        controller = new Controller();
        Main.setController(controller);

        // If first run then create the derby database
        if (firstRun) {
            if (controller.firstRun()) {

                firstRun = false;
                prefs.putBoolean("firstRun", false);

            } else {
                // Error creating derby database
                System.out.println("Error creating database!");
            }
        }

        controller.loadDataBase();
        database = new ArrayList<>();
        list = FXCollections.observableArrayList();
        dataList.setItems(list);
        updateDisplay();
    }

}
