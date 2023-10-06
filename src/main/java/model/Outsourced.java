package model;

/**
 * Inherited class from the part class that holds parts bought from outside companies
 * @author Isabel Nelson
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor for Outsourced class that sets all values
     * InHouse is an inherited class from the Part class
     *
     * @param id this is a part id
     * @param name this is a part name
     * @param price this is a part price
     * @param stock this is a part inventory
     * @param min this is a part inventory min
     * @param max this is a part inventory max
     * @param companyName this is a part company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max); // Constructor inherited from the Part class
        this.companyName = companyName; // Setting the additional variable not included in the part class
    }

    /**
     * Function to get the company name for in house parts
     * @return this is a part company name return type
     */
    public String getCompanyName() {
        return companyName; // Getting machine id
    }

    /**
     * Function to set the company name for in house parts
     * @param companyName this is a part company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;  // Setting machine id to inputted value
    }
}
