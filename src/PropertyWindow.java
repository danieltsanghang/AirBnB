import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
        Pane content = loadContent(property);
        popUpPane.setBottom(navigation);
        popUpPane.setCenter(content);
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
        popUpPane.setMinSize(500,300);
    }


//    private Pane loadContent(AirbnbListing property) {
//        BorderPane root = new BorderPane();
//        Text text = new Text(property.getName());
//        root.setTop(text);
//
//        VBox container = new VBox();
//        Label hostNameBox = new Label("Host Name: " + property.getHost_name());
//        Label idBox = new Label("ID: " + property.getId());
//        Label nameBox = new Label(" Name: " + property.getName());
//        Label hostIdBox = new Label("Host ID: " + property.getHost_id());
//        Label neighbourhoodBox = new Label("Neighbourhood: " + property.getNeighbourhood());
//        Label latitudeBox = new Label("Latitude: " + Double.toString(property.getLatitude()));
//        Label longitudeBox = new Label("Longitude: " + Double.toString(property.getLongitude()));
//        Label priceBox = new Label("Price: " + String.valueOf(property.getPrice()));
//        Label minimumNightsBox = new Label("Minimum Night: " + String.valueOf(property.getMinimumNights()));
//        Label reviewBox = new Label("Number of reviews: " + String.valueOf(property.getNumberOfReviews()));
//        Label lastReviewBox = new Label("Last Review: " + property.getLastReview());
//        Label reviewsPerMonthBox = new Label("Reviews Per Month: " + Double.toString(property.getReviewsPerMonth()));
//        Label calculatedHostListingsCountBox = new Label("Host Listings: " + String.valueOf(property.getCalculatedHostListingsCount()));
//
//        container.getChildren().addAll(hostNameBox, idBox, nameBox, hostIdBox, neighbourhoodBox,
//                latitudeBox, longitudeBox, priceBox, minimumNightsBox, reviewBox,
//                lastReviewBox, reviewsPerMonthBox, calculatedHostListingsCountBox);
//        container.getStyleClass().add("container");
//        root.setRight(container);
//        root.setMinSize(container.getMinWidth(), container.getMinHeight());
//        return root;
//    }
//        private void checkIsFavourite (AirbnbListing property){
//            isFav = false;
//            favBtn.setGraphic(emptyHeart);
//            for (AirbnbListing listing : favourites) {
//                if (listing.getId().equals(property.getId())) {
//                    isFav = true;
//                }
//            }
//            if (isFav) {
//                favBtn.setGraphic(filledHeart);
//            }
//        }

        private Pane loadContent (AirbnbListing property)   {

            HBox pane = new HBox();
            Pane googleMapPane = new Pane();
            BorderPane right = new BorderPane();
            GridPane contentGrid = new GridPane();
            GoogleMapPanel gog = new GoogleMapPanel();
//
//            favBtn.setOnAction(e -> {
//                if (login) {
//                    if (!isFav) {
//                        try {
//                            user.newFavourite(property);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                        favBtn.setGraphic(filledHeart);
//                    } else {
//                        try {
//                            user.removeFavourite(property);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                        favBtn.setGraphic(emptyHeart);
//                    }
//                    isFav = !isFav;
//                } else {
//                    Alert loginRequired = new Alert(Alert.AlertType.INFORMATION);
//                    loginRequired.setTitle("Login Required");
//                    loginRequired.setHeaderText("An account is required to favourite properties.");
//                    loginRequired.setContentText("Please head to login page to login or create an account.");
//                    loginRequired.showAndWait();
//                }
//            });

            contentGrid.getStyleClass().add("contentGrid");
            contentGrid.add(new Label("Host Name:"), 0, 0);
            contentGrid.add(new Label(property.getHost_name()), 1, 0);
            contentGrid.add(new Label("ID:"), 0, 1);
            contentGrid.add(new Label(property.getId()), 1, 1);
            contentGrid.add(new Label(" Name:"), 0, 2);
            contentGrid.add(new Label(property.getName()), 1, 2);
            contentGrid.add(new Label("Host ID:"), 0, 3);
            contentGrid.add(new Label(property.getHost_id()), 1, 3);
            contentGrid.add(new Label("Borough:"), 0, 4);
            contentGrid.add(new Label(property.getNeighbourhood()), 1, 4);
            contentGrid.add(new Label("Price:"), 0, 5);
            contentGrid.add(new Label(property.getPrice() + ""), 1, 5);
            contentGrid.add(new Label("Minimum Night:"), 0, 6);
            contentGrid.add(new Label(property.getMinimumNights() + ""), 1, 6);
            contentGrid.add(new Label("Number of reviews:"), 0, 7);
            contentGrid.add(new Label(property.getNumberOfReviews() + ""), 1, 7);
            contentGrid.add(new Label("Last Review:"), 0, 8);
            contentGrid.add(new Label(property.getLastReview()), 1, 8);
            contentGrid.add(new Label("Host Listings:"), 0, 9);
            contentGrid.add(new Label(property.getCalculatedHostListingsCount() + ""), 1, 9);

//            right.setTop(favBtn);
            right.setCenter(contentGrid);
            try {
                right.setLeft(gog.start(property.getLatitude(), property.getLongitude()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pane.getChildren().addAll(googleMapPane, right);
            return pane;

        }

    public Pane getPane() {
        return popUpPane;
    }
}
