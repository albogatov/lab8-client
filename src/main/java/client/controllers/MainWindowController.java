package client.controllers;

import client.Client;
import client.WorkerApp;
import client.utils.CollectionRefresher;
import client.utils.LocalizationTool;

import commons.elements.*;
import javafx.animation.ScaleTransition;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class MainWindowController {

    private Client client;

    private WorkerApp workerApp;

    private ObservableList<Worker> data;

    private PopUpWindowController popUpWindowController;

    private CollectionRefresher collectionRefresher;

    private Stage popUpStage;

    private Map<String, Color> userColorMap;

    private Map<Shape, Long> shapeMap;

    private Map<Long, Text> textMap;

    private Map<String, Locale> localeMap;

    private Shape prevClicked;

    private Color prevColor;

    private Random randomGenerator;

    private LocalizationTool localizationTool;

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
        localeMap = new HashMap<>();
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("Shqiptare", new Locale("sq", "AL"));
        localeMap.put("Slovák", new Locale("sk", "SK"));
        localeMap.put("English (Canada)", new Locale("en", "CA"));
        langChoiceComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        shapeMap = new HashMap<>();
        textMap = new HashMap<>();
        userColorMap = new HashMap<>();
        randomGenerator = new Random();
        getTable();
    }

    public void getTable() {
        workerTableView.setItems(data);
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
//            data = FXCollections.observableArrayList(collectionRefresher.getCollection());
//            getTable();
            requestCommand("show");
            visualise();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void visualise() {
        shapeMap.keySet().forEach(s -> visualMapPane.getChildren().remove(s));
        shapeMap.clear();
        textMap.values().forEach(s -> visualMapPane.getChildren().remove(s));
        textMap.clear();
        for (Worker worker : workerTableView.getItems()) {
            if (!userColorMap.containsKey(worker.getUsername()))
                userColorMap.put(worker.getUsername(),
                        Color.color(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble()));

            double size = Math.min(worker.getSalary(), 250);

            Shape circleObject = new Circle(size, userColorMap.get(worker.getUsername()));
            circleObject.setOnMouseClicked(this::shapeOnMouseClicked);
            circleObject.translateXProperty().bind(visualMapPane.widthProperty().divide(2).add(worker.getCoordinateX()));
            circleObject.translateYProperty().bind(visualMapPane.heightProperty().divide(2).subtract(worker.getCoordinateY()));

            Text textObject = new Text(String.valueOf(worker.getId()));
            textObject.setOnMouseClicked(circleObject::fireEvent);
            textObject.setFont(Font.font(size / 3));
            textObject.setFill(userColorMap.get(worker.getUsername()).darker());
            textObject.translateXProperty().bind(circleObject.translateXProperty().subtract(textObject.getLayoutBounds().getWidth() / 2));
            textObject.translateYProperty().bind(circleObject.translateYProperty().add(textObject.getLayoutBounds().getHeight() / 4));

            visualMapPane.getChildren().add(circleObject);
            visualMapPane.getChildren().add(textObject);
            shapeMap.put(circleObject, worker.getId());
            textMap.put(worker.getId(), textObject);

            ScaleTransition circleAnimation = new ScaleTransition(Duration.millis(1000), circleObject);
            ScaleTransition textAnimation = new ScaleTransition(Duration.millis(1000), textObject);
            circleAnimation.setFromX(0);
            circleAnimation.setToX(1);
            circleAnimation.setFromY(0);
            circleAnimation.setToY(1);
            textAnimation.setFromX(0);
            textAnimation.setToX(1);
            textAnimation.setFromY(0);
            textAnimation.setToY(1);
            circleAnimation.play();
            textAnimation.play();
        }
    }

    /**
     * Shape on mouse clicked.
     */
    private void shapeOnMouseClicked(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        long id = shapeMap.get(shape);
        for (Worker worker : workerTableView.getItems()) {
            if (worker.getId() == id) {
                workerTableView.getSelectionModel().select(worker);
                break;
            }
        }
        if (prevClicked != null) {
            prevClicked.setFill(prevColor);
        }
        prevClicked = shape;
        prevColor = (Color) shape.getFill();
        shape.setFill(prevColor.brighter());
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
//        try {
        HashSet<Worker> result = client.processRequestFromUser(command, argument, object);
        if (result != null) {
            System.out.println(result);
            workerTableView.setItems(FXCollections.observableArrayList(result));
            workerTableView.getSelectionModel().clearSelection();
        }
//            Thread.sleep(1000);
//            data = FXCollections.observableArrayList(collectionRefresher.getCollection());
//            getTable();
//        } catch (InterruptedException e) {
//
//        }
    }

    public void setResources(LocalizationTool localizationTool) {
        this.localizationTool = localizationTool;
        for (String localeName : localeMap.keySet()) {
            if (localeMap.get(localeName).equals(localizationTool.getResources().getLocale()))
                langChoiceComboBox.getSelectionModel().select(localeName);
        }
        if (langChoiceComboBox.getSelectionModel().getSelectedItem().isEmpty())
            langChoiceComboBox.getSelectionModel().selectFirst();
        langChoiceComboBox.setOnAction((action) -> {
            localizationTool.setResources(ResourceBundle.getBundle("bundles.ui",
                    localeMap.get(langChoiceComboBox.getValue())));
        });
        bindLanguage();
    }

    public void bindLanguage() {
        localizationTool.setResources(ResourceBundle.getBundle("bundles.ui",
                localeMap.get(langChoiceComboBox.getSelectionModel().getSelectedItem())));
        idColumn.textProperty().bind(localizationTool.getStringBinding("IdColumn"));
        nameColumn.textProperty().bind(localizationTool.getStringBinding("NameColumn"));
        xColumn.textProperty().bind(localizationTool.getStringBinding("XColumn"));
        yColumn.textProperty().bind(localizationTool.getStringBinding("YColumn"));
        salaryColumn.textProperty().bind(localizationTool.getStringBinding("SalaryColumn"));
        endDateColumn.textProperty().bind(localizationTool.getStringBinding("EndDateColumn"));
        creationDateColumn.textProperty().bind(localizationTool.getStringBinding("CreationDateColumn"));
        positionColumn.textProperty().bind(localizationTool.getStringBinding("PositionColumn"));
        statusColumn.textProperty().bind(localizationTool.getStringBinding("StatusColumn"));
        orgColumn.textProperty().bind(localizationTool.getStringBinding("OrgColumn"));
        orgTypeColumn.textProperty().bind(localizationTool.getStringBinding("OrgTypeColumn"));
        annualTurnoverColumn.textProperty().bind(localizationTool.getStringBinding("AnnualTurnoverColumn"));
        streetColumn.textProperty().bind(localizationTool.getStringBinding("StreetColumn"));
        postalCodeColumn.textProperty().bind(localizationTool.getStringBinding("PostalCodeColumn"));
        userColumn.textProperty().bind(localizationTool.getStringBinding("UserColumn"));
        refreshButton.textProperty().bind(localizationTool.getStringBinding("RefreshButton"));
        addButton.textProperty().bind(localizationTool.getStringBinding("AddButton"));
        updateButton.textProperty().bind(localizationTool.getStringBinding("UpdateButton"));
        removeButton.textProperty().bind(localizationTool.getStringBinding("RemoveButton"));
        removeGreaterButton.textProperty().bind(localizationTool.getStringBinding("RemoveGreaterButton"));
        removeLowerButton.textProperty().bind(localizationTool.getStringBinding("RemoveLowerButton"));
        clearButton.textProperty().bind(localizationTool.getStringBinding("ClearButton"));
        helpButton.textProperty().bind(localizationTool.getStringBinding("HelpButton"));
        infoButton.textProperty().bind(localizationTool.getStringBinding("InfoButton"));
        countByStatusButton.textProperty().bind(localizationTool.getStringBinding("CountByStatusButton"));
        printUniqueOrgsButton.textProperty().bind(localizationTool.getStringBinding("PrintUniqueOrgsButton"));
        addIfMinButton.textProperty().bind(localizationTool.getStringBinding("AddIfMinButton"));
        saveButton.textProperty().bind(localizationTool.getStringBinding("SaveButton"));
        executeScriptButton.textProperty().bind(localizationTool.getStringBinding("ExecuteScriptButton"));
        visualMapTab.textProperty().bind(localizationTool.getStringBinding("VisualMapTab"));
        dataTableTab.textProperty().bind(localizationTool.getStringBinding("DataTableTab"));
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
