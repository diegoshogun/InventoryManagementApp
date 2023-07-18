package wgu.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import wgu.model.Inventory;
import wgu.model.Part;
import wgu.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This controller class holds MainScreen of the application.
 */
public class MainScreenController implements Initializable {
    // stage
    private Stage stage;
    private Parent root;
    private Scene scene;

    //table
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partsPartIdCol;
    @FXML private TableColumn<Part, String> partsPartNameCol;
    @FXML private TableColumn<Part, Double> partsPriceCostCol;
    @FXML private TableColumn<Part, Integer> partsInventoryLevelCol;
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productsPartIdCol;
    @FXML private TableColumn<Product, String> productsPartNameCol;
    @FXML private TableColumn<Product, Double> productsPriceCostCol;
    @FXML private TableColumn<Product, Integer> productsInventoryLevelCol;

    // text fields and labels
    @FXML private TextField searchPartText;
    @FXML private TextField searchProductText;
    @FXML private Label partsLabel;
    @FXML private Label productsLabel;
    @FXML private Label noPartsFoundLabel;
    @FXML private Label noProductsFoundLabel;
    @FXML private Label productHasAssociatedPartsLabel;
    @FXML private Label IMSLabel;

    // buttons
    @FXML private Button productsModifyButton;
    @FXML private Button partsAddButton;
    @FXML private Button partsDeleteButton;
    @FXML private Button exitButton;
    @FXML private Button partsModifyButton;
    @FXML private Button productsAddButton;
    @FXML private Button productsDeleteButton;


    /**
     * Method switches scenes to AddPart FXML.
     *
     * @param event - switches scene
     * @throws IOException -
     */
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/AddPart.fxml"));
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method switches scenes to AddProduct FXML.
     *
     * @param event - switches scene
     * @throws IOException -
     */
    @FXML
    public void onActionAddProduct(ActionEvent event) throws IOException {
        System.out.println("Add product");
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/AddProduct.fxml"));
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method switches scenes to ModifyPart FXML.
     *
     * @param event - switches scene
     * @throws IOException -
     */
    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException {
        try {
            System.out.println("Modify part");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ModifyPart.fxml"));
            loader.load();

            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.selectedPartToModify(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            root = loader.getRoot();

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException nullPointerException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part to modify!");
            alert.showAndWait();
        }
    }

    /**
     * Method switches scenes to ModifyProduct FXML.
     *
     * @param event - switches scene
     * @throws IOException -
     */
    @FXML
    public void onActionModifyProduct(ActionEvent event) throws IOException {
        try {
            System.out.println("Modify product");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.modifyProduct(productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            root = loader.getRoot();

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException nullPointerException) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a product to modify!");
            alert.showAndWait();
        }

    }

    /**
     * Method deletes part selected from list of all parts.
     *
     * @param event - deletes part
     * @throws IOException -
     */
    @FXML
    public void onActionDeletePart(ActionEvent event) throws IOException {
        System.out.println("Delete part");
        Part selectedPartToDelete = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPartToDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?" +
                    " Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPartToDelete);
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setContentText("Please select a part to delete!");
            alert2.showAndWait();
        }
    }

    /**
     * Method deletes product selected from list of all products.
     *
     * @param event - deletes product
     * @throws IOException -
     */
    @FXML
    public void onActionDeleteProduct(ActionEvent event) throws IOException {
        System.out.println("Delete product");
        Product selectedProductToDelete = productsTableView.getSelectionModel().getSelectedItem();

        if (selectedProductToDelete != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?" +
                    " Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (selectedProductToDelete.getAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(selectedProductToDelete);
                    productHasAssociatedPartsLabel.setOpacity(0);
                } else {
                    productHasAssociatedPartsLabel.setOpacity(1);
                }
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setContentText("Please select a product to delete!");
            alert2.showAndWait();
        }
    }

    /**
     * This method allows user to search for a specific part from list of allParts.
     *
     * @param event - searches for part
     */
    @FXML
    public void onKeyTypedSearchPart(KeyEvent event) {
        String filter = searchPartText.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(filter);
        if (foundParts.isEmpty()) {
            partTableView.setItems(foundParts);
            noPartsFoundLabel.setOpacity(1);
            System.out.println("no more parts found");
        } else {
            noPartsFoundLabel.setOpacity(0);
            partTableView.setItems(foundParts);
        }
    }

    /**
     * This method allows user to search for a specific product from list of allProducts.
     *
     * @param event - searches for product
     */
    @FXML
    public void onKeyTypedSearchProduct(KeyEvent event) {
        String filter = searchProductText.getText();
        ObservableList<Product> foundProducts = Inventory.lookupProduct(filter);
        if (foundProducts.isEmpty()) {
            productsTableView.setItems(foundProducts);
            noProductsFoundLabel.setOpacity(1);
            System.out.println("no more products found");
        } else {
            noProductsFoundLabel.setOpacity(0);
            productsTableView.setItems(foundProducts);
        }
    }

    /**
     * Method terminates application when called.
     *
     * @param event - exits app
     */
    @FXML
    public void onActionExitApp(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Initializes controller class.
     *
     * @param resourceBundle -
     * @param url            -
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        productsTableView.setItems(Inventory.getAllProducts());

        partsPartIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partsPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partsPriceCostCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productsPartIdCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productsPartNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productsPriceCostCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }
}
