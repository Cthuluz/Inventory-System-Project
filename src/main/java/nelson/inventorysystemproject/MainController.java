package nelson.inventorysystemproject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static model.Inventory.lookupPart;
import static model.Inventory.lookupProduct;

/**
 * Controller class that dictates actions based on buttons or actions taken with the Main Controller menu
 * @author Isabel Nelson
 */
public class MainController implements Initializable {

     //Variables created for part and product tables, all column fields, search text fields,
     //and a static variable to hold the current product value

    public TableColumn<Product, Integer> ProductID;
    public TableColumn<Product, String> ProductName;
    public TableView<Part> PartTable;
    public TableView<Product> ProductTable;
    public TableColumn<Part, Integer> PartID;
    public TableColumn<Part, String> PartName;
    public TableColumn<Product, Integer> ProductInventoryLevel;
    public TableColumn<Product, Double> ProductPrice;
    public TableColumn<Part, Integer> PartInventoryLevel;
    public TableColumn<Part, Double> PartPrice;
    public TextField PartSearchText;
    public TextField ProductSearchText;
    static Product currentProduct;

    /**
     * Initialization of the MainController file, which fills out the table values for both parts and products
     *
     * @param url this is the url parameter
     * @param resourceBundle this is the resource bundle parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Setting all data within the product table
        ProductTable.setItems(Inventory.getAllProducts());
        ProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Setting all data within the parts table
        PartTable.setItems(Inventory.getAllParts());
        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Transfer menu to the Add Part menu
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the add button under the part table
     * @throws IOException the function throws an exception for a runtime error
     */
    public void addPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-part-form.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 506, 481);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Transfer menu to the modify part menu. Sends currently selected part values to the modify part menu tables
     * Error message if no part is selected
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the modify button under the part table
     * @throws IOException the function throws an exception for a runtime error
     */
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("modify-part-form.fxml"));
            loader.load();

            ModifyPartController currentController = loader.getController();
            currentController.sendPart(PartTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException e) {
            // Alert message for no selection made
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("No part chosen");
            alert.showAndWait();
        }
    }

    /**
     * Delete selected part from part observable list
     * Error message if no part is selected
     * Confirmation message to make sure user wants to delete part
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the delete button under the part table
     */
    public void deletePart(ActionEvent actionEvent) {
        try {
            // Confirmation message to make sure user wants to delete part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                boolean status = Inventory.deletePart(PartTable.getSelectionModel().getSelectedItem());
            }

        } catch (NullPointerException e) {
            // Alert message for no selection being made
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("No part deleted because no item chosen");
            alert.showAndWait();
        }
    }

    /**
     * Transfer menu to the add product menu
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the add button under the product table
     * @throws IOException the function throws an exception for a runtime error
     */
    public void addProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-product-form.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 818, 557);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Transfer menu to the modify product menu. Sends currently selected part values to the modify product menu tables
     * Error message if no product is selected
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the modify button under the product table
     * @throws IOException the function throws an exception for a runtime error
     */
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("modify-product-form.fxml"));
            loader.load();

            ModifyProductController currentController = loader.getController();
            currentProduct = ProductTable.getSelectionModel().getSelectedItem();
            currentController.sendProduct(currentProduct);

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException e) {
            // Alert message for no selection being made
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("No product chosen");
            alert.showAndWait();
        }
    }

    /**
     * Delete selected product from product observable list
     * Error message if no product is selected
     * Comfirmation message to make sure user wants to delete product
     * Error message if product contains associated parts
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the delete button under the product table
     */
    public void deleteProduct(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                if (ProductTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                    boolean status = Inventory.deleteProduct(ProductTable.getSelectionModel().getSelectedItem());
                } else {
                    // Alert message because product contains associated parts
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("An Error Occurred!");
                    alert1.setContentText("Product cannot be deleted if it contains associated parts");
                    alert1.showAndWait();
                }
            }

        } catch (NullPointerException e) {
            // Alert message because of no selection being made
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("An Error Occurred!");
            alert2.setContentText("No product deleted because no product chosen");
            alert2.showAndWait();
        }
    }

    /**
     * Closes entire program
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the exit button
     */
    public void exitMain(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Searches parts in part table.
     * For ID searches, shows exact matches highlighted
     * For name searches, allows a partial name search, and shows all matching names
     * Error message shown if no parts are found
     *
     * @param actionEvent this is the action event parameter that constitutes searching in the parts search text field
     */
    public void searchParts(ActionEvent actionEvent) {
        PartTable.setItems(Inventory.getAllParts());
        PartTable.getSelectionModel().clearSelection();

        String nameOrID = PartSearchText.getText();
        ObservableList<Part> searchedValues = lookupPart(nameOrID);

        if (searchedValues.size() > 0) {
            PartTable.setItems(searchedValues);
        } else if (searchedValues.size() == 0) {
            try {
                int id = Integer.parseInt(nameOrID);
                Part currentPart = lookupPart(id);
                if (currentPart != null) {
                    PartTable.getSelectionModel().select(currentPart);
                } else {
                    // Alert message because search result was empty
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("An Error Occurred!");
                    alert.setContentText("No parts were found");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                // Alert message because search result was empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("No parts were found");
                alert.showAndWait();
            }
        }
    }

    /**
     * Searches products in product table.
     * For ID searches, shows exact matches highlighted
     * For name searches, allows a partial name search, and shows all matching names
     * Error message shown if no products are found
     *
     * @param actionEvent this is the action event parameter that constitutes searching in the products search text field
     */
    public void searchProducts(ActionEvent actionEvent) {
        ProductTable.setItems(Inventory.getAllProducts());
        ProductTable.getSelectionModel().clearSelection();

        String nameOrID = ProductSearchText.getText();
        ObservableList<Product> searchedValues = lookupProduct(nameOrID);

        if (searchedValues.size() > 0) {
            ProductTable.setItems(searchedValues);
        }

        if (searchedValues.size() == 0) {
            try {
                int id = Integer.parseInt(nameOrID);
                Product product = lookupProduct(id);
                if (product != null) {
                    ProductTable.getSelectionModel().select(product);
                } else {
                    // Alert message from search coming up empty
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("An Error Occurred!");
                    alert.setContentText("No products were found");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                // Alert message from search coming up empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("No products were found");
                alert.showAndWait();
            }
        }
    }
}