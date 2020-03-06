

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class tester extends Application
{
    private MapPanel panel;

    @Override
    public void start(Stage stage)
    {
        // JavaFX must have a Scene (window content) inside a Stage (window)
        panel = new MapPanel();
        Pane pane = panel.getPanel(0, 1000);
        Scene scene = new Scene(pane, 1920, 1080, Color.rgb(108, 108, 108));
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);
        stage.setMaximized(true);

        // Show the Stage (window)
        stage.show();

        System.out.println("shown");
    }
}
