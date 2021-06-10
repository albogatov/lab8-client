package client;

import client.controllers.AuthWindowController;
import client.controllers.MainWindowController;
import client.controllers.PopUpWindowController;
import client.utils.AlertDisplay;
import client.utils.LocalizationTool;
import commons.elements.Worker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.ResourceBundle;

public class WorkerApp extends Application {

    private Stage primaryStage;

    private static Client client;

    private static LocalizationTool localizationTool;

    public static void main(String[] args) throws IOException {
        localizationTool = new LocalizationTool();
        localizationTool.setResources(ResourceBundle.getBundle("client.bundles.gui"));
        AlertDisplay.setLocalizationTool(localizationTool);
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
        authWindowController.initLangs(localizationTool);
        primaryStage.setScene(authWindowScene);
        primaryStage.setTitle("Worker App");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void startMainWindow() throws IOException {
        client.initCollection();
        HashSet<Worker> workers = client.getCollection();

        FXMLLoader mainWindowLoader = new FXMLLoader(WorkerApp.class.getResource("/mainWindow.fxml"));
        Parent mainWindowRootNode = mainWindowLoader.load();
        Scene mainWindowScene = new Scene(mainWindowRootNode);
        MainWindowController mainWindowController = mainWindowLoader.getController();
        mainWindowController.setWorkerApp(this);
        mainWindowController.setClient(client);
        mainWindowController.setCollectionRefresher(client.getCollectionRefresher());
        mainWindowController.setData(workers);
        mainWindowController.init();
        mainWindowController.setResources(localizationTool);

        FXMLLoader popUpWindowLoader = new FXMLLoader(WorkerApp.class.getResource("/PopUpWindow.fxml"));
        Parent popUpWindowRootNode = popUpWindowLoader.load();
        Scene popUpWindowScene = new Scene(popUpWindowRootNode);
        Stage popUpStage = new Stage();
        popUpStage.setScene(popUpWindowScene);
        PopUpWindowController popUpWindowController = popUpWindowLoader.getController();
        popUpWindowController.setClient(client);
        popUpWindowController.setDisplayStage(popUpStage);
        popUpWindowController.initLangs(localizationTool);
        popUpStage.setResizable(false);
        popUpStage.setTitle("Worker App");

        mainWindowController.setPopUpWindowController(popUpWindowController);
        primaryStage.setScene(mainWindowScene);
        primaryStage.setResizable(false);
        Thread update = new Thread(() -> {
            try {
                while (true) {
                    client.processRequestFromUser("show", "", null);
                    Platform.runLater(() -> mainWindowController.visualise());
                    Thread.sleep(60000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Platform.exit();
            }
        });
        update.start();
        primaryStage.show();
    }

    public static boolean initializeClient(String[] args) throws IOException {
        client = new Client();
        client.setCollectionRefresher(client);
        client.setAlertDisplay();
        try {
            if (client.connect(args[0], Integer.parseInt(args[1]))) {
                client.setHost(args[0]);
                client.setPort(Integer.parseInt(args[1]));
                return true;
            } else return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

}
