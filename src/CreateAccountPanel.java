import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class CreateAccountPanel extends Panel {
    public CreateAccountPanel() {
        super();
    }

    public Pane getPanel(int minPrice, int maxPrice) {
        GridPane newAccountPane = new GridPane();

        Label username = new Label("Your Username:");
        Label password = new Label("Your Password:");
        Label cPassword = new Label("Confirm Password:");
        Label errorMessage = new Label();

        TextField inputUsername = new TextField();
        TextField inputPassword = new TextField();
        TextField inputCPassword = new TextField();

        newAccountPane.add(username, 0, 0);
        newAccountPane.add(password, 0, 1);
        newAccountPane.add(cPassword, 0, 2);
        newAccountPane.add(inputUsername, 1, 0);
        newAccountPane.add(inputPassword, 1, 1);
        newAccountPane.add(inputCPassword, 1, 2);
        newAccountPane.add(errorMessage, 1, 3);

        return newAccountPane;
    }

    private boolean MatchAccounts(String attempt) {
        boolean match = false;
        for (Account existing : accounts) {
            if (existing.getUName().equals(attempt)) {
                match = true;
            }
        }
        return match;
    }
}
