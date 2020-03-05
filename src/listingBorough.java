import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
private Scene scene = new Scene(container);
private ArrayList<Pane> listings = new ArrayList();
private String name;
private String abbrevName;

        public listingBorough(String name, String abbrevName) {
            this.name = name;
            this.abbrevName = abbrevName;

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
            main.setContent(vbox);
            container.setCenter(main);
        }

        public Pane getPane()   {
            return this;
        }

        public String getName() {
            return name;
        }
}
