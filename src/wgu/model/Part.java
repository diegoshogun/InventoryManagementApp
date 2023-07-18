package wgu.model;

/**
 * Abstract class for all types of parts to extend from.
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * class constructor.
     *
     * @param id    -
     * @param name  -
     * @param price -
     * @param stock -
     * @param min   -
     * @param max   -
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * gets part id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * sets part id.
     *
     * @param id - part id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets part name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets part name.
     *
     * @param name - part name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets part price.
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets part price.
     *
     * @param price - part price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * gets part stock.
     *
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets part stock.
     *
     * @param stock - part stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * gets part min inv.
     *
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * sets part min inv.
     *
     * @param min - part min inv.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * gets part max inv.
     *
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * sets part max inv.
     *
     * @param max - part max inv.
     */
    public void setMax(int max) {
        this.max = max;
    }
}
