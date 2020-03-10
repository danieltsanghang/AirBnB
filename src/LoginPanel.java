import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;


public class LoginPanel extends Panel {
    private String username;
    private String password;
    CreateAccountPanel accountpanel = new CreateAccountPanel();



    public LoginPanel() throws IOException {
        super();
    }

    public Pane getPanel(int minPrice, int maxPrice){

        BorderPane root = new BorderPane();
        BorderPane bottomRoot = new BorderPane();
        GridPane container1 = new GridPane();
        GridPane container2 = new GridPane();
        Button enterButton = new Button("Enter");
        Button createAccountButton = new Button("Create Account");
        File splashScreenImageFile = new File("logo.png");
        Image splashScreenImage = new Image(splashScreenImageFile.toURI().toString());
        ImageView splashScreen = new ImageView(splashScreenImage);

        splashScreen.setFitHeight(200);
        splashScreen.setFitWidth(400);
        splashScreen.setPreserveRatio(true);

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label noAccountLabel = new Label("No account? Create one!");
        // usernameLabel.setMinWidth(50);
        // passwordLabel.setMinWidth(50);

        TextField userValue = new TextField();
        TextField passwordValue = new TextField();
        container1.add(usernameLabel, 150, 2);
        container1.add(passwordLabel, 150, 5);
        container1.add(userValue, 200, 2);
        container1.add(passwordValue, 200, 5);
        container1.add(enterButton, 216, 6);
        container2.add(noAccountLabel, 0, 40);
        container2.add(createAccountButton, 216, 40);
        bottomRoot.setTop(container1);
        bottomRoot.setCenter(container2);
        root.setCenter(splashScreen);
        root.setBottom(bottomRoot);
        enterButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                username = userValue.getText();
                password = passwordValue.getText();
                boolean loginSuccess = false;
                boolean existence = false;

                for(int i=0; i<accounts.size(); i++){
                    if(accounts.get(i).getUName().equals(username)){
                        existence = true;
                    }
                    if ( accounts.get(i).getPassword().equals(password) ){
                        System.out.println("Logined");
                        loginSuccess=true;
                        break;
                    }
                    else if (!accounts.get(i).getPassword().equals(password) ) {
                        System.out.println("Wrong Username");
                        break;
                    }

                }

            }
        });


        createAccountButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.setCenter(accountpanel.getPanel(0, 0));
                root.setBottom(null);
            }
        });


        return root;
    }





}
