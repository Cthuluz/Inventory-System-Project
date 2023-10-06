package model;

/**
 * Inherited class from the part class that holds parts made from scratch
 * @author Isabel Nelson
 */
public class InHouse extends Part {
    private int machineId; // Unique variable to the class InHouse

    /**
     * Constructor for InHouse class that sets all values
     * InHouse is an inherited class from the Part class
     *
     * @param id this is the part id
     * @param name this is the part name
     * @param price this is the part price
     * @param stock this is the part inventory
     * @param min this is the part min inventory
     * @param max this is the part max inventory
     * @param machineId this is the part machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max); // Constructor inherited from the Part class
        this.machineId = machineId; // Setting the additional variable not included in the part class
    }

    /**
     * Function to get the machine ID for in house parts
     * @return this is the machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Function to set the machine ID for in house parts
     * @param machineId this is the part machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId; // Setting machine id to inputted value
    }
}
