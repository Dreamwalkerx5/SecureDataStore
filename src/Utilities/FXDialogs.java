package Utilities;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class FXDialogs {

    public static void errorDialog(String title, String header, String content) {
        doDialog(Alert.AlertType.ERROR, title, header, content);
    }

    public static void informationDialog(String title, String header, String content) {
        doDialog(Alert.AlertType.INFORMATION, title, header, content);
    }

    public static void blankDialog(String title, String header, String content) {
        doDialog(Alert.AlertType.NONE, title, header, content);
    }

    public static void warningDialog(String title, String header, String content) {
        doDialog(Alert.AlertType.WARNING, title, header, content);
    }

    public static String passwordDialog(String title, String header) {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //TextField username = new TextField();
        //username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        //grid.add(new Label("Username:"), 0, 0);
        //grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);



        // Do some validation (using the Java 8 lambda syntax).
        /*username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });*/

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> password.requestFocus());

        // This must be set. It defines what should happen when a button is pressed ie what to return
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return  password.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){
            return result.get();
        }else {
            return "";
        }

    }

    public static boolean confirmationDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        // ... user chose OK
// ... user chose CANCEL or closed the dialog
        return result.get() == ButtonType.OK;
    }

    public static int twoOptionDialog(String title, String header, String content, String button1, String button2) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType buttonTypeOne = new ButtonType(button1);
        ButtonType buttonTypeTwo = new ButtonType(button2);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            return 1;
        } else if (result.get() == buttonTypeTwo) {
            return 2;
        } else {
            return 0;
        }
    }

    private static void doDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
