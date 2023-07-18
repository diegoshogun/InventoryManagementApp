package wgu.model;

/**
 * This class extends Part class and allows the creation of OutSourced parts.
 */
public class OutSourced extends Part {
    private String companyName;

    /**
     * Constructor for OutSourced class. Initialize objects for class.
     *
     * @param id          -
     * @param name        -
     * @param price       -
     * @param stock       -
     * @param min         -
     * @param max         -
     * @param companyName -
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * gets companyName.
     *
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * sets companyName.
     *
     * @param companyName - companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
