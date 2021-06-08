package client.utils;

import javafx.scene.control.Alert;

public class AlertDisplay {

    private static void msg(String title, String toOut, String[] args, Alert.AlertType msgType) {
        Alert alert = new Alert(msgType);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(toOut);
//        alert.setContentText(tryResource(toOut, args));
        alert.showAndWait();
    }

    public static void showError(String error) {
        msg("Error!", error, null, Alert.AlertType.ERROR);
    }

    public static void showInfo(String info) {
        msg("Info.", info, null, Alert.AlertType.INFORMATION);
    }
}
