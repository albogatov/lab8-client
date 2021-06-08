package client.controllers;

import client.Client;
import client.WorkerApp;
import client.utils.AlertDisplay;
import client.utils.LocalizationTool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.PortUnreachableException;

public class AuthWindowController {
    private Client client;

    private WorkerApp workerApp;

    private LocalizationTool localizationTool;

    @FXML
    private Label loginLabel = new Label();
    @FXML
    private TextField loginField = new TextField();
    @FXML
    private PasswordField pwdField = new PasswordField();
    @FXML
    private Button loginButton = new Button();
    @FXML
    private Button registerButton = new Button();

    public AuthWindowController() {

    }

    @FXML
    public void loginAction() throws IOException {
        try {
            if (client.login(loginField.getText(), pwdField.getText())) {
                workerApp.startMainWindow();
            }
        } catch (PortUnreachableException e) {
            AlertDisplay.showError("TryLater");
        }
    }

    @FXML
    public void registerAction() throws IOException {
        try {
            if (client.register(loginField.getText(), pwdField.getText())) {
                workerApp.startMainWindow();
            }
        } catch (PortUnreachableException e) {
            AlertDisplay.showError("TryLater");
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setWorkerApp(WorkerApp app) {
        this.workerApp = app;
    }

    public void initLangs(LocalizationTool localizationTool) {
        this.localizationTool = localizationTool;
        loginLabel.textProperty().bind(localizationTool.getStringBinding("LoginLabel"));
        loginField.promptTextProperty().bind(localizationTool.getStringBinding("LoginFieldText"));
        pwdField.promptTextProperty().bind(localizationTool.getStringBinding("PwdFieldText"));
        loginButton.textProperty().bind(localizationTool.getStringBinding("LoginButton"));
        registerButton.textProperty().bind(localizationTool.getStringBinding("RegisterButton"));
    }
}
