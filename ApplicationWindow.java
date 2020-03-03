
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import javafx.beans.value.ChangeListener;
import java.io.FileInputStream; 
import java.io.InputStream;
import java.io.FileNotFoundException; 

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
    private int minPrice;
    private int maxPrice;
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
        
        //ChoiceDialog d = new ChoiceDialog(); 
        minComboBox.getItems().addAll("0", "50", "100", "150", "200", "250", "300");
        // Set the Limit of visible months to 5
        minComboBox.setVisibleRowCount(3);
        maxComboBox.getItems().addAll( "50", "100", "150", "200", "250", "300");
        // Set the Limit of visible months to 5
        maxComboBox.setVisibleRowCount(3);
        
        minComboBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                String output = minComboBox.getSelectionModel().getSelectedItem().toString();
                minPrice =  Integer.parseInt(output);
                
            }
        });
        
        maxComboBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                String output = minComboBox.getSelectionModel().getSelectedItem().toString();
                minPrice =  Integer.parseInt(output);
                
            }
        });
        
        GridPane topGridPane = new GridPane();
        topGridPane.setStyle("-fx-background-color: #336699;");
        topGridPane.setPrefSize(100, 10);
        topGridPane.setPadding(new Insets(10, 10, 10, 10));
        topGridPane.setMinSize(300, 100);
        topGridPane.setVgap(10);
        topGridPane.setHgap(10);
        
        
        GridPane bottomGridPane = new GridPane();
        bottomGridPane.setStyle("-fx-background-color: #336699;");
        bottomGridPane.setPrefSize(100, 10);
        bottomGridPane.setPadding(new Insets(10, 10, 10, 10));
        bottomGridPane.setMinSize(300, 100);
        bottomGridPane.setVgap(10);
        bottomGridPane.setHgap(10);

        //set an action on the button using method reference
        backButton.setOnAction(this::backButtonClick);
        forwardButton.setOnAction(this::forwardButtonClick);

        // Add the button and label into the pane
        topGridPane.add(priceFromLabel,95,0);
        topGridPane.add(minComboBox, 100, 0);
        topGridPane.add(priceToLabel,105,0);
        topGridPane.add(maxComboBox, 110, 0);
        bottomGridPane.add(backButton,30 , 0);
        bottomGridPane.add(forwardButton,100, 0);
        
        
        
        root.setTop(topGridPane);
        root.setBottom(bottomGridPane);
        centerPanel = new WelcomePanel();
        root.setCenter(centerPanel.getPanel(0,0));
        //root.setCenter(imageView);
        
        //root.setCenter();
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root, 300,100);
        stage.setTitle("Application Window");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1*/
   
    private void backButtonClick(ActionEvent event)
    {
        // Counts number of button clicks and shows the result on a label
       
    }
    private void forwardButtonClick(ActionEvent event)
    {
        // Counts number of button clicks and shows the result on a label
        
    }
}
