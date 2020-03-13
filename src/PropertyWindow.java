import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private BorderPane popUpPane;

    private AirbnbListing property;
    private ArrayList<AirbnbListing> listings;
    private ArrayList<AirbnbListing> favourites;

    private int position;

    private boolean login;
    private Account user;

    private ImageView filledHeart;
    private ImageView emptyHeart;
    private Button favBtn;
    private boolean isFav;

    public PropertyWindow(AirbnbListing property, ArrayList<AirbnbListing> list, int pos, Account user) {

        File filledHeartImageFile = new File("icons/filledHeart.png");
        Image filledHeartImage = new Image(filledHeartImageFile.toURI().toString());
        filledHeart = new ImageView(filledHeartImage);

        File emptyHeartImageFile = new File("icons/emptyHeart.png");
        Image emptyHeartImage = new Image(emptyHeartImageFile.toURI().toString());
        emptyHeart = new ImageView(emptyHeartImage);

        this.property = property;
        this.listings = list;
        this.position = pos;
        this.login = (user != null);
        this.user = user;

        favBtn = new Button();
        favBtn.setMaxHeight(40);
        favBtn.setGraphic(emptyHeart);
        isFav = false;
        if (this.login) {
            this.favourites = user.getFavourites();
            checkIsFavourite(property);
        }

        popUpPane = new BorderPane();
        buildWindow();
    }

    private void buildWindow() {
        Pane content = loadContent(property);

        HBox navigation = new HBox();

        Button left = new Button("Left");
        Button right = new Button("Right");

        navigation.getChildren().addAll(left, right);

        popUpPane.setBottom(navigation);
        popUpPane.setCenter(content);

        left.setOnMouseClicked(e -> {
            if (position != 0) {
                position--;
                property = listings.get(position);
                checkIsFavourite(property);
                popUpPane.setCenter(loadContent(property));
            }
        });

        right.setOnMouseClicked(e -> {
            if (position != listings.size()) {
                position++;
                property = listings.get(position);
                checkIsFavourite(property);
                popUpPane.setCenter(loadContent(property));
            }
        });
        popUpPane.setMinSize(500,300);
    }

    private void checkIsFavourite(AirbnbListing property) {
        isFav = false;
        favBtn.setGraphic(emptyHeart);
        for (AirbnbListing listing : favourites) {
            if (listing.getId().equals(property.getId())) {
                isFav = true;
            }
        }
        if (isFav) {
            favBtn.setGraphic(filledHeart);
        }
    }

    private Pane loadContent(AirbnbListing property){

        HBox pane = new HBox();
        Pane googleMapPane = new Pane();
        BorderPane right = new BorderPane();
        GridPane contentGrid = new GridPane();
        GoogleMapPanel gog = new GoogleMapPanel();

        favBtn.setOnAction(e -> {
            if (login) {
                if (!isFav) {
                    try {
                        user.newFavourite(property);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    favBtn.setGraphic(filledHeart);
                }
                else {
                    try {
                        user.removeFavourite(property);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    favBtn.setGraphic(emptyHeart);
                }
                isFav = !isFav;
            }
            else {
                Alert loginRequired = new Alert(Alert.AlertType.INFORMATION);
                loginRequired.setTitle("Login Required");
                loginRequired.setHeaderText("An account is required to favourite properties.");
                loginRequired.setContentText("Please head to login page to login or create an account.");
                loginRequired.showAndWait();
            }
        });

        contentGrid.getStyleClass().add("contentGrid");
        contentGrid.add(new Label("Host Name:"), 0, 0); contentGrid.add(new Label (property.getHost_name()), 1, 0);
        contentGrid.add(new Label("ID:"), 0, 1); contentGrid.add(new Label(property.getId()), 1,1);
        contentGrid.add(new Label(" Name:"), 0, 2); contentGrid.add(new Label(property.getName()), 1, 2);
        contentGrid.add(new Label("Host ID:"), 0, 3); contentGrid.add(new Label(property.getHost_id()), 1, 3);
        contentGrid.add(new Label("Borough:"), 0, 4); contentGrid.add(new Label(property.getNeighbourhood()), 1, 4);
        contentGrid.add(new Label("Price:"), 0, 5); contentGrid.add(new Label(property.getPrice() + ""), 1, 5);
        contentGrid.add(new Label("Minimum Night:"), 0, 6); contentGrid.add(new Label(property.getMinimumNights() + ""), 1, 6);
        contentGrid.add(new Label("Number of reviews:"), 0, 7); contentGrid.add(new Label(property.getNumberOfReviews() + ""), 1, 7);
        contentGrid.add(new Label("Last Review:"), 0, 8); contentGrid.add(new Label(property.getLastReview()), 1, 8);
        contentGrid.add(new Label("Host Listings:"), 0, 9); contentGrid.add(new Label(property.getCalculatedHostListingsCount() + ""), 1, 9);

        right.setTop(favBtn);
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
