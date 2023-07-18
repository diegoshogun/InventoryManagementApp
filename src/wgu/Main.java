package wgu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.model.InHouse;
import wgu.model.Inventory;
import wgu.model.OutSourced;
import wgu.model.Product;

/**
 * Javadoc file attached in directory of this project, same file is also attached in zip file.
 *
 * FUTURE ENHANCEMENT - I would like to implement the ability to switch the color of the gui to a
 * lighter theme if the user so chooses. Right now it's set to one theme. But if the user prefers a lighter
 * theme as opposed to this darker theme, they're not able to change it.
 *
 * RUNTIME ERROR - I encountered an error if I did not have a part selected first to modify.
 * A NullPointerException error would occur, if I did not select a part first.
 * I fixed this by simply adding a try/catch block and instead of an error crashing my app,
 * I implemented an Alert box to appear as a warning to let the user know they need to select
 * a part first before modifying it.
 *
 * Main class for project. Creates application and loads the MainScreen.
 */
public class Main extends Application {
    /**
     * Start method for class. Loads MainScreen FXML and creates window.
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // setting up stage and scene
            Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
            Scene scene = new Scene(root);

            stage.setTitle("C482 PA - Diego Rodriguez");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method launches the application. Also stores prebuilt data for application.
     *
     * @param args main
     */
    public static void main(String[] args) {
        System.out.println("C482 Performance Assessment");

        // pre created parts and products
        InHouse inHousePart1 = new InHouse(1, "Brakes", 15.00, 15, 1, 20, 111);
        InHouse inHousePart2 = new InHouse(2, "Wheel", 11.00, 16, 1, 20, 112);
        InHouse inHousePart3 = new InHouse(3, "Seat", 14.00, 13, 1, 20, 113);
        OutSourced outSourcedPart1 = new OutSourced(4, "Screws", 0.3, 1000, 100, 2000, "Bolts & Screws");

        Product product1 = new Product(1, "Car", 100.00, 10, 1, 20);
        Product product2 = new Product(2, "Dirt Bike", 50.00, 20, 5, 40);
        Product product3 = new Product(3, "Bicycle", 20.00, 50, 20, 100);
        Product product4 = new Product(4, "Power Rack", 4000.00, 10, 5, 20);

        product3.addAssociatedPart(inHousePart3);

        // adding ports and products to their respective lists
        Inventory.addPart(inHousePart1);
        Inventory.addPart(inHousePart2);
        Inventory.addPart(inHousePart3);
        Inventory.addPart(outSourcedPart1);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);

        // launches application
        Application.launch();
    }
}
