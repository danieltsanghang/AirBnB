import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import javafx.beans.value.ChangeListener;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import java.util.ArrayList;
import java.util.Iterator;

public class listingBorough extends Pane{

private BorderPane container = new BorderPane();
private ArrayList<Pane> matchedListings = new ArrayList();
private String name;
private int min;
private int max;

    public listingBorough(String name) {
            this.name = name;
        AirbnbDataLoader data = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = data.load();
            for (AirbnbListing property : listings) {
                int price = property.getPrice();
                System.out.println(min + max); // doesnt work YET
                if (property.getNeighbourhood().equals(name)
// doesnt work YET  && min < price && price < max
                        ) {
                    matchedListings.add(this.toBoxes(property));
                }
            }


            Label sortLabel = new Label("Sort by:");
            ComboBox sortBox = new ComboBox();
            sortBox.getItems().addAll("Date","Price","dick size");

            BorderPane topbar = new BorderPane();
            GridPane sort = new GridPane();
            sort.add(sortLabel,0,0);
            sort.add(sortBox, 1,0);
            topbar.setRight(sort);
            container.setTop(topbar);

            ScrollPane main = new ScrollPane();
            VBox vbox = new VBox();
            for (Pane pane : matchedListings)   {
                vbox.getChildren().add(pane);
            }
            vbox.setPadding(new Insets(5,5,5,5));
            main.setContent(vbox);
            container.setCenter(main);
        }

        private Pane toBoxes(AirbnbListing listing) {
            GridPane box = new GridPane();
            Label hostName= new Label();
            Label price= new Label();
            Label reviews= new Label();
            Label minStay= new Label();
            hostName.setText("Host:" + listing.getHost_name());
            price.setText("$ " + listing.getPrice());
            reviews.setText("Number of reviews: " + listing.getNumberOfReviews());
            minStay.setText("Minimum nights: " + listing.getMinimumNights());
            hostName.setAlignment(Pos.CENTER_LEFT);
            price.setAlignment(Pos.CENTER_RIGHT);
            box.add(hostName,0,0);
            box.add(price,1,0);
            box.add(reviews,0,1);
            box.add(minStay,1,1);

            box.setVgap(7);
            box.setHgap(20);
            return box;
        }
        public Pane getPane()   {
            return container;
        }

        public String getName() {
            return name;
        }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
