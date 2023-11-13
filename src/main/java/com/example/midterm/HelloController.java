package com.example.midterm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class HelloController {
    @FXML
    private TableView<Product> productListTableView;

    @FXML
    private TextField productNameField;
    @FXML
    private TextField idField;
    @FXML
    private ComboBox<String> materialComboBox;
    @FXML
    private ComboBox<Integer> quantityComboBox;
    @FXML
    private DatePicker deliveryDatePicker;
    @FXML
    private Label errorLabel;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        TableColumn<Product, String> productNameCol = new TableColumn<>("Product Name");
        productNameCol.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());

        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<Product, String> materialCol = new TableColumn<>("Material");
        materialCol.setCellValueFactory(cellData -> cellData.getValue().materialProperty());

        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        productListTableView.getColumns().addAll(productNameCol, idCol, materialCol, quantityCol);

        productListTableView.setItems(productList);
    }

    @FXML
    public void handleAddProduct(ActionEvent event) {
        String productName = productNameField.getText();
        String id = idField.getText();
        String material = materialComboBox.getValue();
        Integer quantity = quantityComboBox.getValue();
        LocalDate deliveryDate = deliveryDatePicker.getValue();

        if (productName.isEmpty() || id.isEmpty() || deliveryDate == null) {
            errorLabel.setText("Please fill in all fields.");
            return;
        } else if (idExists(id)) {
            errorLabel.setText("ID already exists. Please choose a different one.");
            return;
        } else if (deliveryDate.isBefore(LocalDate.now())) {
            errorLabel.setText("Delivery date cannot be in the past.");
            return;
        }

        if (material != null && quantity != null) {

            Product product = new Product(productName, id, material, quantity, deliveryDate);
            productList.add(product);

            productListTableView.setItems(null);
            productListTableView.setItems(productList);

            productNameField.clear();
            idField.clear();
            materialComboBox.getSelectionModel().clearSelection();
            quantityComboBox.getSelectionModel().clearSelection();
            deliveryDatePicker.getEditor().clear();
        }
    }

    private boolean idExists(String id) {
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}