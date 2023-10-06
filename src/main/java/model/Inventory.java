package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that holds all list of parts and products in observable lists
 * @author Isabel Nelson
 */
public class Inventory {
    // Observable list of parts used to hold data for the controller classes
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Function to get the observable list of parts in the inventory class
     * @return this is a observable list of parts
     */
    public static ObservableList<Part> getAllParts() { return allParts;}

    /**
     * Function to get the observable list of products in the inventory class
     * @return this is a observable list of parts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Function to add part to observable list of parts in the inventory class
     * @param P this is a part
     */
    public static void addPart(Part P) {
        allParts.add(P);
    }

    /**
     * Function to dd product to observable list of products in the inventory class
     * @param Pr this is a product
     */
    public static void addProduct(Product Pr) {
        allProducts.add(Pr);
    }

    /**
     * Function to look up part by ID in the inventory class
     * @param id this is a part id
     * @return this is a part
     */
    public static Part lookupPart(int id) {
        for(Part P : Inventory.getAllParts()) {
            if(P.getId() == id) {
                return P;
            }
        }
        return null;
    }

    /**
     * Function to look up part by name in the inventory class
     * @param name this is a part name
     * @return this is an observable list of parts
     */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part P : allParts) {
            if(P.getName().toLowerCase().contains(name.toLowerCase())) {
                namedParts.add(P);
            }
        }
        return namedParts;
    }

    /**
     * Function to look up product by ID in the inventory class
     * @param id this is a product id
     * @return this is a product
     */
    public static Product lookupProduct(int id) {
        for(Product Pr : Inventory.getAllProducts()) {
            if(Pr.getId() == id) {
                return Pr;
            }
        }
        return null;
    }

    /**
     * Function to look up product by name in the inventory class
     * @param name this is a product name
     * @return this is an observable list of products
     */
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for(Product Pr : allProducts) {
            if(Pr.getName().toLowerCase().contains(name.toLowerCase())) {
                namedProducts.add(Pr);
            }
        }
        return namedProducts;
    }

    /**
     * Function to update a part in the inventory class
     * @param index this is a part index
     * @param P this is a part
     */
    public static  void updatePart(int index, Part P) {
        allParts.set(index, P);
    }

    /**
     * Function to update a product in the inventory class
     * @param index this is a product index
     * @param Pr this is a product
     */
    public static void updateProduct(int index, Product Pr) {
        allProducts.set(index, Pr);
    }

    /**
     * Function to delete a part in the inventory class
     * @param P this is a part
     * @return this is a boolean return type
     */
    public static boolean deletePart(Part P) {
        boolean status = lookupPart(P.getId()) != null;
        allParts.remove(P);
        return status;
    }

    /**
     * Function to delete a product the inventory class
     * @param Pr this is a product
     * @return this is a boolean return type
     */
    public static boolean deleteProduct(Product Pr) {
        boolean status = lookupProduct(Pr.getId()) != null;
        allProducts.remove(Pr);
        return status;
    }
}
