package client.controllers;

import client.Client;
import client.WorkerApp;
import javafx.scene.control.Button;

public class MainWindowController {
    Client client;

    WorkerApp workerApp;

    private Button refreshButton;

    private Button addButton;

    private Button updateButton;

    private Button removeButton;

    private Button removeGreaterButton;

    private Button removeLowerButton;

    private Button clearButton;

    private Button helpButton;

    private Button infoButton;

    private Button countByStatusButton;

    private Button printUniqueOrgsButton;

    private Button saveButton;

    private Button addIfMinButton;

    private Button executeScriptButton;

    public MainWindowController() {

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setWorkerApp(WorkerApp app) {
        this.workerApp = app;
    }
}
