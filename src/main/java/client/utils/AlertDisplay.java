package client.utils;

import javafx.scene.control.Alert;

import java.text.MessageFormat;
import java.util.MissingResourceException;

public class AlertDisplay {

    private static LocalizationTool localizationTool;

    private static void msg(String title, String toOut, String[] args, Alert.AlertType msgType) {
        Alert alert = new Alert(msgType);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(localize(toOut, args));
        alert.showAndWait();
    }

    public static void showError(String error) {
        msg("Error!", error, null, Alert.AlertType.ERROR);
    }

    public static void showInfo(String info) {
        msg("Info.", info, null, Alert.AlertType.INFORMATION);
    }

    private static String localize(String str, String[] args) {
        try {
            if (args == null) return localizationTool.getResources().getString(str);
            MessageFormat messageFormat = new MessageFormat(localizationTool.getResources().getString(str));
            return messageFormat.format(args);
        } catch (MissingResourceException | NullPointerException exception) {
            return str;
        }
    }

    public static void setLocalizationTool(LocalizationTool tool) {
        localizationTool = tool;
    }
}
