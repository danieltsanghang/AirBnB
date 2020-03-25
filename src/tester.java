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


        Scene scene = new Scene();
        stage.setScene(scene);
        stage.show();
    }
}
