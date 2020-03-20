import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;

public class GoogleMapPanel
{
    // Unique API key for Google's static street view API
    public String API_KEY = "AIzaSyAYSnY8BF9kfmVKe-DMTlGMBhvK4KtBjgI";
    // Default URL String
    public static String urlString = "";
    // ArrayList to hold all WebEngines that are loaded when generating a 360 degree image from static street view
    private ArrayList<String> streetViewURLs;

    // Declaring variables
    public WebEngine streetViewEngine;
    public WebView streetView;
    public int preferredWidth;
    public int preferredHeight;
    //streetViewURLs goes from index 0 to index 5 (0 degrees to 300 degrees)
    public int index = 0;


    /**
     * Initiates the class by creating a new WebView object and a new WebEngine object
     */
    public GoogleMapPanel(){
        // Creates a new WebView
        streetView = new WebView();
        // Creates a new WebEngine
        streetViewEngine = new WebEngine();
        // Links WebEngine to the WebView
        streetViewEngine = streetView.getEngine();

        preferredWidth = (int) streetView.getPrefWidth();
        preferredHeight = (int) streetView.getPrefHeight();
        // Create an array to store the urls generated
        streetViewURLs = new ArrayList<>();
    }

    /**
     * Loads the static web view api into specific locations of the properties that the user
     * wants to look at.
     * @param latitude latitudinal location of the property that the user wants to look at
     * @param longitude longitudinal location of the property that the user wants to look at
     * @return pane that contains the street view image(s) and buttons to switch between the images
     */
    public Pane start(Double latitude, Double longitude){
        getViews(latitude, longitude);

        streetViewEngine.load(streetViewURLs.get(index));
        // Making the background transparent
        WebPage webPage = Accessor.getPageFor(streetViewEngine);
        webPage.setBackgroundColor(0);

        //Making the buttons
        Button backButton = new Button("<");
        backButton.setPrefSize(20,preferredHeight);
        backButton.setOnAction(this::backAction);
        Button forwardButton = new Button(">");
        forwardButton.setPrefSize(20,preferredHeight);
        forwardButton.setOnAction(this::forwardAction);

        // Adding buttons into a hbox with the StreetView window
        HBox streetViewBox = new HBox();
        streetViewBox.getChildren().addAll(backButton, streetView, forwardButton);

        return streetViewBox;
    }

    private void getViews(Double latitude, Double longitude)   {
        // Sets initial heading to North
        int heading = 0;

        // While loop to load new instances of WebEngines every 60 degrees
        while(heading < 360) {
            // Producing a unique link for each location, dimension, and heading
            urlString = "https://maps.googleapis.com/maps/api/streetview?size=" + preferredWidth + "x" + preferredHeight + "&scale=4&location=" +
                    latitude + "," + longitude + "&fov=120&heading=" + heading + "&pitch=0&radius=600&key=" + API_KEY;
            heading += 30;
            streetViewURLs.add(urlString);
        }
    }
    private void backAction(ActionEvent event) {
        if(index - 1 > 0)   {
            index--;
        }
        else    {
            index = streetViewURLs.size() - 1;
        }
        updateStreetViewImage(index);
    }
    private void forwardAction(ActionEvent event) {
        if(index + 1 < streetViewURLs.size())   {
            index++;
        }
        else    {
            index = 0;
        }
        updateStreetViewImage(index);
    }

    public void updateStreetViewImage(int index){
        streetViewEngine.load(streetViewURLs.get(index));
    }
}