import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Scanner;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import javafx.scene.layout.GridPane;

public class LoginWindow extends Application {
    private String username;
    private String password;
    Label usernameLabel = new Label();
    Label passwordLabel = new Label();
    GridPane root = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        Scanner userScanner = new Scanner(System.in);
        System.out.println("Enter user ");
        Scanner userScanner = new Scanner(System.in);
        System.out.println("Enter a number: ");


        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");

        Stage mainStage = new Stage();
        mainStage.setTitle("User Login Window");
        mainStage.setScene(scene);

        mainStage.setResizable(false);
        mainStage.show();
    }
}
