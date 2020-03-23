import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;

/**
 * GoogleMapPanel is responsible for the integration of streetView panel into the javaFX window
 * In order to do this, we generate unique links for each property when the user clicks on the 'view more'
 * button and this is used when the user wants to look around the location of the property
 */
public class GoogleMapPanel
{
    // Unique API key for Google's static street view API
    public String API_KEY = "AIzaSyAYSnY8BF9kfmVKe-DMTlGMBhvK4KtBjgI";
    // Default URL String - left blank as a url will always be generated
    public static String urlString = "";
    // ArrayList to hold all WebEngines that are loaded when generating a 360 degree image from static street view
    private ArrayList<String> streetViewURLs;
    // Declaring a WebView - the graphical representation of the WebEngine
    public WebView streetView;
    // Declaring a WebEngine - used to load webpages onto the WebView
    public WebEngine streetViewEngine;
    // Declaring a 'preferredWidth' and a 'preferredHeight' for the URL generation method and for the buttons.
    public int preferredWidth;
    public int preferredHeight;
    //streetViewURLs goes from index 0 to index 5 (0 degrees to 300 degrees) - stored globally for easy changing between the street view panels
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
        // Getting the preferred width of the WebView window in order to make the window and button of a proportional height/width
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

        //Making the buttons and adding functionality to them
        Button backButton = new Button("<");
        backButton.setPrefSize(20,preferredHeight);
        backButton.setOnAction(this::backAction);
        Button forwardButton = new Button(">");
        forwardButton.setPrefSize(20,preferredHeight);
        forwardButton.setOnAction(this::forwardAction);

        // Adding buttons into a horizontal box with the StreetView window
        HBox streetViewBox = new HBox();
        streetViewBox.getChildren().addAll(backButton, streetViewPanels.get(0), forwardButton);

        Pane test = streetViewPanels.get(0);

        // Returns the horizontal box containing the WebView and the two buttons used to navigate in the view.
        return streetViewBox;
    }

    /**
     * Generates the urls and stores them into an ArrayList 'streetViewURLs'
     * @param latitude latitudinal value of the property the user has clicked on - used to generate the urls
     * @param longitude longitudinal value of the property the user has clicked on - used to generate the urls
     */
    private void getViews(Double latitude, Double longitude)   {
        // Sets initial heading to North
        int heading = 0;
        // While loop to load new instances of WebEngines every 60 degrees
        while(heading < 360) {
            // Producing a unique link for each location, dimension, and heading
            urlString = "https://maps.googleapis.com/maps/api/streetview?size=" + preferredWidth + "x" + preferredHeight + "&scale=4&location=" +
                    latitude + "," + longitude + "&fov=120&heading=" + heading + "&pitch=0&radius=600&key=" + API_KEY;
            // heading is used to control how many streetview static images there are to form the 360 degree image
            heading += 30;
            // Adding the URLs that have been generated into an ArrayList
            streetViewURLs.add(urlString);
        }
    }

    /**
     * Method is used when the user clicks the button on the left hand side to move 30 degrees to the left
     * @param event Button press moves between the images of the street view mode
     */
    private void backAction(ActionEvent event) {
        if(index - 1 > 0)   { index--; }
        else    { index = streetViewURLs.size() - 1; }
        // Changing the webpage loaded onto the WebView - switching images
        updateStreetViewImage(index);
    }

    /**
     * Method is used when the user clicks the button on the right hand side to move 30 degrees to the right
     * @param event Button press moves between the images of the street view mode
     */
    private void forwardAction(ActionEvent event) {
        if(index + 1 < streetViewURLs.size())   { index++; }
        else    { index = 0; }
        // Changing the webpage loaded onto the WebView - switching images
        updateStreetViewImage(index);
    }

    /**
     * Used to load a different image onto the panel through the previously stored URLs and a global value (index)
     * @param index parsing index through to globally keep the value of which panel the user has clicked to on street view
     */
    public void updateStreetViewImage(int index){ streetViewEngine.load(streetViewURLs.get(index)); }
}