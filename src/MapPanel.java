import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;

import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class MapPanel extends Panel
{
    // Scale factor for the circles
    private double scale;
    // Horizontal shift factor the circles
    private double hShift;

    public MapPanel(ArrayList<AirbnbListing> loadedListings) throws IOException
    {
        super(loadedListings);
        // Define scaling and shifting values
        scale = 0.55;
        hShift = 100;
    }

    /**
     * @param minPrice minimum price for filtering
     * @param maxPrice maximum price for filtering
     * @return The number of listings of the most populated Borough
     */
    private int getMax(int minPrice, int maxPrice)
    {
        ArrayList<Integer> tempStorage = new ArrayList<>();
        // Adds the number of listings of each borough into an ArrayList
        for (Borough borough : boroughs) {
            tempStorage.add(borough.getNumberOfListings(minPrice, maxPrice));
        }
        // Returns the maximum value in the ArrayList
        return Collections.max(tempStorage);
    }

    /**
     * Mathematically calculate the Color based on the number of listings in the borough
     * The Color's saturation generated relatively
     * The more populated it is, the more saturated the color
     * @param number Number of listings the borough has
     * @param max The number of listings the most populated borough has
     * @return The generated Color
     */
    private Color getFillColor(double number, double max)
    {
        double redValue = 255 - number/ max * 13;
        double greenValue = 255 - number / max * 169;
        double blueValue = 255 - number / max * 163;
        return Color.rgb((int) redValue, (int) greenValue, (int)blueValue);
    }

    /**
     * Creation of Map Panel
     * @param minPrice Selected minimum price for filtering
     * @param maxPrice Selected maximum price for filtering
     * @return The constructed pane
     */
    @Override
    public Pane getPanel(int minPrice, int maxPrice)
    {
        Pane mapPane = new Pane();
        for (Borough borough : boroughs) {
            // Get the values needed for the dynamic color generation
            double numberOfListings = borough.getNumberOfListings(minPrice, maxPrice);
            double maxNumberOfListings = getMax(minPrice, maxPrice);

            // Get the color of this Borough
            Color color = getFillColor(numberOfListings, maxNumberOfListings);

            // Create the circle and fill it with the generated color
            Circle circle = new Circle(borough.getRadius(scale));
            circle.setFill(color);
            circle.setId("circleBG");

            // Get the abbreviated name of borough and scale it
            Text abbrevName = new Text(borough.getAbbrevName());
            abbrevName.setFont(new Font(20 * scale));
            abbrevName.setBoundsType(TextBoundsType.VISUAL);

            // Create a stack pane that houses the circle and the abbreviated name
            StackPane stack = new StackPane();
            // repositions the StackPane
            stack.relocate(borough.getX(scale) - hShift, borough.getY(scale));
            stack.getChildren().addAll(circle, abbrevName);
            // Set action when StackPane is pressed
            stack.setOnMousePressed(e -> {
                ApplicationWindow.triggerBoroughWindow(borough.getName(), boroughs);
            });
            stack.setId("circle");

            // Add the StackPane into mapPane
            mapPane.getChildren().add(stack);
        }
        // Add Stylesheet
        mapPane.getStylesheets().add("darkMode.css");
        return mapPane;
    }
}
