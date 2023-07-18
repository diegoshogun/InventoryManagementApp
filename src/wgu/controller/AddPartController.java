package wgu.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import wgu.model.InHouse;
import wgu.model.Inventory;
import wgu.model.OutSourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This controller class allows user to create a new part.
 */
public class AddPartController implements Initializable {
    //stage
    private Stage stage;
    private Parent root;
    private Scene scene;

    // Buttons
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outSourcedRadioButton;

    // Labels and text fields
    @FXML private TextField idText;
    @FXML private TextField invText;
    @FXML private TextField priceCostText;
    @FXML private TextField minText;
    @FXML private TextField maxText;
    @FXML private TextField nameText;
    @FXML private TextField machineIdCompanyNameText;
    @FXML private Label machineIdCompanyNameLabel;

    // other variables
    private int newId;
    private boolean inHouseRadioButtonSelected = true;

    /**
     * This method changes the label from 'Machine ID' to 'Company Name' when needed.
     * If inHouseRadioButton is selected, MachineID will show
     * If inHouseRadioButton is not selected, Company Name will show
     *
     * @param event - ActionEvent
     * @throws IOException -
     */
    @FXML
    public void onActionRadioButton(ActionEvent event) throws IOException {
        if (inHouseRadioButton.isSelected()) {
            inHouseRadioButtonSelected = true;
            machineIdCompanyNameLabel.setText("Machine ID");
        } else {
            inHouseRadioButtonSelected = false;
            machineIdCompanyNameLabel.setText("Company Name");
        }
    }

    /**
     * This method saves the newly created part to the Observable List that holds all parts.
     *
     * @param event - ActionEvent
     * @throws IOException -
     */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = newId;
            String name = nameText.getText();
            double price = Double.parseDouble(priceCostText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());
            int machineId;
            String companyName;

            // checks to make sure stock/inv is greater than min value and less than max value
            // also checks to make sure the name text field is not empty
            if ((stock >= min && stock <= max) && (max > min) && !name.isEmpty()) {
                if (inHouseRadioButtonSelected) {
                    System.out.println("inhouse");
                    machineId = Integer.parseInt(machineIdCompanyNameText.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                } else {
                    System.out.println("outsourced");
                    companyName = machineIdCompanyNameText.getText();
                    Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
                }

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
                alert.setContentText("Please ensure all values are filled in!");
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
     * This method cancels the creation of a new part and returns to the MainScreen.
     *
     * @param event - ActionEvent
     * @throws IOException -
     */
    @FXML
    public void onActionCancelButton(ActionEvent event) throws IOException {
        // confirmation alert ensures use wants to cancel and return to main screen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, " +
                "do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        // if user does wish to cancel and return to main screen
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
     * This method creates the new auto-generated ID for the part.
     */
    public void setNewId() {
        newId = Inventory.getAllParts().size() + 1;
        idText.setText(String.valueOf(newId));
    }

    /**
     * Initializes controller class.
     *
     * @param resourceBundle - initialize function
     * @param url            - initialize function
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNewId();
    }
}
