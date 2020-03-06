
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import javafx.beans.value.ChangeListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;




/**
 * Write a description of JavaFX class ApplicationWindow here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ApplicationWindow extends Application
{
    // We keep track of the count, and label displaying the count:
    private int count = 0;
    private BorderPane root= new  BorderPane();
    private Integer minPrice;
    private Integer maxPrice;
    private static Panel centerPanel;

    /**
     * The start method is the main entry point for every JavaFX application.
     * It is called after the init() method has returned and after
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)throws FileNotFoundException {

        Label priceFromLabel = new Label("From");
        priceFromLabel.setStyle("-fx-background-color: #00ffff;");
        Label priceToLabel = new Label("To");
        priceToLabel.setStyle("-fx-background-color: #00ffff;");
        Button backButton = new Button("BACK");
        Button forwardButton = new Button("FORWARD");
        ComboBox minComboBox = new ComboBox();
        ComboBox maxComboBox = new ComboBox();
        MenuBar menuBar = new MenuBar();

        //combo
        minComboBox.getItems().addAll("0", "50", "100", "150", "200", "250", "300");
        minComboBox.setVisibleRowCount(3);
        maxComboBox.getItems().addAll( "0", "50", "100", "150", "200", "250", "300");
        // Set the Limit of visible months to 5
        maxComboBox.setVisibleRowCount(3);

        minComboBox.setOnAction(new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent event) {
                String output = minComboBox.getSelectionModel().getSelectedItem().toString();
                minPrice =  Integer.valueOf(output);
                if (maxPrice!=null && isPriceCorrect())
                {
                    MapPanel map = new MapPanel();
                    Pane centerPanel = map.getPanel(minPrice,maxPrice);
                    root.setCenter(centerPanel);
                }

                if (maxPrice!=null && minPrice.intValue()>= maxPrice.intValue())
                {
                    showAbout();
                    minComboBox.valueProperty().set(null);
                }

            }
        });

        maxComboBox.setOnAction(new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent event) {
                String output = maxComboBox.getSelectionModel().getSelectedItem().toString();
                maxPrice =  Integer.valueOf(output);
                /*if (minPrice!=null && isPeiceCorrect())
                {

                }*/

                if (minPrice!=null && minPrice>= maxPrice)
                {
                    showAbout();
                    maxComboBox.valueProperty().set(null);
                }

            }
        });

        GridPane topGridPane = new GridPane();
        topGridPane.setPadding(new Insets(7, 7, 7, 7));
        topGridPane.setHgap(10);
        menuBar.setStyle("-fx-background-color: #336699;");

        BorderPane bottomPane = new BorderPane();
        bottomPane.setStyle("-fx-background-color: #336699;");
        bottomPane.setPrefSize(100, 10);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.setMinHeight(50);

        //set an action on the button using method reference
        backButton.setOnAction(this::backButtonClick);
        forwardButton.setOnAction(this::forwardButtonClick);

        // Add the button and label into the pane
        topGridPane.add(priceFromLabel, 0, 0);
        topGridPane.add(minComboBox, 1, 0);
        topGridPane.add(priceToLabel, 2, 0);
        topGridPane.add(maxComboBox, 3, 0);

        //menuBar.getMenus().add(topGridPane);
        bottomPane.setLeft(backButton);
        bottomPane.setRight(forwardButton);

        root.setTop(topGridPane);
        root.setBottom(bottomPane);
        WelcomePanel welcome = new WelcomePanel();
        Pane centerPanel = welcome.getPanel(0,0);
        root.setCenter(centerPanel);
        //root.setCenter(imageView);

        //root.setCenter();
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root);

        stage.setTitle("Application Window");
        stage.setScene(scene);
        stage.setMinHeight(centerPanel.getHeight());
        stage.setMinWidth(centerPanel.getWidth());
        stage.setMaximized(true);
        stage.setResizable(false);
        // Show the Stage (window)
        stage.show();
    }

    /**
     * This will check whether there is false input of prices
     */

    private boolean isPriceCorrect()
    {
        if (minPrice.intValue()>= maxPrice.intValue())
        {
            return false;
        }
        return true;
    }

    /**
     * This will show a dialog if the input price is not correct
     */

    private void showAbout()
    {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("About ImageViewer");
    alert.setHeaderText(null);  // Alerts have an optional header.
    alert.setContentText("Please select the correct input \n Minimum price must be lower than Maximum Price");
    alert.showAndWait();
    }

    static public void popUpList(listingBorough pane, String name)  {
        Stage newWindow = new Stage();
        Scene scene = new Scene(pane.getPane());
        newWindow.setTitle("Properties of " + name );
        newWindow.setScene(scene);
        newWindow.setMaxHeight(600);
        newWindow.setMinWidth(300);
        newWindow.show();
        pane.button();
    }

    static public void popUpProperty (Pane pane, String propertyName)    {
        Stage newWindow = new Stage();
        Scene scene = new Scene(pane);
        newWindow.setTitle(propertyName);
        newWindow.setScene(scene);
        newWindow.setMaxHeight(600);
        newWindow.setMinWidth(300);
        newWindow.show();
    }

    static public int getMinPrice()    {
        return minPrice;
    }

    static public int getMaxPrice() {
        return maxPrice;
    }
}
