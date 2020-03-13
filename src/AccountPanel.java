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

public class AccountPanel extends Panel
{
    // URL holding the profile picture image
    private static final String userURL = "file:user.png";

    // Pane selection 0, 1, or 2
    private int paneSelection = 0;

    // ArrayList that holds all the panes that belong to this panel
    private ArrayList<Pane> panes;

    // Logged in state
    private boolean loginSuccess = false;

    // JavaFX Control Nodes
    private Button escapeButton;
    private Button newAccountButton;
    private Button loginButton;

    // JavaFX Layouts
    private BorderPane returnPane;
    private VBox favouriteBoroughBox;

    // JavaFx Images
    private ImageView loginScreen;

    public AccountPanel() throws IOException
    {
        super();
        // Create the login pane
        Pane loginPane = makeLoginPane();
        // Create the new account pane
        Pane newAccountPane = makeCreateAccountPane();

        // The pane to return in the getPanel method
        returnPane = new BorderPane();
        // The VBox that shows the favourite listings of this user
        favouriteBoroughBox = new VBox();

        // Initialize ArrayList panes and adds loginPane and newAccountPane
        panes = new ArrayList<>();
        panes.add(loginPane);
        panes.add(newAccountPane);

        // Image Loading
        File loginImageFile = new File("img/loginscreen.png");
        Image loginImage = new Image(loginImageFile.toURI().toString());
        loginScreen = new ImageView(loginImage);
    }

    /**
     * Parameters not in use
     * @param minPrice Selected minimum price for filtering
     * @param maxPrice Selected maximum price for filtering
     * @return The selected pane
     */
    public Pane getPanel(int minPrice, int maxPrice)
    {
        returnPane.setCenter(panes.get(paneSelection));
        return returnPane;
    }

    /**
     * Creation of the login Pane
     * @return constructed login pane
     */
    private Pane makeLoginPane()
    {
        StackPane loginPane = new StackPane(); // The base pane

        VBox loginVBox = new VBox(); // The VBox holding all the controls
        loginVBox.getStyleClass().add("loginVBox"); // Set style class

        TextField userValue = new TextField(); // Username input textbox
        userValue.getStyleClass().add("inputBox"); // Set style class
        userValue.setPromptText("Username");

        PasswordField passwordValue = new PasswordField(); // Password input textbox
        passwordValue.getStyleClass().add("inputBox"); // Set style class
        passwordValue.setPromptText("Password");

        Label errorLabel = new Label(""); // Label of error message
        errorLabel.getStyleClass().add("whiteText"); // Set style class

        loginButton = new Button("Login"); // Login button
        loginButton.setOnAction(e -> { // Set login button action
            String username = userValue.getText();
            String password = passwordValue.getText();

            Iterator<Account> accountIT = accounts.iterator();
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

        newAccountButton = new Button("Create New Account"); // Create account button
        newAccountButton.setOnAction(e -> { // Set create account button action
           paneSelection = 1;
           returnPane.setCenter(panes.get(paneSelection));
        });

        Pane spacing = new Pane(); // Create spacing pane
        spacing.getStyleClass().add("loginSpacing"); // Set style class

        // Add all nodes into VBox
        loginVBox.getChildren().addAll(userValue, passwordValue, loginButton, errorLabel, spacing, newAccountButton);
        // Add all loginScreen.png and VBox into StackPane
        loginPane.getChildren().addAll(loginScreen, loginVBox);
        return loginPane;
    }

    /**
     * Creation of Create Account Pane
     * @return constructed create account pane
     */
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
        favouriteBoroughBox.setPrefSize(400,400);
        scrollPane.setContent(favouriteBoroughBox);
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
            favouriteBoroughBox.getChildren().add(account);

        }
    }
}