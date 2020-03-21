import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.util.ArrayList;

/**
 * GoogleMapPanel is responsible for the native javaFX integration of streetView into a javaFX Window
 * GoogleMapPanel is used when the user clicks on a property for further details in the MapPanel's PropertyWindow,
 * and then displays the closest available streetView location for the user to look at the surroundings/the property itself
 */
public class GoogleMapPanel
{
    // Unique API key for Google's static street view API
    public String API_KEY = "AIzaSyAYSnY8BF9kfmVKe-DMTlGMBhvK4KtBjgI";
    // Default URL String
    public static String urlString = "";
    // ArrayList to hold all WebEngines that are loaded when generating a 360 degree image from static street view
    private ArrayList<String> streetViewURLs;
    // Creating a new WebView - the graphical representation of the URLs loaded into the WebEngine
    public WebView streetView;
    // Creating a new WebEngine - used to load web pages into the WebView
    public WebEngine streetViewEngine;
    // Creates two global integers used to get the preferred dimensions of the streetView Pane
    public int preferredWidth;
    public int preferredHeight;
    // Global value to store which heading/pane the user is currently at.
    // streetViewURLs goes from index 0 to index 11 (0 degrees to 330 degrees)
    public int index = 0;

    /**
     * Initiates the class by creating a new WebView object and a new WebEngine object
     * Also loads preferredWidth/Height with a value and creates an array to store the URLs needed for streetView functionality
     */
    public GoogleMapPanel(){
        // Creates a new WebView
        streetView = new WebView();
        // Creates a new WebEngine
        streetViewEngine = new WebEngine();
        // Links WebEngine to the WebView
        streetViewEngine = streetView.getEngine();
        // Gets a value for the preferredWidth/Height for use in url generation and in button generation
        preferredWidth = (int) streetView.getPrefWidth();
        preferredHeight = (int) streetView.getPrefHeight();
        // Initialises array to store the urls generated
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
        // Generating the URLs
        getViews(latitude, longitude);
        // Loading the default streetView image (directly north)
        streetViewEngine.load(streetViewURLs.get(index));

        // Making the background transparent
        WebPage webPage = Accessor.getPageFor(streetViewEngine);
        webPage.setBackgroundColor(0);

        //Making the buttons and assigning actions to these buttons
        Button backButton = new Button("<");
        backButton.setPrefSize(20,preferredHeight);
        backButton.setOnAction(this::backAction);
        Button forwardButton = new Button(">");
        forwardButton.setPrefSize(20,preferredHeight);
        forwardButton.setOnAction(this::forwardAction);

        // Adding buttons into a horizontal box with the StreetView window
        HBox streetViewBox = new HBox();
        streetViewBox.getChildren().addAll(backButton, streetView, forwardButton);

        // Returns the horizontal box containing the two buttons for navigation and the streetView Pane itself
        return streetViewBox;
    }

    /**
     * Method generates URLs and stores them into an array for switching the image between them when the user has clicked
     * a button on the left or the right.
     * URLs generated are used for the streetView functionality.
     * @param latitude is the latitudinal number for the property listing that the user has clicked on - used for url generation
     * @param longitude is the longitudinal number for the property listing that the user has clicked on - used for url generation
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
            // Storing the URLs into an array to be loaded when the user has clicked on a property or a button within the detailed window
            streetViewURLs.add(urlString);
        }
    }

    /**
     * Action to move the street view image 30 degrees to the left
     * @param event for button click, changes street view panel
     */
    private void backAction(ActionEvent event) {
        if(index - 1 > 0)   { index--; }
        else    { index = streetViewURLs.size() - 1; }
        // Updating the panel with the updated image
        updateStreetViewImage(index);
    }

    /**
     * Action to move the street view image 30 degrees to the right
     * @param event for button click, changes street view panel
     */
    private void forwardAction(ActionEvent event) {
        if(index + 1 < streetViewURLs.size())   { index++; }
        else    { index = 0; }
        // Updating the panel with the updated image
        updateStreetViewImage(index);
    }

    /**
     * Method to load a different url onto the WebView (changing the streetView Pane/Image)
     * @param index global variable that stores which pane the user is currently on (clicking buttons etc.)
     */
    public void updateStreetViewImage(int index){ streetViewEngine.load(streetViewURLs.get(index)); }
}