package nelson.inventorysystemproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;

/**
 * Main class that initiates program, initial stage and initial variables
 * FUTURE ENHANCEMENT: The addition of min, max and stock could be useful in the main menu tables for clarity.
 * JAVADOCS LOCATION: InventorySystemProject/JavaDoc Comments
 *
 * @author Isabel Nelson
 */
public class Main extends Application {

    /**
     * Start function that sets up the initial stage and scene
     *
     * @param stage the stage to start with
     * @throws IOException the function throws an exception for a runtime error
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 275);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main function that creates initial objects and launches the program
     *
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        // Setting four Part objects - two as InHouse and two as Outsourced
        InHouse tire = new InHouse(1, "tire", 20.99, 50, 25, 200, 23899);
        Outsourced bikeSeat = new Outsourced(2, "bikeSeat", 70.99, 80, 15, 100, "Montall");
        InHouse propeller = new InHouse(3, "propeller", 90000, 3, 1, 5, 8973);
        Outsourced engine = new Outsourced(4, "engine", 500.55, 17, 10, 20, "Opto Corp");

        // Adding all new part objects to the inventory observable list of parts
        Inventory.addPart(tire);
        Inventory.addPart(bikeSeat);
        Inventory.addPart(propeller);
        Inventory.addPart(engine);

        // Setting four Product objects
        Product bike = new Product(1, "bike", 499.99, 4, 1, 10);
        Product car = new Product(2, "car", 36500.00, 12, 2, 20);
        Product airplane = new Product(3, "airplane", 5000000, 2, 1, 2);
        Product motorcycle = new Product(4, "motorcycle", 12000, 5, 3, 8);

        // Adding all new product objects to the inventory observable list of products
        Inventory.addProduct(bike);
        Inventory.addProduct(car);
        Inventory.addProduct(airplane);
        Inventory.addProduct(motorcycle);

        launch();
    }
}