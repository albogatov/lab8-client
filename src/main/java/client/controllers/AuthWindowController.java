package client.controllers;

import client.Client;
import client.WorkerApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthWindowController {
    Client client;

    WorkerApp workerApp;

    //    @FXML
//    private Label loginLabel;
//    @FXML
//    private Label pwdLabel;
    @FXML
    private TextField loginField = new TextField();
    @FXML
    private TextField pwdField = new TextField();
    @FXML
    private Button loginButton = new Button();
    @FXML
    private Button registerButton = new Button();

    public AuthWindowController() {

    }

    @FXML
    public void loginAction() throws IOException {
        if (client.login(loginField.getText(), pwdField.getText())) {
            workerApp.startMainWindow();
        }
    }

    @FXML
    public void registerAction() throws IOException {
        if (client.register(loginField.getText(), pwdField.getText())) {
            workerApp.startMainWindow();
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setWorkerApp(WorkerApp app) {
        this.workerApp = app;
    }
}
