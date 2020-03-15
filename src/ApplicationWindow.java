import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
    private int count = 0;
    private BorderPane root= new  BorderPane();
    private ComboBox<String> minComboBox = new ComboBox();
    private ComboBox<String> maxComboBox = new ComboBox();

    private static int minPrice;
    private static int maxPrice;
    private boolean minSelected;
    private boolean maxSelected;

    private static Pane centerPanel;
    private Pane splashLayout;
    private ProgressIndicator loadProgress;
    private Label loadText;

    private Panel welcome = new WelcomePanel();

    private ArrayList<Panel> panels;

    private static FavouriteDataLoader favouriteDataLoader = new FavouriteDataLoader();

    public ApplicationWindow() throws IOException {
    }

    @Override
    public void init() {
        File splashScreenImageFile = new File("img/splash-screen.png");
        Image splashScreenImage = new Image(splashScreenImageFile.toURI().toString());
        ImageView splashScreen = new ImageView(splashScreenImage);

        loadProgress = new ProgressIndicator();
        loadText = new Label();
        loadText.getStyleClass().add("whiteText");

        VBox overlay = new VBox();
        overlay.getStyleClass().add("overlayPane");
        overlay.getChildren().addAll(loadProgress, loadText);

        splashLayout = new StackPane();
        splashLayout.getChildren().addAll(splashScreen, overlay);
    }

    @Override
    public void start(final Stage initStage) throws FileNotFoundException {
        Task<ArrayList<Panel>> createPanels = new Task<ArrayList<Panel>>() {
            @Override
            protected ArrayList<Panel> call() throws InterruptedException, IOException {
                ArrayList<Panel> panels = new ArrayList<>();
                updateMessage("Loading Map Panel ...");
                panels.add(new MapPanel());

                updateMessage("Loading Stats Panel");
                panels.add(new StatsPanel());

                updateMessage("Loading Account Panel");
                panels.add(new UserPanel());

                updateMessage("Application Starting");
                Thread.sleep(300);

                return panels;
            }
        };

        showSplash(initStage, createPanels, () -> showMainStage(createPanels.getValue()));
        new Thread(createPanels).start();
    }

    private void showMainStage(ArrayList<Panel> loadedPanels) {

        panels = loadedPanels;

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
                        centerPanel = panels.get(0).getPanel(minPrice,maxPrice);
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
                        centerPanel = panels.get(0).getPanel(minPrice,maxPrice);
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
        scene.getStylesheets().add("darkMode.css");
        scene.getStylesheets().add("styles.css");

        Stage mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("Application Window");
        mainStage.setScene(scene);
        mainStage.setMinHeight(centerPanel.getHeight());
        mainStage.setMinWidth(centerPanel.getWidth());
        mainStage.setResizable(false);
        mainStage.show();
    }

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

    private void backButtonClick(ActionEvent event) {
        if (minSelected && maxSelected) {
            if (count == 0) {
                count = panels.size()-1;
            }
            else {
                count = (count - 1) % 3;
            }
            FadeTransition fadeOut = new FadeTransition(Duration.millis(100),root.getCenter());
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.play();
            root.setCenter(panels.get(count).getPanel(minPrice, maxPrice));
            FadeTransition fadeIn = new FadeTransition(Duration.millis(100),root.getCenter());
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        }
    }

    private void forwardButtonClick(ActionEvent event) {
        if (minSelected && maxSelected) {
            count = (count + 1) % 3;
            FadeTransition fadeOut = new FadeTransition(Duration.millis(100), root.getCenter());
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.play();
            root.setCenter(panels.get(count).getPanel(minPrice, maxPrice));
            FadeTransition fadeIn = new FadeTransition(Duration.millis(100), root.getCenter());
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        }
    }

    public static void triggerBoroughWindow(String boroughName, ArrayList<Borough> boroughs) {
        BoroughWindow boroughWindow = new BoroughWindow(boroughName, minPrice, maxPrice, boroughs);

        Scene scene = new Scene(boroughWindow.getPane());
        scene.getStylesheets().add("darkMode.css");
        Stage boroughWindowStage = new Stage();
        boroughWindowStage.setTitle("Properties of " + boroughName );
        boroughWindowStage.setScene(scene);
        boroughWindowStage.setMaxHeight(boroughWindow.getPane().getMaxHeight());
        boroughWindowStage.setMinWidth(boroughWindow.getPane().getMinWidth());
        boroughWindowStage.setResizable(false);
        boroughWindowStage.show();
    }

    public static void triggerPropertyWindow (AirbnbListing property, ArrayList<AirbnbListing> list, int pos) {
        PropertyWindow propertyWindow = new PropertyWindow(property, list, favouriteDataLoader,  pos);

        Scene scene = new Scene(propertyWindow.getPane());
        scene.getStylesheets().add("darkMode.css");
        Stage propertyWindowStage = new Stage();
        propertyWindowStage.setTitle("");
        propertyWindowStage.setScene(scene);
        propertyWindowStage.setMinHeight(propertyWindow.getPane().getMinHeight());
        propertyWindowStage.setMinWidth(propertyWindow.getPane().getMinWidth());
        propertyWindowStage.setResizable(false);
        propertyWindowStage.show();
    }

    public interface InitCompletionHandler {
        void complete();
    }
}
