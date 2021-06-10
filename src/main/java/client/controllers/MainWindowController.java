package client.controllers;

import client.Client;
import client.WorkerApp;
import client.utils.AlertDisplay;
import client.utils.CollectionRefresher;
import client.utils.LocalizationTool;

import commons.elements.*;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
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

    private Map<Long, Text> infoTextMap;

    private Map<String, Locale> localeMap;

    private Shape prevClicked;

    private Color prevColor;

    private Random randomGenerator;

    private LocalizationTool localizationTool;

    private FileChooser fileChooser;

    private Stage primaryStage;

    private String ADD = "add";

    private String UPDATE = "update";

    private String REMOVE_GREATER = "remove_greater";

    private String REMOVE_LOWER = "remove_lower";

    private String REMOVE_BY_ID = "remove_by_id";

//    private String SAVE_TO_FILE = "save";

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

//    @FXML
//    private Button saveButton;

    @FXML
    private Button addIfMinButton;

    @FXML
    private Button executeScriptButton;

    @FXML
    private ComboBox<String> langChoiceComboBox;

    @FXML
    private Label currentUserLabel;

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
        infoTextMap = new HashMap<>();
        userColorMap = new HashMap<>();
        randomGenerator = new Random();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        getTable();
        setTableAction();
        langChoiceComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    localizationTool.setResources(ResourceBundle.getBundle("client.bundles.gui", localeMap.get(newValue)));
                    bindLanguage();
                    if (popUpWindowController != null)
                        popUpWindowController.initLangs(localizationTool);
                    AlertDisplay.setLocalizationTool(localizationTool);
                }
        );
    }

    public void getTable() {
        workerTableView.setItems(data);
    }

    @FXML
    public void addButtonOnClick() {
        localizePopUpWindow();
        popUpWindowController.clear();
        popUpStage.showAndWait();
        Worker worker = popUpWindowController.getResult();
        if (worker != null)
            requestCommand(ADD, worker);
    }

    @FXML
    public void updateButtonOnClick() {
        localizePopUpWindow();
        if (!workerTableView.getSelectionModel().isEmpty()) {
            popUpWindowController.prepareForUpdate(workerTableView.getSelectionModel().getSelectedItem());
            long id = workerTableView.getSelectionModel().getSelectedItem().getId();
            popUpStage.showAndWait();
            Worker worker = popUpWindowController.getResult();
            if (worker != null)
                requestCommand(UPDATE, id + "", worker);
        } else {
            AlertDisplay.showError("ObjectNotChosen");
        }
    }

    @FXML
    public void removeButtonOnClick() {
        if (!workerTableView.getSelectionModel().isEmpty()) {
            Worker worker = workerTableView.getSelectionModel().getSelectedItem();
            requestCommand(REMOVE_BY_ID, worker.getId() + "");
        } else {
            AlertDisplay.showError("ObjectNotChosen");
        }
    }

    @FXML
    public void removeGreaterButtonOnClick() {
        if (!workerTableView.getSelectionModel().isEmpty()) {
            Worker worker = workerTableView.getSelectionModel().getSelectedItem();
            requestCommand(REMOVE_GREATER, worker);
        } else {
            AlertDisplay.showError("ObjectNotChosen");
        }
    }

    @FXML
    public void removeLowerButtonOnClick() {
        if (!workerTableView.getSelectionModel().isEmpty()) {
            Worker worker = workerTableView.getSelectionModel().getSelectedItem();
            requestCommand(REMOVE_LOWER, worker);
        } else {
            AlertDisplay.showError("ObjectNotChosen");
        }
    }

    @FXML
    public void addIfMinButtonOnClick() {
        localizePopUpWindow();
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
//        File selectedFile = fileChooser.showOpenDialog(primaryStage);
//        if (selectedFile == null) return;
//        if (client.processScriptToServer(selectedFile)) Platform.exit();
//        else refreshButtonOnAction();
        File scriptFile = fileChooser.showOpenDialog(primaryStage);
        if (scriptFile == null)
            return;
        if (client.executeScript(scriptFile))
            AlertDisplay.showInfo("ScriptSuccess");
        else AlertDisplay.showError("ScriptError");
        refreshButtonOnClick();
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
        if (!workerTableView.getSelectionModel().isEmpty()) {
            requestCommand(COUNT_BY_STATUS, workerTableView.getSelectionModel().getSelectedItem().getStatusString());
        } else {
            AlertDisplay.showError("ObjectNotChosen");
        }
    }

//    @FXML
//    public void saveButtonOnClick() {
//        requestCommand(SAVE_TO_FILE);
//    }

    @FXML
    public void printUniqueOrgsButtonOnClick() {
        requestCommand(PRINT_UNIQUE_ORGS);
    }

    @FXML
    public void refreshButtonOnClick() {
        try {
            requestCommand("show");
//            visualise();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setTableAction() {
        workerTableView.setRowFactory(tv -> {
            TableRow<Worker> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Worker worker = row.getItem();
                    if (worker.getUsername().equals(currentUserLabel.getText())) {
                        popUpWindowController.prepareForUpdate(worker);
                        long id = worker.getId();
                        popUpStage.showAndWait();
                        worker = popUpWindowController.getResult();
                        if (worker != null)
                            requestCommand(UPDATE, id + "", worker);
                    }
                }
            });
            return row;
        });
    }

    public void visualise() {
        shapeMap.keySet().forEach(s -> visualMapPane.getChildren().remove(s));
        shapeMap.clear();
        textMap.values().forEach(s -> visualMapPane.getChildren().remove(s));
        textMap.clear();
        infoTextMap.values().forEach(s -> visualMapPane.getChildren().remove(s));
        infoTextMap.clear();
        for (Worker worker : workerTableView.getItems()) {
            Text infoText = new Text(AlertDisplay.localizeNoMessage("WorkerDisplay", worker.displayWorker().split("\n")));
            infoText.setVisible(false);
            if (!userColorMap.containsKey(worker.getUsername()))
                userColorMap.put(worker.getUsername(),
                        Color.color(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble()));

            double size = Math.min(worker.getSalary(), 250);
            Shape circleObject = new Circle(size, userColorMap.get(worker.getUsername()));
//            circleObject.setOnMouseClicked(this::shapeOnMouseClicked);
            Text textObject = new Text(String.valueOf(worker.getId()));
            circleObject.setOnMousePressed((event) -> {
                switch (event.getClickCount()) {
                    case 1:
                        shapeOnMouseClicked(event);
                        textObject.setVisible(false);
                        infoText.setVisible(true);
                        PauseTransition visiblePause = new PauseTransition(
                                Duration.seconds(5)
                        );
                        visiblePause.setOnFinished(
                                event1 -> {
                                    infoText.setVisible(false);
                                    textObject.setVisible(true);
                                }
                        );
                        visiblePause.play();
                        break;
                    case 2:
                        shapeOnMouseClicked(event);
                        updateButtonOnClick();
                        break;
                    default:
                        break;
                }
            });
//            circleObject.translateXProperty().bind(visualMapPane.widthProperty().divide(2).add(worker.getCoordinateX()));
//            circleObject.translateYProperty().bind(visualMapPane.heightProperty().divide(2).subtract(worker.getCoordinateY()));

            textObject.setOnMouseClicked(circleObject::fireEvent);
            textObject.setFont(Font.font(size / 3));
            textObject.setFill(userColorMap.get(worker.getUsername()).darker());
            textObject.translateXProperty().bind(circleObject.translateXProperty().subtract(textObject.getLayoutBounds().getWidth() / 2));
            textObject.translateYProperty().bind(circleObject.translateYProperty().add(textObject.getLayoutBounds().getHeight() / 4));
            infoText.setVisible(false);
            infoText.setBoundsType(TextBoundsType.VISUAL);
//            infoText.setOnMouseClicked(circleObject::fireEvent);
            infoText.setFont(Font.font(size / 8));
            infoText.setFill(userColorMap.get(worker.getUsername()).darker().darker().desaturate());
            infoText.translateXProperty().bind(circleObject.translateXProperty().add(-50));
            infoText.translateYProperty().bind(circleObject.translateYProperty().subtract(100));
            StackPane layout = new StackPane();
            layout.getChildren().addAll(
                    infoText,
                    textObject,
                    circleObject
            );
            layout.setPadding(new Insets(20));
            layout.setAlignment(Pos.TOP_CENTER);

            visualMapPane.getChildren().add(circleObject);
            visualMapPane.getChildren().add(textObject);
            visualMapPane.getChildren().add(infoText);

            shapeMap.put(circleObject, worker.getId());
            textMap.put(worker.getId(), textObject);
            infoTextMap.put(worker.getId(), infoText);

            Path path = new Path();
            path.getElements().add(new MoveTo(0, -500));
            path.getElements().add(new HLineTo(worker.getCoordinateX()));
            path.getElements().add(new VLineTo(worker.getCoordinateY()));

            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(1500));
            pathTransition.setNode(circleObject);
            pathTransition.setPath(path);
            pathTransition.setOrientation(PathTransition.OrientationType.NONE);
            pathTransition.setAutoReverse(false);
            pathTransition.play();
//            ScaleTransition circleAnimation = new ScaleTransition(Duration.millis(1000), circleObject);
//            ScaleTransition textAnimation = new ScaleTransition(Duration.millis(1000), textObject);
//            circleAnimation.setFromX(0);
//            circleAnimation.setToX(1);
//            circleAnimation.setFromY(0);
//            circleAnimation.setToY(1);
//            textAnimation.setFromX(0);
//            textAnimation.setToX(1);
//            textAnimation.setFromY(0);
//            textAnimation.setToY(1);
//            circleAnimation.play();
//            textAnimation.play();
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
        HashSet<Worker> result = client.processRequestFromUser(command, argument, object);
        if (result != null) {
            workerTableView.setItems(FXCollections.observableArrayList(result));
            workerTableView.getSelectionModel().clearSelection();
            visualise();
        } else {
            AlertDisplay.showError("ConnectionError");
        }
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
            localizationTool.setResources(ResourceBundle.getBundle("client.bundles.gui",
                    localeMap.get(langChoiceComboBox.getValue())));
        });
        bindLanguage();
    }

    public void bindLanguage() {
        localizationTool.setResources(ResourceBundle.getBundle("client.bundles.gui",
                localeMap.get(langChoiceComboBox.getSelectionModel().getSelectedItem())));
        idColumn.setText(localizationTool.getStringBinding("IdColumn"));
        nameColumn.setText(localizationTool.getStringBinding("NameColumn"));
        xColumn.setText(localizationTool.getStringBinding("XColumn"));
        yColumn.setText(localizationTool.getStringBinding("YColumn"));
        salaryColumn.setText(localizationTool.getStringBinding("SalaryColumn"));
        endDateColumn.setText(localizationTool.getStringBinding("EndDateColumn"));
        creationDateColumn.setText(localizationTool.getStringBinding("CreationDateColumn"));
        positionColumn.setText(localizationTool.getStringBinding("PositionColumn"));
        statusColumn.setText(localizationTool.getStringBinding("StatusColumn"));
        orgColumn.setText(localizationTool.getStringBinding("OrgColumn"));
        orgTypeColumn.setText(localizationTool.getStringBinding("OrgTypeColumn"));
        annualTurnoverColumn.setText(localizationTool.getStringBinding("AnnualTurnoverColumn"));
        streetColumn.setText(localizationTool.getStringBinding("StreetColumn"));
        postalCodeColumn.setText(localizationTool.getStringBinding("PostalCodeColumn"));
        userColumn.setText(localizationTool.getStringBinding("UserColumn"));
        refreshButton.setText(localizationTool.getStringBinding("RefreshButton"));
        addButton.setText(localizationTool.getStringBinding("AddButton"));
        updateButton.setText(localizationTool.getStringBinding("UpdateButton"));
        removeButton.setText(localizationTool.getStringBinding("RemoveButton"));
        removeGreaterButton.setText(localizationTool.getStringBinding("RemoveGreaterButton"));
        removeLowerButton.setText(localizationTool.getStringBinding("RemoveLowerButton"));
        clearButton.setText(localizationTool.getStringBinding("ClearButton"));
        helpButton.setText(localizationTool.getStringBinding("HelpButton"));
        infoButton.setText(localizationTool.getStringBinding("InfoButton"));
        countByStatusButton.setText(localizationTool.getStringBinding("CountByStatusButton"));
        printUniqueOrgsButton.setText(localizationTool.getStringBinding("PrintUniqueOrgsButton"));
        addIfMinButton.setText(localizationTool.getStringBinding("AddIfMinButton"));
//        saveButton.textProperty().bind(localizationTool.getStringBinding("SaveButton"));
        executeScriptButton.setText(localizationTool.getStringBinding("ExecuteScriptButton"));
        visualMapTab.setText(localizationTool.getStringBinding("VisualMapTab"));
        dataTableTab.setText(localizationTool.getStringBinding("DataTableTab"));
    }

    public void setClient(Client client) {
        this.client = client;
        currentUserLabel.setText(client.getUser().getLogin());
        currentUserLabel.setStyle("-fx-border-color: white;");
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

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void localizePopUpWindow() {
        String lang;
        lang = langChoiceComboBox.getSelectionModel().getSelectedItem();
        Locale.setDefault(localeMap.get(lang));
    }
}
