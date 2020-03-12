import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
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
//        navigation.setLeft(left);
//        navigation.setRight(right);
<<<<<<< Updated upstream
=======



        Pane content = loadContent(property);
>>>>>>> Stashed changes
        popUpPane.setBottom(navigation);
        popUpPane.setCenter(loadContent(property));
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

        GoogleMapPanel streetView = new GoogleMapPanel();


        BorderPane root = new BorderPane();
        Text text = new Text(property.getName());
        root.setTop(text);

        VBox container = new VBox();
        Label idBox = new Label("ID: "+ property.getId());
        Label nameBox = new Label(" Name: "+property.getName());
        Label hostIdBox = new Label("Host ID: "+property.getHost_id());
        Label neighbourhoodBox = new Label("Neighbourhood: "+property.getNeighbourhood());
        Label latitudeBox = new Label("Latitude: "+Double.toString(property.getLatitude()));
        Label longitudeBox = new Label("Longitude: "+Double.toString(property.getLongitude()));
        Label priceBox = new Label("Price: "+String.valueOf(property.getPrice()));
        Label minimumNightsBox = new Label("Minimum Night: "+String.valueOf(property.getMinimumNights()));
        Label reviewBox = new Label("Number of reviews: "+String.valueOf(property.getNumberOfReviews()));
        Label lastReviewBox = new Label("Last Review: "+property.getLastReview());
        Label reviewsPerMonthBox = new Label("Reviews Per Month: "+Double.toString(property.getReviewsPerMonth()));
        Label calculatedHostListingsCountBox = new Label("Host Listings: "+String.valueOf(property.getCalculatedHostListingsCount()));

        container.getChildren().addAll(idBox,nameBox,hostIdBox,neighbourhoodBox,latitudeBox,longitudeBox,priceBox,minimumNightsBox,reviewBox,lastReviewBox,reviewsPerMonthBox,calculatedHostListingsCountBox);
        container.getStyleClass().add("container");
        root.setRight(container);
<<<<<<< Updated upstream
//        root.getStylesheets().add("style_popupproperty.css");
=======
        root.setLeft(streetView.start(property.getLatitude(),property.getLongitude()));
        root.setMinSize(container.getMinWidth(),container.getMinHeight());
>>>>>>> Stashed changes
        return root;
    }

    public Pane getPane() {
        return popUpPane;
    }
}
