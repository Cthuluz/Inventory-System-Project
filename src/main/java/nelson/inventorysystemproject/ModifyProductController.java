package nelson.inventorysystemproject;

import javafx.collections.FXCollections;
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
import static nelson.inventorysystemproject.MainController.currentProduct;

/**
 * Controller class that dictates actions based on buttons or actions taken with the Modify Product menu
 * @author Isabel Nelson
 */
public class ModifyProductController implements Initializable {


     //Variables created for all text fields, the parts table, the products table, all columns and the search bar for associated parts
     //addedParts observable list to hold inputed values of associated parts

    public TextField ProductID;
    public TextField ProductName;
    public TextField ProductInv;
    public TextField ProductPrice;
    public TextField ProductMax;
    public TextField ProductMin;
    public TableView<Part> PartTable;
    public TableColumn<Part, Integer> PartID;
    public TableColumn<Part, String> PartName;
    public TableColumn<Part, Integer> PartInv;
    public TableColumn<Part, Double> PartPrice;
    public TableView<Part> AssPartTable;
    public TableColumn<Part, Integer> AssPartID;
    public TableColumn<Part, String> AssPartName;
    public TableColumn<Part, Integer> AssPartInv;
    public TableColumn<Part, Double> AssPartPrice;
    public TextField SearchPartBar;
    ObservableList<Part> addedParts = FXCollections.observableArrayList();

    /**
     * Initialization of the ModifyProductController sets up the part table and values
     *
     * @param url this is the url parameter
     * @param resourceBundle this is the resource bundle parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartTable.setItems(Inventory.getAllParts());
        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Adds new associated part to the addedParts observable list and updates the associated part table
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the add button under the parts table
     */
    public void addPartToProduct(ActionEvent actionEvent) {
        Part addedPart = PartTable.getSelectionModel().getSelectedItem();
        addedParts.add(addedPart);

        AssPartTable.setItems(addedParts);
    }

    /**
     * Removes associated part from addedParts observable list
     * Error message if no associated part chosen
     * Confirmation message to confirm the removal of selected associated part
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the remove button under the
     *                    associated parts table
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        try {
            if (AssPartTable.getSelectionModel().getSelectedItem() == null) {
                // Alert message because of no selection being made
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("No part removed because no part chosen");
                alert.showAndWait();
            } else {
                // Confirmation message making sure user wants to remove part
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to remove this associated part?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Part deletedPart = AssPartTable.getSelectionModel().getSelectedItem();
                    addedParts.remove(deletedPart);

                    AssPartTable.setItems(addedParts);
                }
            }

        } catch (NullPointerException e) {
            // Alert message because nothing was selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("No part removed because no part chosen");
            alert.showAndWait();
        }

        AssPartTable.getSelectionModel().clearSelection();
    }

    /**
     * addedParts observable list added to currently modified product's observable list of associated parts
     * Error message if no new name was chosen for product
     * Error message if min, max and inventory have unrealistic values (i.e. max is less than min or inv is greater than max)
     * Error message if non integers are entered in any text box excluding the name text box
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the save button
     * @throws IOException the function throws an exception for a runtime error
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(ProductID.getText());
            String name = ProductName.getText();
            int inventory = Integer.parseInt(ProductInv.getText());
            double price = Double.parseDouble(ProductPrice.getText());
            int max = Integer.parseInt(ProductMax.getText());
            int min = Integer.parseInt(ProductMin.getText());

            if (name.toLowerCase() == "default") {
                // Alert message because no name was chosen
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("Please choose a name");
                alert.showAndWait();

            } else if ((min <= inventory) && (inventory <= max)) {
                Product modifiedProduct = new Product(id, name, price, inventory, min, max);
                for (Part part : addedParts) {
                    modifiedProduct.addAssociatedPart(part);
                }

                int productIndex = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(id));
                Inventory.updateProduct(productIndex, modifiedProduct);

                goToMain(actionEvent);

            } else {
                // Alert message because logical error with inventory
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("Please make sure: MIN < INVENTORY < MAX");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            // Alert message because wrong data was inputted into integer only text field
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
    public void cancelModifyProduct(ActionEvent actionEvent) throws IOException {
        goToMain(actionEvent);
    }

    /**
     * Function to transfer current menu to the main menu
     *
     * RUNTIME ERROR: Values for the part and associated part tables were originally set within this initialization
     * function. However, information taken from the ID text field came back as null. To fix this error, and set the tables
     * with correct values, a new function was required, called sendProduct. This function had to be called within the MainController,
     * where the selected product to modify was specified.
     *
     * @param actionEvent this is the action event parameter that constitutes pressing any cancel button
     * @throws IOException the function throws an exception for a runtime error
     */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-form.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 275);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Function created to be used within the MainController file.
     * Sets values of all text fields in the modify product menu as well as the associated parts table
     *
     * @param product this is a product
     */
    public void sendProduct(Product product) {
        ProductID.setText(String.valueOf(product.getId()));
        ProductName.setText(String.valueOf(product.getName()));
        ProductInv.setText(String.valueOf(product.getStock()));
        ProductPrice.setText(String.valueOf(product.getPrice()));
        ProductMax.setText(String.valueOf(product.getMax()));
        ProductMin.setText(String.valueOf(product.getMin()));

        AssPartTable.setItems(product.getAllAssociatedParts());
        AssPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        for (Part part : currentProduct.getAllAssociatedParts()) {
            addedParts.add(part);
        }

    }

    /**
     * Searches parts in part table.
     * For ID searches, shows exact matches highlighted
     * For name searches, allows a partial name search, and shows all matching names
     * Error message shown if no parts are found
     *
     * LOGICAL ERROR: Original setup of function had an issue where if the ID was searched first,
     * then other ID's couldn't be searched. This was fixed with the addition of re-setting the table at the
     * beginning of each search
     *
     * @param actionEvent this is the action event parameter that constitutes searching in the search part text field
     */
    public void searchPart(ActionEvent actionEvent) {
        PartTable.setItems(Inventory.getAllParts());
        PartTable.getSelectionModel().clearSelection();

        String nameOrID = SearchPartBar.getText();
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
                    // Alert message because search result came up empty
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("An Error Occurred!");
                    alert.setContentText("No parts were found");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                // Alert message because search result came up empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("No parts were found");
                alert.showAndWait();
            }
        }
    }
}