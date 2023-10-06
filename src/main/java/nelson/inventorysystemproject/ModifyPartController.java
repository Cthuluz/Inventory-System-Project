package nelson.inventorysystemproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that dictates actions based on buttons or actions taken with the Modify Part menu
 * @author Isabel Nelson
 */
public class ModifyPartController implements Initializable {

     //Variables created for toggle buttons, text fields, the label of the "Company Name"/"Machine ID" as "PartMachineID",

    public RadioButton InHouse;
    public RadioButton Outsourced;
    public Label MachineID;
    public ToggleGroup sourceGroup;
    public TextField PartName;
    public TextField PartInv;
    public TextField PartPrice;
    public TextField PartMax;
    public TextField PartMachineID;
    public TextField PartID;
    public TextField PartMin;

    /**
     * Initialization of ModifyPartController file
     *
     * @param url this is the url parameter
     * @param resourceBundle this is the resource bundle parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Function that saves inputted changes of part to the part observable list
     * Error message if min, max and inventory have unrealistic values (i.e. max is less than min or inv is greater than max)
     * Error message if non integers are entered in any text box excluding the name text box
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the save button
     * @throws IOException the function throws an exception if a runtime error occurs
     */
    public void savePart(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(PartID.getText());
            String name = PartName.getText();
            int inventory = Integer.parseInt(PartInv.getText());
            double price = Double.parseDouble(PartPrice.getText());
            int max = Integer.parseInt(PartMax.getText());
            int min = Integer.parseInt(PartMin.getText());

            if ((min < max) && (min <= inventory) && (inventory <= max)) {
                if (InHouse.isSelected()) {
                    int machineID = Integer.parseInt(MachineID.getText());
                    Part modifiedPart = new InHouse(id, name, price, inventory, max, min, machineID);
                    int partIndex = Inventory.getAllParts().indexOf(Inventory.lookupPart(id));
                    Inventory.updatePart(partIndex, modifiedPart);
                } else {
                    String companyName = MachineID.getText();
                    Part modifiedPart = new Outsourced(id, name, price, inventory, max, min, companyName);
                    int partIndex = Inventory.getAllParts().indexOf(Inventory.lookupPart(id));
                    Inventory.updatePart(partIndex, modifiedPart);
                }

                goToMain(actionEvent);
            } else {
                // Alert message because of logical inventory error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error Occurred!");
                alert.setContentText("Please make sure: MIN < INVENTORY < MAX");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            // Alert message because of logical inventory error
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
     * @throws IOException the function throws an exception for a runtime error
     */
    public void cancelModifyPart(ActionEvent actionEvent) throws IOException {
        goToMain(actionEvent);
    }

    /**
     * The label for "Company Name" is changed to "Machine ID" when the inHouse toggle is clicked
     *
     * @param actionEvent this is the action event parameter that constitutes pressing the InHouse toggle button
     */
    public void inHouseToggle(ActionEvent actionEvent) {
        MachineID.setText("Machine ID");
    }

    /**
     * The label for "MachineID" is changed to "Company Name" when the outSourced toggle is clicked
     *
     * @param actionEvent this is the action event parameter that constitutes pressing Outsourced toggle button
     */
    public void outsourcedToggle(ActionEvent actionEvent) {
        MachineID.setText("Company Name");
    }

    /**
     * Function that transfers menu to the main menu
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
     * Function created to be used within the MainController file. Sets values of all text fields in the modify part menu
     *
     * @param part this is a part
     */
    public void sendPart(Part part) {
        PartID.setText(String.valueOf(part.getId()));
        PartName.setText(String.valueOf(part.getName()));
        PartInv.setText(String.valueOf(part.getStock()));
        PartPrice.setText(String.valueOf(part.getPrice()));
        PartMax.setText(String.valueOf(part.getMax()));
        PartMin.setText(String.valueOf(part.getMin()));

        if (part instanceof Outsourced) {
            PartMachineID.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            MachineID.setText("Company Name");
            Outsourced.setSelected(true);
        } else if (part instanceof InHouse) {
            PartMachineID.setText(String.valueOf(((InHouse) part).getMachineId()));
            MachineID.setText("Machine ID");
            InHouse.setSelected(true);
        }
    }
}