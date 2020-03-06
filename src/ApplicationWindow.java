import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import java.util.ArrayList;
import java.util.Iterator;

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
    private ComboBox minComboBox = new ComboBox();
    private ComboBox maxComboBox = new ComboBox();

    private static int minPrice;
    private static int maxPrice;
    private boolean minSelected;
    private boolean maxSelected;

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
    public void start(Stage stage)throws FileNotFoundException {

        Label priceFromLabel = new Label("From");
        priceFromLabel.setStyle("-fx-background-color: #00ffff;");
        Label priceToLabel = new Label("To");
        priceToLabel.setStyle("-fx-background-color: #00ffff;");
        Button backButton = new Button("BACK");
        Button forwardButton = new Button("FORWARD");

        panels = new ArrayList<>();
        panels.add(map);
        panels.add(stats);

        //ChoiceDialog d = new ChoiceDialog();

        minComboBox.getItems().addAll(null, "0", "50", "100", "150", "200", "250", "300");
        // Set the Limit of visible months to 5
        minComboBox.setVisibleRowCount(3);
        maxComboBox.getItems().addAll( null, "50", "100", "150", "200", "250", "300");
        // Set the Limit of visible months to 5
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

        BorderPane menuBar = new BorderPane();
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
        menuBar.setRight(topGridPane);
        bottomPane.setLeft(backButton);
        bottomPane.setRight(forwardButton);


        root.setTop(menuBar);
        root.setBottom(bottomPane);
        centerPanel = welcome.getPanel(0,0);
        root.setCenter(centerPanel);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root);

        stage.setTitle("Application Window");
        stage.setScene(scene);
        stage.setMinHeight(centerPanel.getHeight());
        stage.setMinWidth(centerPanel.getWidth());
       // stage.setMaximized(true);
        stage.setResizable(false);
        // Show the Stage (window)
        stage.show();
    }

    private void backButtonClick(ActionEvent event)
    {
        // last center panel
        if (minSelected && maxSelected) {
            if (count==0) {
                count = (count + 1) % 2;
            }
            else if (count ==1){
                count = (count - 1) % 2;
            }
            root.setCenter(panels.get(count).getPanel(minPrice, maxPrice));
        }
    }

    private void forwardButtonClick(ActionEvent event)
    {
        //next center panel
        if (minSelected && maxSelected) {
            count = (count + 1) % 2;
            root.setCenter(panels.get(count).getPanel(minPrice, maxPrice));
        }
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

    static public void popUpProperty (AirbnbListing property, ArrayList<AirbnbListing> list)    {
        Button left = new Button();
        Button right = new Button();
        BorderPane navigation = new BorderPane();
        Stage newWindow = new Stage();
        BorderPane main = new BorderPane();
        Scene scene = new Scene(main);
        left.setText("Left");
        right.setText("Right");
        navigation.setLeft(left);
        navigation.setRight(right);

        main.setCenter(displayProperty(property));
        newWindow.setTitle(displayProperty(property).getId());
        int i = list.indexOf(property);
        if (list.get(i+1) != null) {
            left.setOnMouseClicked(e -> {
                main.setCenter(displayProperty(list.get(i + 1)));
                newWindow.setTitle(displayProperty(list.get(i + 1)).getId());
                    }
            );
        }

        main.setBottom(navigation);
        newWindow.setScene(scene);
        newWindow.setMaxHeight(600);
        newWindow.setMinWidth(300);
        newWindow.show();
    }

    static public Pane displayProperty (AirbnbListing property)    {
        FlowPane container = new FlowPane();

        Label name = new Label();
        name.setText(property.getName());

        container.getChildren().addAll(name);
        container.setId(property.getName());

        return container;
    }

    static public int getMinPrice()    {
        return minPrice;
    }

    static public int getMaxPrice() {
        return maxPrice;
    }
}
