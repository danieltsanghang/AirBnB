import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class WelcomePanel extends Panel
{
    // JavaFx Image
    private Image image;
    private ImageView imageView;

    public WelcomePanel() throws IOException, URISyntaxException {
        super(null);
        //Loads the welcome screen based on the provided URL
        //Converts File to ImageView using Image
        File file = new File("img/welcome-screen.png");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
    }

    /**
     * @param minPrice Selected minimum price for filtering
     * @param maxPrice Selected maximum price for filtering
     * @return Pane with welcome image loaded
     */
    public Pane getPanel(int minPrice, int maxPrice){
        // Create Welcome Pane
        Pane welcomePane = new Pane();
        // Adds image to Welcome Pane
        welcomePane.getChildren().add(imageView);
        // Resize Welcome Pane based on the image's size
        this.setMaxSize(image.getWidth(),image.getHeight());
        // Return Welcome Pane
        return welcomePane;
    }
}
