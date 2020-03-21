import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;

public class PropertyWindow {

    private Button left;
    private Button right;

    private BorderPane navigation;
    private BorderPane popUpPane;

    private AirbnbListing property;

    private ArrayList<AirbnbListing> listings;

    private FavouriteDataLoader favouriteDataLoader;

    private int position;

    private ImageView filledHeart;
    private ImageView emptyHeart;
    private Button favBtn;
    private boolean isFav;

    public PropertyWindow(AirbnbListing property, ArrayList<AirbnbListing> list, FavouriteDataLoader favDataLoader, int pos) {

        File filledHeartFile = new File ("icons/filledHeart.png");
        Image filledHeartImage = new Image(filledHeartFile.toURI().toString());
        filledHeart = new ImageView(filledHeartImage);

        File emptyHeartFile = new File ("icons/emptyHeart.png");
        Image emptyHeartImage = new Image(emptyHeartFile.toURI().toString());
        emptyHeart = new ImageView(emptyHeartImage);

        left = new Button("Left");
        right = new Button("Right");

        navigation = new BorderPane();
        navigation.setLeft(left);
        navigation.setRight(right);

        popUpPane = new BorderPane();
        this.property = property;
        this.listings = list;
        this.position = pos;

        favBtn = new Button();
        favBtn.setMaxHeight(40);
        favBtn.setGraphic(emptyHeart);
        isFav = false;

        favouriteDataLoader = favDataLoader;

        buildWindow();
    }

    private void buildWindow() {
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
        checkIsFavourite(property);
        popUpPane.setMinSize(500, 300);
    }

    private void checkIsFavourite(AirbnbListing property) {
        isFav = false;
        favBtn.setGraphic(emptyHeart);
        for (AirbnbListing listing : favouriteDataLoader.getFavourites(listings)) {
            if (listing.getId().equals(property.getId())) {
                isFav = true;
            }
        }
        if (isFav) {
            favBtn.setGraphic(filledHeart);
        }
    }

    private Pane loadContent(AirbnbListing property) {

        HBox pane = new HBox();
        BorderPane right = new BorderPane();
        GridPane contentGrid = new GridPane();
        GoogleMapPanel gog = new GoogleMapPanel();

        favBtn.setOnAction(e -> {
            if (!isFav) {
                try {
                    favouriteDataLoader.newFavourite(property.getId());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                favBtn.setGraphic(filledHeart);
            } else {
                try {
                    favouriteDataLoader.removeFavourite(property.getId());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                favBtn.setGraphic(emptyHeart);
            }
            isFav = !isFav;
        });

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

        //Set the top, center of the right panel
        right.setTop(favBtn);
        right.setCenter(contentGrid);

        pane.getChildren().addAll(gog.start(property.getLatitude(), property.getLongitude()), right);

        return pane;
    }

    public Pane getPane() {
        return popUpPane;
    }
}

