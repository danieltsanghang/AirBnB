import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.io.IOException;

import java.util.ArrayList;

public class AccountPanel extends Panel{

    private ArrayList<Panel> panels;

    public AccountPanel (ArrayList<Panel> panels) throws IOException{
        super();
        this.panels = panels;
    }

    public Pane getPanel(int minPrice, int maxPrice) {
        BorderPane pane = new BorderPane();

        VBox topBar = new VBox();
        VBox bottomBar = new VBox();

        Button escapeButton = new Button();
        Button newAccountButton = new Button("Don't have an account? Create one!");

        topBar.getChildren().add(escapeButton);
        pane.setTop(topBar);
        pane.setBottom(bottomBar);
        pane.setCenter(panels.get(0).getPanel(0, 0));

        return pane;
    }
}
