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
import wgu.model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This controller class allows the update of a specific part selected.
 */
public class ModifyPartController implements Initializable {
    // stage
    private Stage stage;
    private Parent root;
    private Scene scene;

    // text fields and labels
    @FXML private TextField idText;
    @FXML private TextField nameText;
    @FXML private TextField minText;
    @FXML private TextField maxText;
    @FXML private TextField invText;
    @FXML private TextField priceCostText;
    @FXML private TextField machineIdCompanyNameText;
    @FXML private Label machineIdCompanyNameLabel;

    // buttons and toggle group
    @FXML private ToggleGroup addPartToggleGroup;
    @FXML private Button cancelButton;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outSourcedRadioButton;
    @FXML private Button saveButton;

    // other variables
    private boolean inHouseRadioButtonSelected;

    /**
     * This method changes the label from 'Machine ID' to 'Company Name' when needed.
     * If inHouseRadioButton is selected, MachineID will show
     * If inHouseRadioButton is not selected, Company Name will show
     *
     * @param event - changes label
     */
    @FXML
    public void onActionRadioButton(ActionEvent event) {
        if (inHouseRadioButton.isSelected()) {
            System.out.println("inhouse");
            inHouseRadioButton.fire();
            inHouseRadioButtonSelected = true;
            machineIdCompanyNameLabel.setText("Machine ID");
        } else if(outSourcedRadioButton.isSelected()) {
            System.out.println("outsourced");
            outSourcedRadioButton.fire();
            inHouseRadioButtonSelected = false;
            machineIdCompanyNameLabel.setText("Company Name");
        }
    }

    /**
     * This method saves the newly modified part to the Observable List that holds all parts.
     *
     * @param event - saves part
     * @throws IOException -
     */
    @FXML
    public void onActionSaveModifiedPart(ActionEvent event) throws IOException {
        try {
            int index = 0;
            int id = Integer.parseInt(idText.getText());
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
                for (Part part : Inventory.getAllParts()) {
                    if (part.getId() == id) {
                        if (inHouseRadioButtonSelected) {
                            machineId = Integer.parseInt(machineIdCompanyNameText.getText());
                            Inventory.updatePart(index, new InHouse(id, name, price, stock, min, max, machineId));
                        } else {
                            companyName = machineIdCompanyNameText.getText();
                            Inventory.updatePart(index, new OutSourced(id, name, price, stock, min, max, companyName));
                        }
                    }
                    index++;
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
                alert.setContentText("Please ensure all values are correct!");
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
     * This method cancels the update of a part and returns to the MainScreen.
     *
     * @param event - cancels update
     * @throws IOException -
     */
    @FXML
    public void onActionCancelButton(ActionEvent event) throws IOException {
        // confirmation alert ensures use wants to cancel and return to main screen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No changes will be saved, " +
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
     * This method fills the text fields with original part data to update from MainScreen controller.
     *
     * @param part modifies part
     */
    public void selectedPartToModify(Part part) {
        //fills text fields with original data of part
        idText.setText(String.valueOf(part.getId()));
        nameText.setText(part.getName());
        priceCostText.setText(String.valueOf(part.getPrice()));
        invText.setText(String.valueOf(part.getStock()));
        minText.setText(String.valueOf(part.getMin()));
        maxText.setText(String.valueOf(part.getMax()));

        // checks if part is an InHouse Part or Outsourced Part and sets variables accordingly
        if (part instanceof InHouse) {
            machineIdCompanyNameText.setText(String.valueOf(((InHouse) part).getMachineId()));
            machineIdCompanyNameLabel.setText("Machine ID");
            inHouseRadioButton.fire();
            inHouseRadioButtonSelected = true;
        } else if (part instanceof OutSourced) {
            machineIdCompanyNameLabel.setText("Company Name");
            machineIdCompanyNameText.setText(((OutSourced) part).getCompanyName());
            outSourcedRadioButton.fire();
            inHouseRadioButtonSelected = false;
        }
    }

    /**
     * Initializes controller class.
     *
     * @param resourceBundle -
     * @param url            -
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
