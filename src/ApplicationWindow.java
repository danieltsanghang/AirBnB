import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.concurrent.*;

import java.io.File;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.util.Duration;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;

public class ApplicationWindow extends Application
{
    //Create the root pane for the application window
    private BorderPane root= new  BorderPane();

    //Create combobox of the minimum price and maximum price desired by the user
    private ComboBox<String> minComboBox = new ComboBox();
    private ComboBox<String> maxComboBox = new ComboBox();

    //Minimum price chosen by the user
    private static int minPrice;
    //Maximum price chosen by the user
    private static int maxPrice;
    //Whether the user has chosen the minimum price
    private boolean isMinSelected;
    //Whether hte user has chosen the maximum price
    private boolean isMaxSelected;

    //Create a centerPanel
    private static Pane centerPanel;
    //Create a splashLayout
    private Pane splashLayout;
    //Create a progress indicator
    private ProgressIndicator loadProgress;
    //Create the label for the loading process
    private Label loadText;

    //Create the welcome Panel
    private Panel welcome = new WelcomePanel();
    //Create an array list that stores panels
    private ArrayList<Panel> panels;
    //Create an integer that counts which panels in the array list to be displayed
    private int count = 0;
    // Create a Favourite Data Loader
    private static FavouriteDataLoader favouriteDataLoader = new FavouriteDataLoader();

    public ApplicationWindow() throws IOException {
    }

    @Override
    public void init() {
        // Loads the splash screen image based on the provided URL
        // Converts File to ImageView using Image
        File splashScreenImageFile = new File("img/splashscreen.png");
        Image splashScreenImage = new Image(splashScreenImageFile.toURI().toString());
        ImageView splashScreen = new ImageView(splashScreenImage);
        //Create a new progress indicator named loadProgress
        //Create a new label for load text
        //Style the label with
        loadProgress = new ProgressIndicator();
        loadText = new Label();
        loadText.getStyleClass().add("whiteText");

        //Create a Vbox and add the progress indicator and the load text labour into it
        VBox overlay = new VBox();
        overlay.getStyleClass().add("overlayPane");
        overlay.getChildren().addAll(loadProgress, loadText);

        //Create a stack pane calls splashLayout and add the splash screen and vertical box overlay into it
        splashLayout = new StackPane();
        splashLayout.getChildren().addAll(splashScreen, overlay);
    }

    @Override
    public void start(final Stage initStage) throws FileNotFoundException {
        Task<ArrayList<Panel>> createPanels = new Task<>() {
            @Override
            protected ArrayList<Panel> call() throws InterruptedException, IOException {
                AirbnbDataLoader dataLoader = new AirbnbDataLoader();
                ArrayList<AirbnbListing> master = dataLoader.load();
                //Create an array list panels to store all the panels
                ArrayList<Panel> panels = new ArrayList<>();

                //Update the Message with Map Panel
                //Create and add a map panel in to the array list panels
                updateMessage("Loading Map Panel ...");
                panels.add(new MapPanel(master));

                //Update the Message with Statistics Panel
                //Create and add a statistics panel in to the array list panels
                updateMessage("Loading Stats Panel ...");
                panels.add(new StatsPanel(master));

                //Update the Message with User Panel
                //Create and add a user panel in to the array list panels
                updateMessage("Loading Account Panel ...");
                panels.add(new UserPanel(master));

                //Update the Message with application starting
                //Pause for three hundred milli seconds
                updateMessage("Application Starting");
                Thread.sleep(300);

                //return the array list panels
                return panels;
            }
        };

        showSplash(initStage, createPanels, () -> showMainStage(createPanels.getValue()));
        new Thread(createPanels).start();
    }
    /**
     * @param  loadedPanels  for providing the panels available
     */
    private void showMainStage(ArrayList<Panel> loadedPanels) {
        //Set the field array list panels as the parameter array list loadPanels
        panels = loadedPanels;

        //Provide options for the user to choose for minimum price and maximum price combobox
        //Set the visible rows viewed by the user when choosing the minimum and maximum price as 3
        minComboBox.getItems().addAll(null, "0", "50", "100", "200", "500", "1000", "2000", "4000");
        maxComboBox.getItems().addAll( null, "50", "100", "200", "500", "1000", "2000", "4000", "7000");
        minComboBox.setVisibleRowCount(3);
        maxComboBox.setVisibleRowCount(3);

        //Subsequent acts followed if a value of the minimum price combo box is chosen
        minComboBox.valueProperty().addListener((ov, oldTerm, newTerm) -> {
            if(newTerm != null)  {
                //Set the minimum price desired by the user as the chosen value after converting the chosen value
                //from string to integer
                minPrice = Integer.parseInt(newTerm);

                //If the maximum price is  selected and the maximum price selected is larger than the minimum price
                //selected, set the center panel as the first panel of the array list panels
                if (isMaxSelected && maxPrice > minPrice) {
                    centerPanel = panels.get(0).getPanel(minPrice,maxPrice);
                }
                // The minimum price is selected
                isMinSelected = true;
            }
            else {
                //Set the center panel as a new welcome panel
                centerPanel = welcome.getPanel(0,0);
            }
            //Reset the center panel of the root of application window
            paneFade(root.getCenter(),1,0);
            root.setCenter(centerPanel);
            paneFade(root.getCenter(),0,1);
        });

        //Subsequent acts followed if a value of the minimum price combo box is chosen
        maxComboBox.valueProperty().addListener((ov, oldTerm, newTerm) -> {
            if(newTerm != null)  {
                //Set the maximum price desired by the user as the chosen value after converting the chosen value
                //from string to integer
                maxPrice = Integer.parseInt(newTerm);
                //If the minimum price is  selected and the maximum price selected is larger than the minimum price
                //selected, set the center panel as the first panel of the array list panels
                if (isMinSelected && maxPrice > minPrice) {
                    centerPanel = panels.get(0).getPanel(minPrice,maxPrice);
                }
                // The maximum price is selected
                isMaxSelected = true;
            }
            else{
                //Set the center panel as a new welcome panel
                centerPanel = welcome.getPanel(0,0);
            }
            //Reset the center panel of the root of application window
            paneFade(root.getCenter(),1,0);
            root.setCenter(centerPanel);
            paneFade(root.getCenter(),0,1);
        });
        //Set the center panel as a new welcome panel
        centerPanel = welcome.getPanel(0,0);

        //Create two labels "From" and "To" for indicating the minimum and maximum price combobox
        Label priceFromLabel = new Label("From");
        Label priceToLabel = new Label("To");

        //Create two buttons "Back' and "Forward"
        //Set the subsequent actions when the buttons are clicked
        Button backButton = new Button("BACK");
        backButton.setOnAction(this::backButtonClick);
        Button forwardButton = new Button("FORWARD");
        forwardButton.setOnAction(this::forwardButtonClick);

        //Create a Hbox and add the "From", "To" labels as well as the minimum and combox box into it
        HBox topPane = new HBox();
        topPane.getStyleClass().add("topBar");
        topPane.getChildren().addAll(priceFromLabel, minComboBox, priceToLabel, maxComboBox);

        //Create a Hbox and add the "Back" and "Forward" Button into it
        HBox bottomPane = new HBox();
        bottomPane.setId("navBarMain");
        bottomPane.getChildren().addAll(backButton, forwardButton);

        //Set the root with the toppane, bottompane as well as the center panel
        root.setTop(topPane);
        root.setBottom(bottomPane);
        root.setCenter(centerPanel);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("darkMode.css");
        scene.getStylesheets().add("styles.css");

        Stage mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("Application Window");
        mainStage.setScene(scene);

        //Set the height and width of the mainstage which should be same as those of the center panel. The size could
        //not be resized by the user
        mainStage.setMinHeight(centerPanel.getHeight());
        mainStage.setMinWidth(centerPanel.getWidth());
        mainStage.setResizable(false);

        mainStage.show();
    }
    //Show Splash Method
    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        loadText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());

        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);

                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(0.4), splashLayout);
                fadeSplash.setFromValue(1);
                fadeSplash.setToValue(0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            }
        });

        Scene splashScene = new Scene (splashLayout, Color.TRANSPARENT);
        splashScene.getStylesheets().add("darkMode.css");
        splashScene.getStylesheets().add("styles.css");
        initStage.setScene(splashScene);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }


    //Subsequent acts if the back button is clicked
    private void forwardButtonClick(ActionEvent event) {
        if (isMinSelected && isMaxSelected) {
            //Set count as the current position of array list panels plus one of the remainder of three
            count = (count + 1) % 3;
            //Create a fade transition for root's center
            paneFade(root.getCenter(), 1, 0);
            //Set the root center with the panel of the count position
            root.setCenter(panels.get(count).getPanel(minPrice, maxPrice));
            //Create a fade transition for root's center
            paneFade(root.getCenter(), 0, 1);
        }
    }

    private void paneFade(Node root, int i, int i2) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(100), root);
        fadeOut.setFromValue(i);
        fadeOut.setToValue(i2);
        fadeOut.play();
    }

    //Subsequent acts if the back button is clicked
    private void backButtonClick(ActionEvent event) {
        if (isMinSelected && isMaxSelected) {
            if (count == 0) {
                //Set count as the current position of array list panels minus one
                count = panels.size()-1;
            }
            else {
                //Set count as the current count minus one of the remainder of three
                count = (count - 1) % 3;
            }

            //Create a fade transition for root's center
            paneFade(root.getCenter(), 1, 0);
            //Set the root center with the panel of the count position
            root.setCenter(panels.get(count).getPanel(minPrice, maxPrice));
            //Create a fade transition for root's center
            paneFade(root.getCenter(), 0, 1);
        }
    }
    /**
     * @param boroughName for filtration
     * @param boroughs for later selection of boroughs
     */
    public static void triggerBoroughWindow(String boroughName, ArrayList<Borough> boroughs) {
        //Create a borough window
        BoroughWindow boroughWindow = new BoroughWindow(boroughName, minPrice, maxPrice, boroughs);

        //Set a new scene from borough window pane
        Scene scene = new Scene(boroughWindow.getPane());
        scene.getStylesheets().add("darkMode.css");
        Stage boroughWindowStage = new Stage();
        //Set the title of the stage
        boroughWindowStage.setTitle("Properties of " + boroughName );
        boroughWindowStage.setScene(scene);
        //Set the height and width of the stage which cannot be resized later
        boroughWindowStage.setMaxHeight(boroughWindow.getPane().getMaxHeight());
        boroughWindowStage.setMinWidth(boroughWindow.getPane().getMinWidth());
        boroughWindowStage.setResizable(false);
        //Show the stage
        boroughWindowStage.show();
    }
    /**
     * @param property for selecting the property to be displayed
     * @param list for later selection of properties
     * @param pos for selecting the property at later stage
     */
    public static void triggerPropertyWindow (AirbnbListing property, ArrayList<AirbnbListing> list, int pos) {
        //Create a property window
        PropertyWindow propertyWindow = new PropertyWindow(property, list, favouriteDataLoader,  pos);

        //Set a new scene from borough window pane
        Scene scene = new Scene(propertyWindow.getPane());
        scene.getStylesheets().add("darkMode.css");
        Stage propertyWindowStage = new Stage();

        //Set the title of the stage
        propertyWindowStage.setTitle("");

        propertyWindowStage.setScene(scene);

        //Set the height and width of the stage which cannot be resized later
        propertyWindowStage.setMinHeight(propertyWindow.getPane().getMinHeight());
        propertyWindowStage.setMinWidth(propertyWindow.getPane().getMinWidth());
        //Show the stage
        propertyWindowStage.show();
    }

    public interface InitCompletionHandler {
        void complete();
    }
}
