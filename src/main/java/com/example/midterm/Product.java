package com.example.midterm;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Product {
    private final SimpleStringProperty productName;
    private final SimpleStringProperty id;
    private final SimpleStringProperty material;
    private final SimpleIntegerProperty quantity;
    private final SimpleObjectProperty<LocalDate> deliveryDate;

    public Product(String productName, String id, String material, int quantity, LocalDate deliveryDate) {
        this.productName = new SimpleStringProperty(productName);
        this.id = new SimpleStringProperty(id);
        this.material = new SimpleStringProperty(material);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.deliveryDate = new SimpleObjectProperty<>(deliveryDate);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public SimpleStringProperty materialProperty() {
        return material;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public SimpleObjectProperty<LocalDate> deliveryDateProperty() {
        return deliveryDate;
    }
}
