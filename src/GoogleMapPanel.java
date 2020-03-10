import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class GoogleMapPanel {
    @Override public void start(){
        WebView googleMapView = new WebView();
        WebEngine googleMapEngine = googleMapView.getEngine();

        googleMapEngine.load("googlemap.html");

        Scene mapScene = new Scene(googleMapView, 1000, 700, Color.LIGHTSTEELBLUE); //filler coloUr
        // add pane shit and attach mapscene to a pane or something

    }


}
