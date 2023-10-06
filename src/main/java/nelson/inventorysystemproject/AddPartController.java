package nelson.inventorysystemproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that dictates actions based on buttons or actions taken with the Add Part menu
 * @author Isabel Nelson
 */
public class AddPartController implements Initializable {

     //Variables created for toggle buttons, text fields, the label of the "Company Name"/"Machine ID" as "PartChangingLabel",
     //and a variable to remember index for part IDs

    public ToggleGroup sourceGroup;
    public TextField PartName;
    public TextField PartInv;
    public TextField PartPrice;
    public TextField PartMaxInv;
    public TextField PartMinInv;
    public TextField PartMachineID;
    public TextField PartID;
    public RadioButton InHouseRadio;
    public RadioButton OutsourcedRadio;
    public Label PartChangingLabel;
    public static int partCount = 5;

    /**
     * Initialization of file
     *
     * @param url this is the url parameter
     * @param resourceBundle this is the resource bundle parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {};

    /**
     * Function that saves inputted part to the part observable list
     * Error message if min, max and inventory have unrealistic values (i.e. max is less than min or inv is greater than max)
     * Error message if non integers are entered in any text box excluding the name text box
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the save button
     * @throws IOException the function throws an exception for a runtime error
     */
    public void savePart(ActionEvent actionEvent) throws IOException {
        try {
            String name = PartName.getText();
            int inventory = Integer.parseInt(PartInv.getText());
            double price = Double.parseDouble(PartPrice.getText());
            int max = Integer.parseInt(PartMaxInv.getText());
            int min = Integer.parseInt(PartMinInv.getText());
            if ((min < max) && (min <= inventory) && (inventory <= max)) {
                if (InHouseRadio.isSelected()) {
                    int machineID = Integer.parseInt(PartMachineID.getText());
                    InHouse newPart = new InHouse(partCount, name, price, inventory, min, max, machineID);
                    Inventory.addPart(newPart);
                } else {
                    String companyName = PartMachineID.getText();
                    Outsourced newPart = new Outsourced(partCount, name, price, inventory, min, max, companyName);
                    Inventory.addPart(newPart);
                }

                partCount++;

                goToMain(actionEvent);
            } else {
                // Alert message for inventory logic
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("Please make sure: MIN < INVENTORY < MAX");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            // Alert message for non-integers being typed into integer text fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("Please enter correct data type into text field");
            alert.showAndWait();
        }
    }

    /**
     * Transfers back to the main menu when the cancel button is clicked
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the cancel button
     * @throws IOException the function throws an exception if a runtime error occurs
     */
    public void cancelAddPart(ActionEvent actionEvent) throws IOException {
        goToMain(actionEvent);
    }

    /**
     * The label for "Company Name" is changed to "Machine ID" when the inHouse toggle is clicked
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the inHouse toggle button
     */
    public void inHouseToggle(ActionEvent actionEvent) {
        PartChangingLabel.setText("Machine ID");

    }

    /**
     * The label for "MachineID" is changed to "Company Name" when the outSourced toggle is clicked
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the Outsourced toggle button
     */
    public void outsourcedToggle(ActionEvent actionEvent) {
        PartChangingLabel.setText("Company Name");
    }

    /**
     * Function that transfers menu to the main menu
     *
     * @param actionEvent this is the action event parameter that constitutes pressing any cancel button
     * @throws IOException the function throws an exception if a runtime error occurs
     */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-form.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 275);
        stage.setScene(scene);
        stage.show();
    }
}