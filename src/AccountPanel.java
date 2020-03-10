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

        HBox topBar = new HBox();
        HBox bottomBar = new HBox();

        Button escapeButton = new Button();
        escapeButton.setVisible(false);
        Button newAccountButton = new Button("Don't have an account? Create one!");

        newAccountButton.setOnAction(e -> {
            escapeButton.setVisible(true);
            newAccountButton.setVisible(false);
            pane.setCenter(panels.get(1).getPanel(0, 0));
        });
        escapeButton.setOnAction(e -> {
           escapeButton.setVisible(false);
           newAccountButton.setVisible(true);
           pane.setCenter(panels.get(0).getPanel(0, 0));
        });

        topBar.getChildren().add(escapeButton);
        bottomBar.getChildren().add(newAccountButton);

        pane.setTop(topBar);
        pane.setBottom(bottomBar);
        pane.setCenter(panels.get(0).getPanel(0, 0));

        return pane;
    }
}
