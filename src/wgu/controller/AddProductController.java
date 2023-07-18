package wgu.controller;

import javafx.collections.FXCollections;
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
 * This controller class allows user to create a new product.
 */
public class AddProductController implements Initializable {
    // stage
    private Stage stage;
    private Parent root;
    private Scene scene;

    // parts lists
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // table
    @FXML private TableView<Part> allPartsTableView;
    @FXML private TableView<Part> associatedPartsTableView;
    @FXML private TableColumn<Part, Integer> allPartsPartIdCol;
    @FXML private TableColumn<Part, String> allPartsPartNameCol;
    @FXML private TableColumn<Part, Integer> allPartsInvLevelCol;
    @FXML private TableColumn<Part, Double> allPartsPriceCostCol;
    @FXML private TableColumn<Part, Integer> associatedPartsPartIdCol;
    @FXML private TableColumn<Part, String> associatedPartsPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedPartsInvLevelCol;
    @FXML private TableColumn<Part, Double> associatedPartsPriceCostCol;
    @FXML private TextField idText;
    @FXML private TextField invText;

    // labels and text fields
    @FXML private TextField nameText;
    @FXML private TextField priceText;
    @FXML private TextField minText;
    @FXML private TextField maxText;
    @FXML private TextField searchPartText;
    @FXML private Label noPartsFoundLabel;

    // buttons
    @FXML private Button addAssociatedPartButton;
    @FXML private Button cancelButton;
    @FXML private Button removeAssociatedPartButton;
    @FXML private Button saveButton;

    // other variables
    private int newId;

    /**
     * This method saves the newly created product to the Observable List that holds all products.
     *
     * @param event - Saves new product to list
     * @throws IOException -
     */
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = newId;
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            // checks to make sure stock/inv is greater than min value and less than max value
            // also checks to make sure the name text field is not empty
            if ((stock >= min && stock <= max) && (max > min) && !name.isEmpty()) {
                Product newProduct = new Product(id, name, price, stock, min, max);
                if (!associatedParts.isEmpty()) {
                    for (Part part : associatedParts)
                        newProduct.addAssociatedPart(part);
                }
                // adding new product to list of all products
                Inventory.addProduct(newProduct);
                System.out.println("Successful");

                // returning to main screen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
                scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            } else {
                // shows if one or more text fields are not correct
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please all values are correct!");
                alert.showAndWait();
            }
        } catch (NumberFormatException numberFormatException) { // error if string is entered instead of int/vice versa
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please ensure all values are correct!");
            alert.showAndWait();
        }
    }

    /**
     * This method allows user to search for a part to associate with from list of allParts.
     *
     * @param event - Filters tableview
     */
    @FXML
    public void onKeyTypedSearchPart(KeyEvent event) {
        String filter = searchPartText.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(filter);
        if (foundParts.isEmpty()) { // no parts are found, all parts display. and lets user know no parts are found
            allPartsTableView.setItems(foundParts);
            noPartsFoundLabel.setOpacity(1);
            System.out.println("no more parts found");
        } else { // displays parts that are found
            noPartsFoundLabel.setOpacity(0);
            allPartsTableView.setItems(foundParts);
        }
    }

    /**
     * This method saves the part user selected to associatedParts Observable List.
     *
     * @param event - Adds associated part to list
     */

    @FXML
    public void onActionAddAssociatedPart(ActionEvent event) {
        // ensures selected part to add is not null
        Part selectedPart = allPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) { // ensures part is not null
            if(associatedParts.contains(selectedPart)) { // checks to see if part is already in associated parts list
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Part is already associated!");
                alert.showAndWait();
            } else {
                associatedParts.add(selectedPart);
            }
        } else { // this alert display if a user does not select a part, otherwise error will occur
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setContentText("Please select a part!");
            alert2.showAndWait();
        }
    }

    /**
     * This method removes the part user selected from associatedParts Observable List.
     *
     * @param event - Removes associated part from product
     */
    @FXML
    public void onActionRemoveAssociatedPart(ActionEvent event) {
        Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to dissociate this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                associatedParts.remove(selectedPart);
        } else {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setContentText("Please select a part!");
            alert2.showAndWait();
        }
    }

    /**
     * This method cancels the creation of a new product and returns to the MainScreen.
     *
     * @param event - Cancels creation of new product
     * @throws IOException -
     */
    @FXML
    public void onActionCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, " +
                "do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // returning to main screen
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
            scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method creates the new auto-generated ID for the product.
     */
    public void setNewId() {
        newId = Inventory.getAllProducts().size() + 1;
        idText.setText(String.valueOf(newId));
    }

    /**
     * Initializes controller class and sets up the table views to be seen of all parts + associated parts.
     *
     * @param resourceBundle - sets up tableview
     * @param url            - sets up tableview
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNewId();

        allPartsTableView.setItems(Inventory.getAllParts());
        associatedPartsTableView.setItems(associatedParts);

        allPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        allPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allPartsInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        allPartsPriceCostCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        associatedPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartsInvLevelCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPartsPriceCostCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }
}
