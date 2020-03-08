import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PropertyWindow{

    private Button left;
    private Button right;

    private BorderPane navigation;
    private BorderPane popUpPane;

    private AirbnbListing property;

    private ArrayList<AirbnbListing> listings;

    private int position;

    public PropertyWindow(AirbnbListing property, ArrayList<AirbnbListing> list, int pos) {

        left = new Button("Left");
        right = new Button("Right");

        navigation = new BorderPane();
        navigation.setLeft(left);
        navigation.setRight(right);

        popUpPane = new BorderPane();

        this.property = property;
        this.listings = list;
        this.position = pos;

        buildWindow();
    }

    private void buildWindow() {
        navigation.setLeft(left);
        navigation.setRight(right);
        popUpPane.setBottom(navigation);

        left.setOnMouseClicked(e -> {
            if (position != 0) {
                position--;
                property = listings.get(position);
                popUpPane.setCenter(loadContent(property));
            }
        });

        right.setOnMouseClicked(e -> {
            if (position != listings.size()) {
                position++;
                property = listings.get(position);
                popUpPane.setCenter(loadContent(property));
            }
        });
    }

    private Pane loadContent(AirbnbListing property) {
        Pane pane = new Pane();
        Text text = new Text(property.getName());
        pane.getChildren().add(text);
        return pane;
    }

    public Pane getPane() {
        return popUpPane;
    }
}