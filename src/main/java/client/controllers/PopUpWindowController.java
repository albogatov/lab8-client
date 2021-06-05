package client.controllers;

import client.Client;
import commons.elements.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;


public class PopUpWindowController {

    private Stage displayStage;
    private Client client;
    private Worker result;
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

        }
        displayStage.close();
    }

    public void prepareForUpdate(Worker worker) {
        nameField.setText(worker.getName());
        xField.setText(String.valueOf(worker.getCoordinateX()));
        yField.setText(String.valueOf(worker.getCoordinateY()));
        salaryField.setText(String.valueOf(worker.getSalary()));
        endDatePicker.setValue(LocalDate.parse(worker.getEndDate()));
        positionComboBox.setValue(worker.getPosition());
        statusComboBox.setValue(worker.getStatus());
        orgNameField.setText(worker.getOrganizationName());
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
    }

    public Worker getResult() {
        clear();
        return result;
    }

    public void initialize() {
        positionComboBox.setItems(FXCollections.observableArrayList(Position.values()));
        statusComboBox.setItems(FXCollections.observableArrayList(Status.values()));
        orgTypeComboBox.setItems(FXCollections.observableArrayList(OrganizationType.values()));
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
            return x;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    public Long parseY() {
        try {
            Long y = Long.parseLong(yField.getText());
            return y;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    public Integer parseSalary() {
        try {
            Integer salary = Integer.parseInt(salaryField.getText());
            return salary;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    public Long parseAnnualTurnover() {
        try {
            Long annualTurnover = Long.parseLong(annualTurnoverField.getText());
            return annualTurnover;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}
