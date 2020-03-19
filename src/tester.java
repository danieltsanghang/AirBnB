import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class tester extends Application {

    @Override
    public void start(Stage stage) throws IOException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException {
        GoogleMapPanel panel = new GoogleMapPanel();

        Scene scene = new Scene(panel.start(51.4613, -0.3037,0.1,0.1));
        stage.setScene(scene);
        stage.show();
    }
}
