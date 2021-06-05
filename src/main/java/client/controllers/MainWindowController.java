package client.controllers;

import client.Client;
import client.WorkerApp;
import client.utils.CollectionRefresher;
import com.sun.org.apache.regexp.internal.RE;
import commons.elements.*;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;

public class MainWindowController {

    Client client;

    WorkerApp workerApp;

    ObservableList<Worker> data;

    private PopUpWindowController popUpWindowController;

    private CollectionRefresher collectionRefresher;

    private Stage popUpStage;

    private String ADD = "add";

    private String UPDATE = "update";

    private String REMOVE_GREATER = "remove_greater";

    private String REMOVE_LOWER = "remove_lower";

    private String REMOVE_BY_ID = "remove_by_id";

    private String SAVE_TO_FILE = "save";

    private String EXECUTE_SCRIPT = "execute_script";

    private String HELP = "help";

    private String INFO = "info";

    private String COUNT_BY_STATUS = "count_by_status";

    private String CLEAR = "clear";

    private String PRINT_UNIQUE_ORGS = "print_unique_organization";

    private String ADD_IF_MIN = "add_if_min";

    @FXML
    private TableView<Worker> workerTableView;

    @FXML
    private TableColumn<Worker, Long> idColumn;

    @FXML
    private TableColumn<Worker, String> nameColumn;

    @FXML
    private TableColumn<Worker, Integer> xColumn;

    @FXML
    private TableColumn<Worker, Long> yColumn;

    @FXML
    private TableColumn<Worker, Integer> salaryColumn;

    @FXML
    private TableColumn<Worker, String> endDateColumn;

    @FXML
    private TableColumn<Worker, String> creationDateColumn;

    @FXML
    private TableColumn<Worker, Position> positionColumn;

    @FXML
    private TableColumn<Worker, Status> statusColumn;

    @FXML
    private TableColumn<Worker, String> orgColumn;

    @FXML
    private TableColumn<Worker, OrganizationType> orgTypeColumn;

    @FXML
    private TableColumn<Worker, Long> annualTurnoverColumn;

    @FXML
    private TableColumn<Worker, String> streetColumn;

    @FXML
    private TableColumn<Worker, String> postalCodeColumn;

    @FXML
    private TableColumn<Worker, String> userColumn;

    @FXML
    private AnchorPane visualMapPane;

    @FXML
    private Button refreshButton;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button removeGreaterButton;

    @FXML
    private Button removeLowerButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button countByStatusButton;

    @FXML
    private Button printUniqueOrgsButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button addIfMinButton;

    @FXML
    private Button executeScriptButton;

    @FXML
    private ComboBox<String> langChoiceComboBox;

    @FXML
    private TextFlow currentUserTextFlow;

    @FXML
    private Tab visualMapTab;

    @FXML
    private Tab dataTableTab;

    public MainWindowController() {

    }

    public void setData(HashSet<Worker> workers) {
        data = FXCollections.observableArrayList(workers);
    }

    public void init() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        xColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinateX()));
        yColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinateY()));
        salaryColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSalary()));
        endDateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEndDateString()));
        creationDateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDateString()));
        positionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPosition()));
        statusColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStatus()));
        orgColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getOrganizationName()));
        orgTypeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getOrganizationType()));
        annualTurnoverColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAnnualTurnover()));
        streetColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAddressStreet()));
        postalCodeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAddressZipCode()));
        userColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUsername()));
        getTable();
    }

    public void getTable() {
        System.out.println("refreshing");
        workerTableView.setItems(data);
//        workerTableView.getColumns().add(idColumn);
//        workerTableView.getColumns().add(nameColumn);
//        workerTableView.getColumns().add(xColumn);
//        workerTableView.getColumns().add(yColumn);
//        workerTableView.getColumns().add(salaryColumn);
//        workerTableView.getColumns().add(endDateColumn);
//        workerTableView.getColumns().add(creationDateColumn);
//        workerTableView.getColumns().add(positionColumn);
//        workerTableView.getColumns().add(statusColumn);
//        workerTableView.getColumns().add(orgColumn);
//        workerTableView.getColumns().add(orgTypeColumn);
//        workerTableView.getColumns().add(annualTurnoverColumn);
//        workerTableView.getColumns().add(streetColumn);
//        workerTableView.getColumns().add(postalCodeColumn);
//        workerTableView.getColumns().add(userColumn);
    }

    @FXML
    public void addButtonOnClick() {
        popUpWindowController.clear();
        popUpStage.showAndWait();
        Worker worker = popUpWindowController.getResult();
        if (worker != null)
            requestCommand(ADD, worker);
    }

    @FXML
    public void updateButtonOnClick() {
        if (!workerTableView.getSelectionModel().isEmpty()) {
            popUpWindowController.prepareForUpdate(workerTableView.getSelectionModel().getSelectedItem());
            long id = workerTableView.getSelectionModel().getSelectedItem().getId();
            popUpStage.showAndWait();
            Worker worker = popUpWindowController.getResult();
            if (worker != null)
                requestCommand(UPDATE, id + "", worker);
        }
    }

    @FXML
    public void removeButtonOnClick() {
        if (!workerTableView.getSelectionModel().isEmpty()) {
            Worker worker = workerTableView.getSelectionModel().getSelectedItem();
            requestCommand(REMOVE_BY_ID, worker.getId() + "");
        }
    }

    @FXML
    public void removeGreaterButtonOnClick() {
        if (!workerTableView.getSelectionModel().isEmpty()) {
            Worker worker = workerTableView.getSelectionModel().getSelectedItem();
            requestCommand(REMOVE_GREATER, worker);
        }
    }

    @FXML
    public void removeLowerButtonOnClick() {
        if (!workerTableView.getSelectionModel().isEmpty()) {
            Worker worker = workerTableView.getSelectionModel().getSelectedItem();
            requestCommand(REMOVE_LOWER, worker);
        }
    }

    @FXML
    public void addIfMinButtonOnClick() {
        popUpWindowController.clear();
        popUpStage.showAndWait();
        Worker worker = popUpWindowController.getResult();
        if (worker != null)
            requestCommand(ADD_IF_MIN, worker);
    }

    @FXML
    public void clearButtonOnClick() {
        requestCommand(CLEAR);
    }

    @FXML
    public void executeScriptButtonOnClick() {

    }

    @FXML
    public void infoButtonOnClick() {
        requestCommand(INFO);
    }

    @FXML
    public void helpButtonOnClick() {
        requestCommand(HELP);
    }

    @FXML
    public void countByStatusButtonOnClick() {
        requestCommand(COUNT_BY_STATUS);
    }

    @FXML
    public void saveButtonOnClick() {
        requestCommand(SAVE_TO_FILE);
    }

    @FXML
    public void printUniqueOrgsButtonOnClick() {
        requestCommand(PRINT_UNIQUE_ORGS);
    }

    @FXML
    public void refreshButtonOnClick() {
        try {
            data = FXCollections.observableArrayList(collectionRefresher.getCollection());
            getTable();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void requestCommand(String command, Worker object) {
        requestCommand(command, "", object);
    }

    public void requestCommand(String command) {
        requestCommand(command, "", null);

    }

    public void requestCommand(String command, String argument) {
        requestCommand(command, argument, null);
    }

    public void requestCommand(String command, String argument, Worker object) {
//        Thread.sleep(100);
        try {
            client.processRequestFromUser(command, argument, object);
            Thread.sleep(1000);
            data = FXCollections.observableArrayList(collectionRefresher.getCollection());
            getTable();
        } catch (InterruptedException e) {

        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setWorkerApp(WorkerApp app) {
        this.workerApp = app;
    }

    public void setCollectionRefresher(CollectionRefresher collectionRefresher) {
        this.collectionRefresher = collectionRefresher;
    }

    public void setPopUpWindowController(PopUpWindowController controller) {
        this.popUpWindowController = controller;
        this.popUpStage = controller.getDisplayStage();
    }
}
