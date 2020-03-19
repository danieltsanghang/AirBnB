import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    public static String urlString = "";
    private static byte[] key;


    public WebEngine streetViewEngine;
    public WebView streetView;


    public GoogleMapPanel(){
        streetView = new WebView();
        streetViewEngine = new WebEngine();
        streetViewEngine = streetView.getEngine();

    }

    public Pane start(Double latitude, Double longitude, Double height, Double width) throws URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, IOException {

//        String URLStart = "https://www.google.com/maps?layer=c&cbll=";
//        URLStart += latitude + "," + longitude + "&cbp=,0,,,0";
//        locationURL = URLStart;

        urlString = "https://maps.googleapis.com/maps/api/streetview?size=400x400&location=" + latitude + "," + longitude +
                "&fov=360&heading=100&pitch=0&key=" + API_KEY;

        //String urlToUse = signURL();
        //URLEncoder.encode(urlToUse, "UTF-8");

        streetViewEngine.load(urlString);


        Pane thisIsAPane = new Pane();
        Object svObject = streetView;
        ((Node) svObject).maxHeight(height); ((Node) svObject).minHeight(height);
        ((Node) svObject).maxWidth(width);   ((Node) svObject).minWidth(width);
        thisIsAPane.getChildren().add((Node) svObject);
        return thisIsAPane;

    }

    public String signURL() throws IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        URL url = new URL(urlString);

        urlSigner signer = new urlSigner(API_KEY);
        String digitalSignature = urlSigner.signRequest(url.getPath(), url.getQuery());
        System.out.println((url.getProtocol() + "://" + url.getHost() + digitalSignature));
        return (url.getProtocol() + "://" + url.getHost() + digitalSignature);
    }

    public static class urlSigner{

        private static byte[] key;
        public urlSigner(String keyString) throws IOException
        {
            keyString = keyString.replace('-', '+');
            keyString = keyString.replace('_', '/');

            this.key = Base64.getDecoder().decode(keyString);
        }

        public static String signRequest(String path, String query) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, URISyntaxException{
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

}