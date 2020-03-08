import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import javafx.beans.value.ChangeListener;

import java.io.FileNotFoundException;

import java.util.ArrayList;

public class ApplicationWindow extends Application
{
    private int count = 0;
    private BorderPane root= new  BorderPane();
    private ComboBox minComboBox = new ComboBox();
    private ComboBox maxComboBox = new ComboBox();

    private static int minPrice;
    private static int maxPrice;
    private boolean minSelected;
    private boolean maxSelected;
    private static int position;

    private static Pane centerPanel;
    private Panel map = new MapPanel();
    private Panel stats = new StatsPanel();
    private Panel welcome = new WelcomePanel();

    private ArrayList<Panel> panels;
    /**
     * The start method is the main entry point for every JavaFX application.
     * It is called after the init() method has returned and after
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException {

        panels = new ArrayList<>();
        panels.add(map);
        panels.add(stats);

        minComboBox.getItems().addAll(null, "0", "50", "100", "150", "200", "250", "300");
        maxComboBox.getItems().addAll( null, "50", "100", "150", "200", "250", "300");

        minComboBox.setVisibleRowCount(3);
        maxComboBox.setVisibleRowCount(3);

        minComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t1 != null)  {
                    int min = Integer.parseInt(t1);
                    minPrice = min;
                    if (maxSelected && maxPrice > minPrice) {
                        centerPanel = map.getPanel(minPrice,maxPrice);
                    }
                    minSelected = true;
                }
                else {
                    centerPanel = welcome.getPanel(0,0);
                }
                root.setCenter(centerPanel);
            }
        });

        maxComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t1 != null)  {
                    int max = Integer.parseInt(t1);
                    maxPrice = max;
                    if (minSelected && maxPrice > minPrice) {
                        centerPanel = map.getPanel(minPrice,maxPrice);
                    }
                    maxSelected = true;
                }
                else    {
                    centerPanel = welcome.getPanel(0,0);
                }
                root.setCenter(centerPanel);
            }
        });

        centerPanel = welcome.getPanel(0,0);

        Label priceFromLabel = new Label("From");
        Label priceToLabel = new Label("To");

        Button backButton = new Button("BACK");
        backButton.setOnAction(this::backButtonClick);
        Button forwardButton = new Button("FORWARD");
        forwardButton.setOnAction(this::forwardButtonClick);

        HBox topPane = new HBox();
        topPane.getStyleClass().add("topBar");
        topPane.getChildren().addAll(priceFromLabel, minComboBox, priceToLabel, maxComboBox);

        HBox bottomPane = new HBox();
        bottomPane.getStyleClass().add("bottomBar");
        bottomPane.getChildren().addAll(backButton, forwardButton);

        root.setTop(topPane);
        root.setBottom(bottomPane);
        root.setCenter(centerPanel);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");

        stage.setTitle("Application Window");
        stage.setScene(scene);
        stage.setMinHeight(centerPanel.getHeight());
        stage.setMinWidth(centerPanel.getWidth());
        stage.setResizable(false);
        stage.show();
    }

    private void backButtonClick(ActionEvent event)
    {
        if (minSelected && maxSelected) {
            if (count == 0) {
                count = (count + 1) % 2;
            }
            else if (count == 1){
                count = (count - 1) % 2;
            }
            root.setCenter(panels.get(count).getPanel(minPrice, maxPrice));
        }
    }

    private void forwardButtonClick(ActionEvent event)
    {
        if (minSelected && maxSelected) {
            count = (count + 1) % 2;
            root.setCenter(panels.get(count).getPanel(minPrice, maxPrice));
        }
    }

    public static void triggerBoroughWindow(String boroughName, ArrayList<Borough> boroughs) {
        BoroughWindow boroughWindow = new BoroughWindow(boroughName, minPrice, maxPrice, boroughs);

        Scene scene = new Scene(boroughWindow.getPane());

        Stage boroughWindowStage = new Stage();
        boroughWindowStage.setTitle("Properties of " + boroughName );
        boroughWindowStage.setScene(scene);
        boroughWindowStage.setMaxHeight(600);
        boroughWindowStage.setMinWidth(300);
        boroughWindowStage.show();
    }

    public static void triggerPropertyWindow (AirbnbListing property, ArrayList<AirbnbListing> list, int pos) {
        PropertyWindow propertyWindow = new PropertyWindow(property, list, pos);

        Scene scene = new Scene(propertyWindow.getPane());

        Stage boroughWindowStage = new Stage();
        boroughWindowStage.setTitle("");
        boroughWindowStage.setScene(scene);
        boroughWindowStage.setMaxHeight(600);
        boroughWindowStage.setMinWidth(300);
        boroughWindowStage.show();
    }
}
