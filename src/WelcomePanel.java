import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class WelcomePanel extends Panel
{
    private Image image;
    private ImageView imageView;

    //Creating an image
    public WelcomePanel()
    {
        File file = new File("airbnb_welcome.png");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
    }

    public Pane getPanel(int minPrice, int maxPrice){
        BorderPane welcomePane = new BorderPane();
        welcomePane.setCenter(imageView);
        this.setMinSize(image.getWidth(),image.getHeight());
        return welcomePane;
    }
}
