import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import java.io.File;

import java.util.ArrayList;

public class PropertyWindow {

    //Create two buttons that allow user to look through different properties in this window
    private Button left = new Button("Left");
    private Button right = new Button("Right");

    //Creat two border pane navigation and popUpPane
    private BorderPane navigationPane = new BorderPane();
    private BorderPane popUpPane = new BorderPane();

    private AirbnbListing property ;

    //Create an array list listings that store all  the airbnb listings
    private ArrayList<AirbnbListing> listings;

    //Create a favourite data loader for loading and returning favourites selected by the user
    private FavouriteDataLoader favouriteDataLoader;

    private int position;

    //Create two imageviews filled heart and empty heart for identifying whether the property is selected as favourite
    private ImageView filledHeart;
    private ImageView emptyHeart;

    //Create a button favBtn for the user to mark the property as favourite by the user
    private Button favBtn = new Button();
    private boolean isFav;

    public PropertyWindow(AirbnbListing property, ArrayList<AirbnbListing> list, int pos) {

        //Loads the filled heart image based on the provided URL
        //Converts File to ImageView using Image
        File filledHeartFile = new File ("icons/filledHeart.png");
        Image filledHeartImage = new Image(filledHeartFile.toURI().toString());
        filledHeart = new ImageView(filledHeartImage);

        //Loads the empty heart image based on the provided URL
        //Converts File to ImageView using Image
        File emptyHeartFile = new File ("icons/emptyHeart.png");
        Image emptyHeartImage = new Image(emptyHeartFile.toURI().toString());
        emptyHeart = new ImageView(emptyHeartImage);

        //Set the left side of the navigation pane with the button left
        navigationPane.setLeft(left);
        //Set the right side of the navigation pane with the button left
        navigationPane.setRight(right);

        this.property = property;
        this.listings = list;
        this.position = pos;

        //Set the id of the favourite button for later css editing
        favBtn.setId("favBtn");

        //Set the favourite button as empty heart and set it as not favourite for now
        favBtn.setGraphic(emptyHeart);
        isFav = false;

        favouriteDataLoader = new FavouriteDataLoader();

        buildWindow();
    }

    private void buildWindow() {
        Pane content = loadContent(property);
        popUpPane.setBottom(navigationPane);
        popUpPane.setCenter(content);

        //Set the subsequent acts when the left button is clicked
        left.setOnMouseClicked(e -> {
            position = (position - 1) % listings.size();
            //get the listing of the new position
            property = listings.get(position);
            //set the new property as the pop up pane content
            popUpPane.setCenter(loadContent(property));
        });

        //Set the subsequent acts when the right button is clicked
        right.setOnMouseClicked(e -> {
            position = (position + 1) % listings.size();
            //get the listing of the new position
            property = listings.get(position);
            //set the new property as the pop up pane content
            popUpPane.setCenter(loadContent(property));
        });
        checkIsFavourite(property);
        popUpPane.setMinSize(500, 300);
    }

    /**
     * @param property for checking whether the property is favourite
     */
    private void checkIsFavourite(AirbnbListing property) {
        isFav = false;
        //Set the favourite button as empty heart
        favBtn.setGraphic(emptyHeart);

        ArrayList<String> favID = favouriteDataLoader.getFavourites();
        //Check whether the listing is marked by the user as favourite
        for (String id : favID) {
            if (id.equals(property.getId())) {
                isFav = true;
                break;
            }
        }

        //if it is favourite, set the favourite button as filled heart
        if (isFav) {
            favBtn.setGraphic(filledHeart);
        }
    }

    /**
     * @param property for later filtering
     * @return pane
     */
    private Pane loadContent(AirbnbListing property) {

        //Create a HBox
        HBox pane = new HBox();
        //Create a border pane
        BorderPane right = new BorderPane();
        //Create a content Grid
        GridPane contentGrid = new GridPane();
        //Create a googleMapPanel
        GoogleMapPanel gog = new GoogleMapPanel();

        //Set the subsequent acts when the favourite button is clicked
        favBtn.setOnAction(e -> {
            if (!isFav) {
                favouriteDataLoader.newFavourite(property.getId());
                //Set the favourite button with filled heart
                favBtn.setGraphic(filledHeart);
            }
            else {
                favouriteDataLoader.removeFavourite(property.getId());
                //Set the favourite button with filled heart
                favBtn.setGraphic(emptyHeart);
            }
            //Reverse whether the property is favourite
            isFav = !isFav;
        });

        //Add labels and corresponding values of the property's attributes
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
        right.setMaxWidth(300);
        right.setMinWidth(300);
        pane.getChildren().addAll(gog.start(property.getLatitude(), property.getLongitude()), right);


        return pane;
    }

    /**
     * @return popUpPane
     */
    public Pane getPane() {
        return popUpPane;
    }
}
