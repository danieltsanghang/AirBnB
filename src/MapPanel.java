import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class MapPanel extends Panel
{
    private double scale;

    public MapPanel() throws IOException {
        super();
        scale = 0.55;
    }

    private int getMax(int minPrice, int maxPrice) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (Borough borough : boroughs) {
            temp.add(borough.getNumberOfListings(minPrice, maxPrice));
        }
        return Collections.max(temp);
    }

    private Color getFillColor(double number, double max) {
        double redValue = (max - number) / max * 255;
        double blueValue = 0.2164 * redValue + 200;
        return Color.rgb((int) redValue, 255, (int)blueValue);
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        Pane root = new Pane();

        for (Borough borough : boroughs) {
            double numberOfListings = borough.getNumberOfListings(minPrice, maxPrice);
            double maxNumberOfListings = getMax(minPrice, maxPrice);
            Color color = getFillColor(numberOfListings, maxNumberOfListings);

            Circle circle = new Circle(borough.getRadius(scale));
            circle.setFill(color);

            Text abbrevName = new Text(borough.getAbbrevName());
            abbrevName.setFont(new Font(20 * scale));
            abbrevName.setBoundsType(TextBoundsType.VISUAL);

            StackPane stack = new StackPane();
            stack.setId("circle");
            stack.relocate(borough.getX(scale)-100, borough.getY(scale));
            stack.getChildren().addAll(circle, abbrevName);

            stack.setOnMousePressed(this::popUp);

            root.getChildren().add(stack);
        }
        return root;
    }

    private void popUp(MouseEvent e) {
        StackPane stack = (StackPane) e.getSource();
        Text abbrevName = (Text) stack.getChildren().get(1);
        for (Borough borough : boroughs) {
            if (borough.getAbbrevName().equals(abbrevName.getText())) {
                System.out.println(borough.getName());
                ApplicationWindow.triggerBoroughWindow(borough.getName(), boroughs);
            }
        }
    }
}
