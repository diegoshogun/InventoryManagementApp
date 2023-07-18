package wgu.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class with holds list of allParts Data and list of allProducts Data
 */
public class Inventory {
    // lists of parts and products
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds part to list of all parts
     *
     * @param part -
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * adds product to list of all parts
     *
     * @param product -
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * searches for part using id
     *
     * @param partId -
     * @return - if no part found
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) return part;
        }
        System.out.println("No such partId");
        return null;
    }

    /**
     * searches for product using id
     *
     * @param productId -
     * @return - if no product found
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) return product;
        }
        System.out.println("No such productId");
        return null;
    }

    /**
     * searches for part using name
     *
     * @param filter -
     * @return - if no part found
     */
    public static ObservableList<Part> lookupPart(String filter) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        if (filter.length() == 0 || filter.isBlank()) {
            foundParts = allParts;
        } else {
            for (Part p : allParts) {
                if (p.getName().toLowerCase().contains(filter.toLowerCase()) || String.valueOf(p.getId()).contains(filter))
                    foundParts.add(p);
            }
        }
        return foundParts;
    }

    /**
     * searches for product using name
     *
     * @param filter -
     * @return - if no part found
     */
    public static ObservableList<Product> lookupProduct(String filter) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        if (filter.length() == 0 || filter.isBlank()) {
            foundProducts = allProducts;
        } else {
            for (Product p : allProducts) {
                if (p.getName().toLowerCase().contains(filter.toLowerCase()) || String.valueOf(p.getId()).contains(filter))
                    foundProducts.add(p);
            }
        }

        return foundProducts;
    }

    /**
     * replaces part at specific index with updated part
     *
     * @param index        -
     * @param selectedPart -
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * replaces product at specific index with updated product
     *
     * @param index           -
     * @param selectedProduct -
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * deletes part from list
     *
     * @param selectedPart -
     * @return true or false if part was found or not to delete
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : allParts) {
            if (part.getId() == selectedPart.getId()) {
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     * deletes product from list
     *
     * @param selectedProduct -
     * @return true or false if product was found or not to delete
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : allProducts) {
            if (product.getId() == selectedProduct.getId()) {
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }

    /**
     * gets observable list of all parts
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * gets observable list of all products
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
