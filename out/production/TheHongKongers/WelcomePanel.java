import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import java.io.FileInputStream; 
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

/**
 * Write a description of class WelcomePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WelcomePanel extends Pane
{
    private Image image;
    private BorderPane welcomePane = new BorderPane(); 
    //Creating an image 
    public WelcomePanel()
    {   
        File file = new File("welcomepanel_try.png");
        image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        welcomePane.setCenter(imageView);
        this.setMinSize(image.getWidth(),image.getHeight());
    }
    /*URL url = getClass().getResource("welcomepanel_try.png");
    Image image = new Image(new FileReader(new File(url.toURI()).getAbsolutePath()));
    ImageView imageView = new ImageView(image);*/
    
    //Setting the position of the image
    /*imageView.setX(50); 
    imageView.setY(25);  */
    
    /**
     * Constructor for objects of class WelcomePanel
     */
    
     public Pane getPanel(int minPrice, int maxPrice){ //why does price exist here -d
        return welcomePane;
    }

    public double height()
    {
        return image.getHeight();
    }
    
    public double width()
    {
        return image.getWidth();
    }
}
