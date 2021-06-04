package client;

import client.controllers.AuthWindowController;
import commons.elements.Worker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkerApp extends Application {

    private Stage primaryStage;

    private static Client client;

    public static void main(String[] args) throws IOException {
        if (initializeClient(args))
            launch(args);
        else System.exit(-1);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader authWindowLoader = new FXMLLoader(Worker.class.getResource("/authWindow.fxml"));
        Parent authWindowRootNode = authWindowLoader.load();
        Scene authWindowScene = new Scene(authWindowRootNode);
        AuthWindowController authWindowController = authWindowLoader.getController();
        authWindowController.setWorkerApp(this);
        authWindowController.setClient(client);
        primaryStage.setScene(authWindowScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void startMainWindow() throws IOException {
        FXMLLoader mainWindowLoader = new FXMLLoader(Worker.class.getResource("/mainWindow.fxml"));
        Parent mainWindowRootNode = mainWindowLoader.load();
        Scene mainWindowScene = new Scene(mainWindowRootNode);
        AuthWindowController mainWindowController = mainWindowLoader.getController();
        primaryStage.setScene(mainWindowScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static boolean initializeClient(String[] args) throws IOException {
        client = new Client();
        try {
            if (client.connect(args[0], Integer.parseInt(args[1])))
                return true;
            else return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
}
