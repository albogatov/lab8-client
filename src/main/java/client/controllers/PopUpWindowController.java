package client.controllers;

import client.Client;
import client.utils.AlertDisplay;
import client.utils.LocalizationTool;
import commons.elements.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Locale;


public class PopUpWindowController {

    private Stage displayStage;
    private Client client;
    private Worker result;
    private LocalizationTool localizationTool;
    @FXML
    private Label nameLabel;
    @FXML
    private Label xLabel;
    @FXML
    private Label yLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label orgLabel;
    @FXML
    private Label orgTypeLabel;
    @FXML
    private Label annTurnoverLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField orgNameField;

    @FXML
    private TextField annualTurnoverField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<Position> positionComboBox;

    @FXML
    private ComboBox<Status> statusComboBox;

    @FXML
    private ComboBox<OrganizationType> orgTypeComboBox;

    @FXML
    private Button doneButton;

    @FXML
    public void doneButtonOnClick() {
        try {
            result = new Worker(parseName(), new Coordinates(parseX(), parseY()),
                    parseSalary(), endDatePicker.getValue(), positionComboBox.getValue(), statusComboBox.getValue(),
                    new Organization(parseAnnualTurnover(), orgTypeComboBox.getValue(), new Address(streetField.getText(),
                            postalCodeField.getText()), orgNameField.getText()), client.getUser().getLogin());
        } catch (IllegalArgumentException e) {
            result = null;
            AlertDisplay.showError("WrongEnteredData");
        }
        displayStage.close();
    }

    public void prepareForUpdate(Worker worker) {
        nameField.setText(worker.getName());
        xField.setText(String.valueOf(worker.getCoordinateX()));
        yField.setText(String.valueOf(worker.getCoordinateY()));
        salaryField.setText(String.valueOf(worker.getSalary()));
        if (!worker.getEndDateString().equals("null"))
            endDatePicker.setValue(LocalDate.parse(worker.getEndDate()));
        positionComboBox.setValue(worker.getPosition());
        statusComboBox.setValue(worker.getStatus());
        orgNameField.setText(worker.getOrganizationName());
        annualTurnoverField.setText(worker.getAnnualTurnoverString());
        orgTypeComboBox.setValue(worker.getOrganizationType());
        streetField.setText(worker.getAddressStreet());
        postalCodeField.setText(worker.getAddressZipCode());
    }

    public void clear() {
        nameField.clear();
        xField.clear();
        yField.clear();
        salaryField.clear();
        orgNameField.clear();
        annualTurnoverField.clear();
        streetField.clear();
        postalCodeField.clear();
        positionComboBox.valueProperty().set(null);
        statusComboBox.valueProperty().set(null);
        orgTypeComboBox.valueProperty().set(null);
        endDatePicker.setValue(null);
    }

    public Worker getResult() {
        Worker worker = result;
        result = null;
        clear();
        return worker;
    }

    public void initialize() {
        positionComboBox.setItems(FXCollections.observableArrayList(Position.values()));
        statusComboBox.setItems(FXCollections.observableArrayList(Status.values()));
        orgTypeComboBox.setItems(FXCollections.observableArrayList(OrganizationType.values()));
    }

    public void initLangs(LocalizationTool localizationTool) {
        this.localizationTool = localizationTool;
        nameLabel.setText(localizationTool.getStringBinding("NameLabel"));
        xLabel.setText(localizationTool.getStringBinding("XLabel"));
        yLabel.setText(localizationTool.getStringBinding("YLabel"));
        salaryLabel.setText(localizationTool.getStringBinding("SalaryLabel"));
        endDateLabel.setText(localizationTool.getStringBinding("EndDateLabel"));
        positionLabel.setText(localizationTool.getStringBinding("PositionLabel"));
        statusLabel.setText(localizationTool.getStringBinding("StatusLabel"));
        orgLabel.setText(localizationTool.getStringBinding("OrgLabel"));
        orgTypeLabel.setText(localizationTool.getStringBinding("OrgTypeLabel"));
        annTurnoverLabel.setText(localizationTool.getStringBinding("AnnTurnoverLabel"));
        streetLabel.setText(localizationTool.getStringBinding("StreetLabel"));
        postalCodeLabel.setText(localizationTool.getStringBinding("PostalCodeLabel"));
        doneButton.setText(localizationTool.getStringBinding("DoneButton"));
        nameField.setPromptText(localizationTool.getStringBinding("NamePrompt"));
        xField.setPromptText(localizationTool.getStringBinding("XPrompt"));
        yField.setPromptText(localizationTool.getStringBinding("YPrompt"));
        salaryField.setPromptText(localizationTool.getStringBinding("SalaryPrompt"));
        endDatePicker.setPromptText(localizationTool.getStringBinding("EndDatePrompt"));
        positionComboBox.setPromptText(localizationTool.getStringBinding("PositionPrompt"));
        statusComboBox.setPromptText(localizationTool.getStringBinding("StatusPrompt"));
        orgNameField.setPromptText(localizationTool.getStringBinding("OrgPrompt"));
        orgTypeComboBox.setPromptText(localizationTool.getStringBinding("OrgTypePrompt"));
        annualTurnoverField.setPromptText(localizationTool.getStringBinding("AnnTurnoverPrompt"));
        streetField.setPromptText(localizationTool.getStringBinding("StreetPrompt"));
        postalCodeField.setPromptText(localizationTool.getStringBinding("PostalCodePrompt"));
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setDisplayStage(Stage stage) {
        this.displayStage = stage;
    }

    public Stage getDisplayStage() {
        return displayStage;
    }

    public String parseName() {
        String name = nameField.getText();
        if (name.equals(""))
            throw new IllegalArgumentException();
        else return name;
    }

    public Integer parseX() {
        try {
            Integer x = Integer.parseInt(xField.getText());
            if (x < 0 || x > 627)
                throw new IllegalArgumentException();
            return x;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    public Long parseY() {
        try {
            Long y = Long.parseLong(yField.getText());
            if (y < 0 || y > 990)
                throw new IllegalArgumentException();
            return y;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    public Integer parseSalary() {
        try {
            Integer salary = Integer.parseInt(salaryField.getText());
            if (salary < 0)
                throw new IllegalArgumentException();
            return salary;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    public Long parseAnnualTurnover() {
        try {
            Long annualTurnover = Long.parseLong(annualTurnoverField.getText());
            if (annualTurnover < 0)
                throw new IllegalArgumentException();
            return annualTurnover;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}
