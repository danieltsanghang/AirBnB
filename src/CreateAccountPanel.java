import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.IOException;

public class CreateAccountPanel extends Panel {
    public CreateAccountPanel() throws IOException {
        super();
    }

    public Pane getPanel(int minPrice, int maxPrice) {
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

        return gridpane;
    }

    private boolean matchAccounts(String attempt) {
        boolean match = false;
        for (Account existing : accounts) {
            if (existing.getUName().equals(attempt)) {
                match = true;
            }
        }
        return match;
    }
}
