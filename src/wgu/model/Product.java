package wgu.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for product object and associated parts.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * class constructor
     *
     * @param id    -
     * @param name  -
     * @param price -
     * @param stock -
     * @param min   -
     * @param max   -
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * gets product id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * sets product id
     *
     * @param id - product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets product name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets product name
     *
     * @param name - product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets product price
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets product price
     *
     * @param price - product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * gets product stock
     *
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets product stock
     *
     * @param stock - product stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * gets product min inv
     *
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * sets product min inv
     *
     * @param min - product min inv
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * gets product max inv
     *
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * sets product max inv
     *
     * @param max - product max inv
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds associated part to associated parts list
     *
     * @param part - part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes associated part from associated parts list
     *
     * @param part - deletes associated part
     * @return true -
     */
    public boolean deleteAssociatedPart(Part part) {
        return (associatedParts.remove(part));
    }

    /**
     * Returns all associated parts
     *
     * @return associatedParts - Returns all associated parts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
}
