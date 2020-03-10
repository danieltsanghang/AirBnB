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

import java.util.iterator;

public class LoginPanel extends Panel {
    private String username;
    private String password;

    private Iterator<Account> accountIT;
    private boolean loginSuccess = false;

    public LoginPanel() throws IOException {
        super();
    }

    public Pane getPanel(int minPrice, int maxPrice){

        BorderPane root = new BorderPane();
        BorderPane bottomPane = new BorderPane();
        GridPane mainContainerPane = new GridPane();
        GridPane secondaryContainerPane = new GridPane();
        Button enterButton = new Button("Enter");
        File splashScreenImageFile = new File("logo.png");
        Image splashScreenImage = new Image(splashScreenImageFile.toURI().toString());
        ImageView splashScreen = new ImageView(splashScreenImage);

        splashScreen.setFitHeight(200);
        splashScreen.setFitWidth(400);
        splashScreen.setPreserveRatio(true);

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        // usernameLabel.setMinWidth(50);
        // passwordLabel.setMinWidth(50);

        TextField userValue = new TextField();
        TextField passwordValue = new TextField();
        Label errorLabel = new Label("");
        mainContainerPane.add(usernameLabel, 150, 2);
        mainContainerPane.add(passwordLabel, 150, 5);
        mainContainerPane.add(userValue, 200, 2);
        mainContainerPane.add(passwordValue, 200, 5);
        mainContainerPane.add(enterButton, 216, 6);
        secondaryContainerPane.add(errorLabel);
        bottomPane.setTop(mainContainerPane);
        bottomPane.setCenter(secondaryContainerPane);
        root.setCenter(splashScreen);
        root.setBottom(bottomPane);

        enterButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                username = userValue.getText();
                password = passwordValue.getText();
                boolean loginSuccess = false;
                accountIT = accounts.iterator();

                while (!loginSuccess) {
                    while(accountIT.hasNext()) {
                        Account accountToCheck = accountIT.next();
                        if (username.equals(accountToCheck.getUserName())) {
                            if (password.equals(accountToCheck.getPassword())) {
                                loginSuccess = true;
                            } else {
                                //wrong password
                                errorLabel = "Please check your username or password again."
                            }
                        } else {
                            //no such user
                            errorLabel = "Please check your username or password again."


                System.out.println(loginSuccess);
            }
        });
        if (loginSuccess) {
            return null;
        }
        return root;
    }
}
