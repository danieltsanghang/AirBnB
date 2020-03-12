import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

public class AccountPanel extends Panel{
    private static final String userURL = "file:user.png";
    private Button escapeButton;
    private Button newAccountButton;
    private Button loginButton;

    private BorderPane returnPane;

    private int paneSelection = 0;

    private Iterator<Account> accountIT;
    private ArrayList<Pane> panes;
    private boolean loginSuccess = false;
    VBox savedBoroughBox = new VBox();

    public AccountPanel () throws IOException{
        super();
        Pane loginPane = makeLoginPane();
        Pane newAccountPane = makeCreateAccountPane();
        returnPane = new BorderPane();

        panes = new ArrayList<>();
        panes.add(loginPane);
        panes.add(newAccountPane);


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
        vbox.getStyleClass().add("loginVBox");

        TextField userValue = new TextField();
        userValue.getStyleClass().add("inputBox");
        userValue.setPromptText("Username");

        PasswordField passwordValue = new PasswordField();
        passwordValue.getStyleClass().add("inputBox");
        passwordValue.setPromptText("Password");

        Label errorLabel = new Label("");
        errorLabel.getStyleClass().add("whiteText");

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
                    ApplicationWindow.login(accountToCheck);
                    paneSelection = 2;
                    panes.add(makeMyAccountPane(username));
                    returnPane.setCenter(panes.get(paneSelection));
                }
            }
            if (!loginSuccess) {
                errorLabel.setText("Invalid username or password");
            }
        });

        newAccountButton.setOnAction(e -> {
           paneSelection = 1;
           returnPane.setCenter(panes.get(paneSelection));
        });

        Pane spacing = new Pane();
        spacing.getStyleClass().add("loginSpacing");

        vbox.getChildren().addAll(userValue, passwordValue, loginButton, errorLabel, spacing, newAccountButton);
        loginPane.getChildren().addAll(loginScreen, vbox);
        return loginPane;
    }

    private Pane makeCreateAccountPane() {
        GridPane newAccountPane = new GridPane();

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
        escapeButton = new Button("<");
        escapeButton.getStyleClass().add("escapeButton");

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
                    accounts.add(newaccount);
                    errorMessage.setText("Account successfully created");
                    inputDisplayName.clear();
                    inputUsername.clear();
                    inputPassword.clear();
                    inputCPassword.clear();
                } catch (IOException ex) {
                    errorMessage.setText("Account creation failure. Please try again.");
                }
            }
        });

        escapeButton.setOnAction(e -> {
            paneSelection = 0;
            returnPane.setCenter(panes.get(paneSelection));
        });

        Pane spacing = new Pane();
        spacing.getStyleClass().add("loginSpacing");

        newAccountPane.add(escapeButton, 0, 0);
        newAccountPane.add(displayName, 1, 1);
        newAccountPane.add(username, 1, 2);
        newAccountPane.add(password, 1, 3);
        newAccountPane.add(cPassword, 1, 4);
        newAccountPane.add(inputDisplayName, 2, 1);
        newAccountPane.add(inputUsername, 2, 2);
        newAccountPane.add(inputPassword, 2, 3);
        newAccountPane.add(inputCPassword, 2, 4);
        newAccountPane.add(createAccount, 1, 5, 2, 1);
        newAccountPane.add(spacing, 1, 6, 2, 1);
        newAccountPane.add(errorMessage, 1, 7, 2, 1);
        newAccountPane.getStyleClass().add("createAccountGrid");

        return newAccountPane;
    }

    private Pane makeMyAccountPane(String username) {
        HBox myAccountPane = new HBox();
        myAccountPane.getStyleClass().add("profileHBox");
        VBox personalInformation = new VBox();
        VBox boroughSavedPane = new VBox();

        Label boroughSavedLabel = new Label("Borough Saved:");
        boroughSavedLabel.getStyleClass().add("accountLabel");
        ScrollPane scrollPane = new ScrollPane();
        loadBoxes(Account.getFavourites());
        savedBoroughBox.setPrefSize(400,400);
        scrollPane.setContent(savedBoroughBox);
        boroughSavedPane.getChildren().addAll(boroughSavedLabel, scrollPane);
        boroughSavedPane.getStyleClass().add("scrollPaneVox");

        Label name = new Label("Welcome " + username + "!");
        name.getStyleClass().add("accountLabel");
        Circle circle  = new Circle(100);
        Image userImage = new Image(userURL);
        circle.setFill(new ImagePattern(userImage, 0, 0, 1, 1, true));
        personalInformation.getChildren().addAll(circle, name);
        personalInformation.getStyleClass().add("profileVBox");

        myAccountPane.getChildren().addAll(boroughSavedPane, personalInformation);

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

    private void loadBoxes(ArrayList<AirbnbListing> favouriteAccounts) {
        for (int i = 0; i < favouriteAccounts.size(); i++) {
            Label account = new Label (favouriteAccounts.get(i).getName());
            savedBoroughBox.getChildren().add(account);

        }
    }
}