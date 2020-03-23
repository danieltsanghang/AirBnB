import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.Panel;
import java.net.*;
import java.util.ArrayList;

public class GoogleMapPanel
{
    // Unique API key for Google's static street view API
    public String API_KEY = "AIzaSyAYSnY8BF9kfmVKe-DMTlGMBhvK4KtBjgI";
    // Default URL String
    public static String urlString = "";
    // ArrayList to hold all WebEngines that are loaded when generating a 360 degree image from static street view
    private ArrayList<Pane> streetViewPanels;
    private ArrayList<String> streetViewURLs;

    // Declaring variables
    public WebEngine streetViewEngine;
    public WebView streetView;


    /**
     * Initiates the class by creating a new WebView object and a new WebEngine object
     */
    public GoogleMapPanel(){
        streetView = new WebView();
        streetViewEngine = new WebEngine();
        streetViewEngine = streetView.getEngine();
    }

    /**
     * Loads the static web view api into specific locations of the properties that the user
     * wants to look at.
     * @param latitude latitudinal location of the property that the user wants to look at
     * @param longitude longitudinal location of the property that the user wants to look at
     * @return pane that contains the street view image(s) and buttons to switch between the images
     */
    public Pane start(Double latitude, Double longitude){
        // Sets initial heading to North
        int heading = 0;
        // Creates an ArrayList to store the WebEngines loaded
        streetViewPanels = new ArrayList<>();
        // Gets the preferred dimensions of the WebView panel
        int preferredWidth = (int) streetView.getPrefWidth();
        int preferredHeight = (int) streetView.getPrefHeight();

        streetViewURLs = new ArrayList<>();
        // While loop to load new instances of WebEngines every 60 degrees
        while(heading < 360) {
            // Producing a unique link for each location, dimension, and heading
            urlString = "https://maps.googleapis.com/maps/api/streetview?size=" + preferredWidth + "x" + preferredHeight + "&scale=4&location=" +
                    latitude + "," + longitude + "&fov=120&heading=" + heading + "&pitch=0&radius=600&key=" + API_KEY;
            heading += 60;
            streetViewURLs.add(urlString);
        }

        int i = 0;

        while(i < streetViewURLs.size()){
            // Loading and storing the unique street view urls
            streetViewEngine.load(streetViewURLs.get(i));
            streetViewPanels.add(new Pane(streetView));
            i++;
        }

        System.out.println(streetViewPanels.size());

        // Making the background transparent
        WebPage webPage = Accessor.getPageFor(streetViewEngine);
        webPage.setBackgroundColor(0);

        int index = 0; //streetViewPanels goes from index 0 to index 5 (0 degrees to 300 degrees)

        //Making the buttons
        Button backButton = new Button("<");
        backButton.setPrefSize(20,preferredHeight);
        Button forwardButton = new Button(">");
        forwardButton.setPrefSize(20,preferredHeight);

        // Adding buttons into a hbox with the StreetView window
        HBox streetViewBox = new HBox();
        streetViewBox.getChildren().addAll(backButton, streetViewPanels.get(0), forwardButton);

        Pane test = streetViewPanels.get(0);

        return test;
    }
}