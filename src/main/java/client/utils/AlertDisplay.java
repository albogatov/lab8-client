package client.utils;

import javafx.scene.control.Alert;

public class AlertDisplay {

    private static void msg(String title, String toOut, String[] args, Alert.AlertType msgType) {
        Alert alert = new Alert(msgType);
        alert.setTitle(title);
        alert.setHeaderText(null);
//        alert.setContentText(tryResource(toOut, args));
        alert.showAndWait();
    }

    public void showError(String error) {

    }

    public void showInfo(String info) {

    }
}
