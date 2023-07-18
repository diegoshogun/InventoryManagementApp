package wgu.model;

/**
 * This class extends Part class and allows the creation of InHouse parts.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for InHouse class. Initialize objects for class.
     *
     * @param id    -
     * @param name  -
     * @param price -
     * @param stock -
     * @param min   -
     * @param max   -
     * @param machineId -
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * gets machineId.
     *
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * sets machineId.
     *
     * @param machineId - machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
