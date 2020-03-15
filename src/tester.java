import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class tester extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        StatsPanel panel = new StatsPanel();

        Scene scene = new Scene(panel.getPanel(0, 0));
        stage.setScene(scene);
        stage.show();
    }
}
