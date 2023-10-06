package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to create individual parts with any number of associated parts held in observable list
 * @author Isabel Nelson
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
     * Constructor for product class
     *
     * @param id this is the product id
     * @param name this is the product name
     * @param price this is the product price
     * @param stock this is the product stock
     * @param min this is the product min
     * @param max this is the product max
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
     * Function to return the observable list of associated parts
     * @return this is an observable list of parts
     */
    public ObservableList<Part> getAllAssociatedParts(){ return associatedParts;}

    /**
     * Function to return the product ID
     * @return this is a product id
     */
    public int getId() {return id;}

    /**
     * Function to return the product name
     * @return this is a product name
     */
    public String getName() {return name;}

    /**
     * Function to return the product price
     * @return this is a product price
     */
    public double getPrice() {return price;}

    /**
     * Function to return the product inventory
     * @return this is a product stock
     */
    public int getStock() {return stock;}

    /**
     * Function to return the product min inventory
     * @return this is a product min
     */
    public int getMin() {return min;}

    /**
     * Function to return the product max inventory
     * @return this is a product max
     */
    public int getMax() {return max;}

    /**
     * Function to set the product ID
     * @param id this is a product id
     */
    public void setId(int id) {this.id = id;}

    /**
     * Function to set the product name
     * @param name this is a product name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Function to set the product price
     * @param price this is a product price
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * Function to set the product inventory
     * @param stock this is a product inventory
     */
    public void setStock(int stock) {this.stock = stock;}

    /**
     * Function to set the product min inventory
     * @param min this is a product min
     */
    public void setMin(int min) {this.min = min;}

    /**
     * Function to set the product max inventory
     * @param max this is a product max
     */
    public void setMax(int max) {this.max = max;}

    /**
     * Function to add an associated part to observable list of associated parts
     * @param P this is a part
     */
    public void addAssociatedPart(Part P) {associatedParts.add(P);}

    /**
     * Function to delete an associated part to observable list of associated parts
     * @param P this is a part
     * @return this is a boolean return type
     */
    public boolean deleteAssociatedPart(Part P) {
        associatedParts.remove(P);
        return true;
    }
}
