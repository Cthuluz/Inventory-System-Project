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

/**
 * Controller class that dictates actions based on buttons or actions taken within the Add Product menu
 * @author Isabel Nelson
 */
public class AddProductController implements Initializable {

     //Variables created for all text fields, the parts table, the products table, all columns and the search bar for associated parts
     //productCount static variable to keep track of product IDs
     //newProduct created to hold newly created product

    public TextField IDBar;
    public TextField NameBar;
    public TextField InvBar;
    public TextField PriceBar;
    public TextField MaxBar;
    public TextField MinBar;
    public static int productCount = 5;
    public TableView<Part> AssociatedPartsTable;
    public TableView<Part> PartsTable;
    public TableColumn<Part, Integer> PartID;
    public TableColumn<Part, String> PartName;
    public TableColumn<Part, Integer> PartInv;
    public TableColumn<Part, Double> PartPrice;
    public TableColumn<Part, Integer> AssPartID;
    public TableColumn<Part, String> AssPartName;
    public TableColumn<Part, Integer> AssPartInv;
    public TableColumn<Part, Double> AssPartPrice;
    public TextField SearchPartBar;
    Product newProduct = new Product(0, "Default", 0, 0, 0, 0);

    /**
     * Initialization of the AddProductController sets up the part table and values
     *
     * @param url this is the url parameter
     * @param resourceBundle this is the resource bundle parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartsTable.setItems(Inventory.getAllParts());
        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Adds new associated part to the new product and updates the associated part table
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the add part button
     */
    public void addPartToProduct(ActionEvent actionEvent) {
        Part addedPart = PartsTable.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedPart(addedPart);

        AssociatedPartsTable.setItems(newProduct.getAllAssociatedParts());
        AssPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Removes associated part from new product
     * Error message if no associated part chosen
     * Confirmation message to confirm the removal of selected associated part
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the remove button
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        try {
            if (AssociatedPartsTable.getSelectionModel().getSelectedItem() == null) {
                // Alert message for no selected part
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("No part removed because no part chosen");
                alert.showAndWait();
            } else {
                // Confirmation message for removing associated part
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to remove this associated part?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Part deletedPart = AssociatedPartsTable.getSelectionModel().getSelectedItem();
                    newProduct.deleteAssociatedPart(deletedPart);

                    AssociatedPartsTable.setItems(newProduct.getAllAssociatedParts());
                }
            }

        } catch (NullPointerException e) {
            // Alert message for no selected part
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("No part removed because no part chosen");
            alert.showAndWait();
        }

        AssociatedPartsTable.getSelectionModel().clearSelection();
    }

    /**
     * New product saved to inventory product observable list
     * Error message if no new name was chosen for product
     * Error message if min, max and inventory have unrealistic values (i.e. max is less than min or inv is greater than max)
     * Error message if non integers are entered in any text box excluding the name text box
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the save button
     * @throws IOException the function throws an exception for a runtime error
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        try {
            String name = NameBar.getText();
            int inventory = Integer.parseInt(InvBar.getText());
            double price = Double.parseDouble(PriceBar.getText());
            int max = Integer.parseInt(MaxBar.getText());
            int min = Integer.parseInt(MinBar.getText());

            if (name.toLowerCase() == "default") {
                // Alert message for user forgetting to input name
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("Please choose a name");
                alert.showAndWait();

            } else if ((min <= inventory) && (inventory <= max)) {
                newProduct.setId(productCount);
                newProduct.setName(name);
                newProduct.setPrice(price);
                newProduct.setStock(inventory);
                newProduct.setMin(min);
                newProduct.setMax(max);

                Inventory.addProduct(newProduct);

                productCount++;

                goToMain(actionEvent);
            } else {
                // Alert message for logical error with inventory
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("Please make sure: MIN < INVENTORY < MAX");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            // Alert for non integer in integer only text field
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("Please enter correct data type into text field");
            alert.showAndWait();
        }
    }

    /**
     * Transfer menu back to main menu and don't save inputed information
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the cancel button
     * @throws IOException the function throws an exception for a runtime error
     */
    public void cancelAddProduct(ActionEvent actionEvent) throws IOException {
        goToMain(actionEvent);
    }

    /**
     * Function to transfer current menu to the main menu
     *
     * @param actionEvent this is the action event parameter that constitutes pressing of any cancel button
     * @throws IOException  the function throws an exception for a runtime error
     */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-form.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 275);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Searches parts in part table.
     * For ID searches, shows exact matches highlighted
     * For name searches, allows a partial name search, and shows all matching names
     * Error message shown if no parts are found
     *
     * @param actionEvent this is the action event parameter that constitutes entering of text to search
     */
    public void searchPart(ActionEvent actionEvent) {
        PartsTable.setItems(Inventory.getAllParts());
        PartsTable.getSelectionModel().clearSelection();

        String nameOrID = SearchPartBar.getText();
        ObservableList<Part> searchedValues = lookupPart(nameOrID);

        if (searchedValues.size() > 0) {
            PartsTable.setItems(searchedValues);
        } else if (searchedValues.size() == 0) {
            try {
                int id = Integer.parseInt(nameOrID);
                Part currentPart = lookupPart(id);
                if (currentPart != null) {
                    PartsTable.getSelectionModel().select(currentPart);
                } else {
                    // Alert message for no parts coming up in search
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("An Error Occurred!");
                    alert.setContentText("No parts were found");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                // Alert message for no parts coming up in search
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("No parts were found");
                alert.showAndWait();
            }
        }
    }
}