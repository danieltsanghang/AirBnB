import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

public class AccountPanel extends Panel{
    private Button escapeButton;
    private Button newAccountButton;
    private Button loginButton;

    private BorderPane returnPane;

    private int paneSelection = 0;

    private Iterator<Account> accountIT;
    private ArrayList<Pane> panes;
    private boolean loginSuccess = false;

    public AccountPanel () throws IOException{
        super();
        Pane loginPane = makeLoginPane();
        Pane gridpane = makeCreateAccountPane();
        returnPane = new BorderPane();

        panes = new ArrayList<>();
        panes.add(loginPane);
        panes.add(gridpane);
    }

    public Pane getPanel(int minPrice, int maxPrice) {
        returnPane.setCenter(panes.get(paneSelection));
        return returnPane;
    }

    private Pane makeLoginPane() {
        StackPane loginPane = new StackPane();

        File loginImageFile = new File("loginscreen.png");
        Image loginImage = new Image(loginImageFile.toURI().toString());
        ImageView loginScreen = new ImageView(loginImage);

        VBox vbox = new VBox();
        vbox.setStyle("-fx-alignment: bottom-center");

        TextField userValue = new TextField();
        userValue.setPromptText("Username");

        PasswordField passwordValue = new PasswordField();
        passwordValue.setPromptText("Password");

        Label errorLabel = new Label("asdf");

        loginButton = new Button("Login");
        newAccountButton = new Button("Create New Account");

        loginButton.setOnAction(e -> {
            String username = userValue.getText();
            String password = passwordValue.getText();

            accountIT = accounts.iterator();
            while (accountIT.hasNext() && !loginSuccess) {
                Account accountToCheck = accountIT.next();
                boolean userMatch = username.equals(accountToCheck.getUserName());
                boolean pswdMatch = password.equals(accountToCheck.getPassword());
                if (userMatch && pswdMatch) {
                    loginSuccess = true;
                    returnPane.setCenter(makeMyAccountPane(username));
                }
            }
            if (!loginSuccess) {
                System.out.println("login failed");
            }
        });

        newAccountButton.setOnAction(e -> {
           paneSelection = 1;
           returnPane.setCenter(panes.get(paneSelection));
        });

        vbox.getChildren().addAll(userValue, passwordValue, loginButton, errorLabel, newAccountButton);
        loginPane.getChildren().addAll(loginScreen, vbox);
        return loginPane;
    }

    private Pane makeCreateAccountPane() {
        BorderPane newAccountPane = new BorderPane();
        GridPane gridpane = new GridPane();

        Label displayName = new Label ("Your Displayed Name:");
        Label username = new Label("Your Username:");
        Label password = new Label("Your Password:");
        Label cPassword = new Label("Confirm Password:");
        Label errorMessage = new Label();

        TextField inputDisplayName = new TextField();
        TextField inputUsername = new TextField();
        PasswordField inputPassword = new PasswordField();
        PasswordField inputCPassword = new PasswordField();

        Button createAccount = new Button("Create Account");
        escapeButton = new Button("Back");

        createAccount.setOnAction(e -> {
            if (inputUsername.getText().equals("")) {
                errorMessage.setText("Please input username");
            }
            else if (inputPassword.getText().equals("")) {
                errorMessage.setText("Please input password");
            }
            else if (matchAccounts(inputUsername.getText())) {
                errorMessage.setText("There already is an account with the username: " + inputUsername.getText());
            }
            else if (!inputPassword.getText().equals(inputCPassword.getText())) {
                errorMessage.setText("Passwords don't match");
            }
            else {
                Account newaccount = new Account(inputDisplayName.getText(), inputPassword.getText(), inputCPassword.getText());
                accounts.add(newaccount);
                try {
                    accountLoader.newAccount(newaccount);
                    errorMessage.setText("Account successfully created");
                } catch (IOException ex) {
                    errorMessage.setText("Account creation failure. Please try again.");
                }
            }
        });

        escapeButton.setOnAction(e -> {
            paneSelection = 0;
            returnPane.setCenter(panes.get(paneSelection));
        });

        gridpane.add(displayName, 0, 0);
        gridpane.add(username, 0, 1);
        gridpane.add(password, 0, 2);
        gridpane.add(cPassword, 0, 3);
        gridpane.add(inputDisplayName, 1, 0);
        gridpane.add(inputUsername, 1, 1);
        gridpane.add(inputPassword, 1, 2);
        gridpane.add(inputCPassword, 1, 3);
        gridpane.add(createAccount, 0, 4, 2, 1);
        gridpane.add(errorMessage, 0, 5, 2, 1);

        newAccountPane.setTop(escapeButton);
        newAccountPane.setCenter(gridpane);
        return newAccountPane;
    }

    private Pane makeMyAccountPane(String username) {
        Pane myAccountPane = new Pane();
        return myAccountPane;
    }

    private boolean matchAccounts(String attempt) {
        boolean match = false;
        for (Account existing : accounts) {
            if (existing.getUserName().equals(attempt)) {
                match = true;
            }
        }
        return match;
    }
}