import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.net.URL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;  // JDK 1.8 only - older versions may need to use Apache Commons or similar.
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GoogleMapPanel
{

    public String API_KEY = "AIzaSyAYSnY8BF9kfmVKe-DMTlGMBhvK4KtBjgI";
    public static String urlString = "https://www.google.com/maps?layer=c&cbll=51.5071707,-0.1274402&cbp=,300,,,0";
    private static byte[] key;


    public WebEngine streetViewEngine;
    public WebView streetView;


    public GoogleMapPanel(){
        streetView = new WebView();
        streetViewEngine = new WebEngine();
        streetViewEngine = streetView.getEngine();

    }

    public Pane start(Double latitude, Double longitude) throws URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, IOException {

//        String URLStart = "https://www.google.com/maps?layer=c&cbll=";
//        URLStart += latitude + "," + longitude + "&cbp=,0,,,0";
//        locationURL = URLStart;

        urlString = "https://maps.googleapis.com/maps/api/streetview?size=400x400&location=" + latitude + "," + longitude +
                "&fov=80&heading=70&pitch=0&key=" + API_KEY;

        String urlToUse = signURL();

        streetViewEngine.load(urlToUse);


        Pane thisIsAPane = new Pane();
        Object urlSV = streetView;
        thisIsAPane.getChildren().add((Node) urlSV);
        return thisIsAPane;

    }

    public String signURL() throws IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        URL url = new URL(urlString);

        signer(API_KEY);
        String digitalSignature = signRequest(url.getPath(), url.getQuery());

        return (url.getProtocol() + "://" + url.getHost() + digitalSignature);
    }

    public void signer(String keyString) throws IOException{
        keyString = keyString.replace('-', '+');
        keyString = keyString.replace('_', '/');

        this.key = Base64.getDecoder().decode(keyString);
    }

    public String signRequest(String path, String query) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, URISyntaxException{
        String URL = path + "?" + query;

        SecretKeySpec sha1KEY = new SecretKeySpec(key, "HmacSHA1");

        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(sha1KEY);

        byte[] significantBytes = mac.doFinal(URL.getBytes());

        String signatureURL = Base64.getEncoder().encodeToString(significantBytes);

        signatureURL = signatureURL.replace('+', '-');
        signatureURL = signatureURL.replace('/', '_');

        return URL + "&signature=" + signatureURL;
    }

}

//    public Pane getGog(){
//        Pane thisIsAPane = new Pane();
//        Object o = streetView;
//        thisIsAPane.getChildren().add((Node) o);
//        return thisIsAPane;
//    }

//    public void loadLocation(String latitude, String longitude){
//
//    }


//    public MapViewOptions mapOption;
//    public MapView mapView;

//        mapOption = new MapViewOptions();
//        mapOption.streetViewLayout().setSize(100);
//        mapOption.streetViewLayout().setPosition(StreetViewLayout.Position.BOTTOM); // doesn't matter since it's taking up 100% of the screen space
//        mapView = new MapView(mapOption);
//        mapView.setOnMapReadyHandler(new MapReadyHandler() {
//            @Override
//            public void onMapReady(MapStatus mapStatus) {
//                // check map loaded properly
//                if(mapStatus == MapStatus.MAP_STATUS_OK){
//                    Map map = mapView.getMap();
//                    MapOptions mapOptions = new MapOptions();
//                    map.setOptions(mapOptions);
//                    map.setCenter(new LatLng(latitude, longitude));
//
//                    StreetViewPanoramaOptions streetViewPanOptions = new StreetViewPanoramaOptions();
//                    StreetViewAddressControlOptions streetViewAddressOptions = new StreetViewAddressControlOptions();
//                    streetViewAddressOptions.setPosition(ControlPosition.TOP_LEFT);
//                    streetViewPanOptions.setAddressControlOptions(streetViewAddressOptions);
//                    mapView.getPanorama().setOptions(streetViewPanOptions);
//                    mapView.getPanorama().setPosition(map.getCenter());
//                    StreetViewPov streetViewPov = new StreetViewPov();
//                    streetViewPov.setHeading(0);
//                    streetViewPov.setPitch(0);
//
//                    mapView.getPanorama().setPov(streetViewPov);
//                }
//            }
//        });
//         Pane thisIsAPane = new Pane();
//        thisIsAPane.getChildren().add(mapView);
//        return thisIsAPane;