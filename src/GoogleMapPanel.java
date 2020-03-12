import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.javafx.MapView;
import java.net.URL;

<<<<<<< Updated upstream
public class GoogleMapPanel {
    @Override public void start(){
        WebView googleMapView = new WebView();
        WebEngine googleMapEngine = googleMapView.getEngine();
=======
public class GoogleMapPanel extends MapView
{
    public String defaultURL = "https://www.google.com/maps?layer=c&cbll=51.5071707,-0.1274402&cbp=,300,,,0";
    public String locationURL = "";
>>>>>>> Stashed changes

//    public WebEngine streetViewEngine;
//    public WebView streetView;
    public MapViewOptions mapOption;
    public MapView mapView;

    public GoogleMapPanel(){
//        streetView = new WebView();
//        streetViewEngine = new WebEngine();
//        streetViewEngine = streetView.getEngine();
        mapOption = new MapViewOptions();
        mapOption.streetViewLayout().setSize(100);
        mapOption.streetViewLayout().setPosition(StreetViewLayout.Position.BOTTOM); // doesn't matter since it's taking up 100% of the screen space
        mapView = new MapView(mapOption);
    }

    public Pane start(Double latitude, Double longitude){

//        String URLStart = "https://www.google.com/maps?layer=c&cbll=";
//        URLStart += latitude + "," + longitude + "&cbp=,0,,,0";
//        locationURL = URLStart;
//
//        setLocation();

        mapView.setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus mapStatus) {
                // check map loaded properly
                if(mapStatus == MapStatus.MAP_STATUS_OK){
                    Map map = mapView.getMap();
                    MapOptions mapOptions = new MapOptions();
                    map.setOptions(mapOptions);
                    map.setCenter(new LatLng(latitude, longitude));

                    StreetViewPanoramaOptions streetViewPanOptions = new StreetViewPanoramaOptions();
                    StreetViewAddressControlOptions streetViewAddressOptions = new StreetViewAddressControlOptions();
                    streetViewAddressOptions.setPosition(ControlPosition.TOP_LEFT);
                    streetViewPanOptions.setAddressControlOptions(streetViewAddressOptions);
                    mapView.getPanorama().setOptions(streetViewPanOptions);
                    mapView.getPanorama().setPosition(map.getCenter());
                    StreetViewPov streetViewPov = new StreetViewPov();
                    streetViewPov.setHeading(0);
                    streetViewPov.setPitch(0);

                    mapView.getPanorama().setPov(streetViewPov);
                }
            }
        });

        Pane thisIsAPane = new Pane();
        thisIsAPane.getChildren().add(mapView);
        return thisIsAPane;

    }

<<<<<<< Updated upstream

=======
//    public Pane getGog(){
//        Pane thisIsAPane = new Pane();
//        Object o = streetView;
//        thisIsAPane.getChildren().add((Node) o);
//        return thisIsAPane;
//    }

//    public void loadLocation(String latitude, String longitude){
//
//    }

//    private void setLocation(){
//        if(locationURL.equals("") || locationURL == null) {
//            streetViewEngine.load(defaultURL); // sets a default location
//            System.out.println("No location provided");
//        } else{
//            streetViewEngine.load(locationURL);
//        }
//    }
>>>>>>> Stashed changes
}
