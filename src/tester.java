import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class tester extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        ArrayList<Panel> accountPanels = new ArrayList<>();

        accountPanels.add(new LoginPanel());
        accountPanels.add(new CreateAccountPanel());
        accountPanels.add(new MyAccountPanel());

        AccountPanel panel = new AccountPanel(accountPanels);

        Scene scene = new Scene(panel.getPanel(0, 0));
        stage.setScene(scene);
        stage.show();
    }
}
