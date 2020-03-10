import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class LoginWindow extends Application {
    private String username;
    private String password;
    BorderPane root = new BorderPane();
    BorderPane bottomRoot = new BorderPane();
    GridPane container1 = new GridPane();
    GridPane container2 = new GridPane();
    Button enterButton = new Button("Enter");
    Button createAccountButton = new Button("Create Account");
    @Override
    public void start(Stage primaryStage) {
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
        container1.add(userValue,200,2 );
        container1.add(passwordValue,200 ,5 );
        container1.add(enterButton, 216,6);
        container2.add(noAccountLabel, 0, 40);
        container2.add(createAccountButton, 216,40);
        bottomRoot.setTop(container1);
        bottomRoot.setCenter(container2);
        root.setCenter(splashScreen);
        root.setBottom(bottomRoot);
        enterButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                username = userValue.getText();
                password = passwordValue.getText();
                /*if(checkUser.equals(user) && checkPw.equals(pw)){
                    lblMessage.setText("Congratulations!");
                    lblMessage.setTextFill(Color.GREEN);
                }
                else{
                    lblMessage.setText("Incorrect user or pw.");
                    lblMessage.setTextFill(Color.RED);
                }
                txtUserName.setText("");
                pf.setText("");*/
            }
        });


        createAccountButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });


        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");

        Stage mainStage = new Stage();
        mainStage.setTitle("User Login Window");
        mainStage.setScene(scene);
        mainStage.setHeight(400);
        mainStage.setWidth(400);
        mainStage.setResizable(false);


        mainStage.show();
    }

}
