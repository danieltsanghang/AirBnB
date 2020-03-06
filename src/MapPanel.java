
/**
 * Write a description of class MapPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class MapPanel extends Panel
{
    /**
     * Constructor for objects of class MapPanel
     */

    private int max;
    private HashMap<String, Integer> boroughs;
    private Set<String> boroughsList;
    private Iterator<String> boroughsIT;


    public MapPanel()
    {
        super();
    }

    private Color getFillColor(double number, double max) {
        double redValue = (max - number) / max * 255;
        double blueValue = 0.2164 * redValue + 200;
        //System.out.println((int) redValue + " " + 255 + " " + (int)blueValue);
        return Color.rgb((int) redValue, 255, (int)blueValue);
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        Pane root = new Pane();

        boroughs = boroughToPropertyNo(minPrice, maxPrice);
        boroughsList = boroughs.keySet();
        boroughsIT = boroughsList.iterator();
        max = Collections.max(boroughs.values());

        loadBoroughs();

        double scale = 0.55;

        while (boroughsIT.hasNext()) {
            String boroughName = boroughsIT.next();
            Borough current = matchBoroughs(boroughName);

            Color color = getFillColor(boroughs.get(boroughName), max);

            Circle circle = new Circle(current.getRadius(scale));
            circle.setFill(color);

            Text abbrevName = new Text(current.getAbbrevName());
            Text name = new Text(current.getName());
            name.setFont(new Font(0.00001));
            name.setVisible(false);
            abbrevName.setFont(new Font(20 * scale));
            abbrevName.setBoundsType(TextBoundsType.VISUAL);

            StackPane stack = new StackPane();
            stack.relocate(current.getX(scale), current.getY(scale));
            stack.getChildren().addAll(circle, name, abbrevName);

            MouseGestures mg = new MouseGestures();
            mg.makePressable(stack);

            root.getChildren().add(stack);
        }
        return root;
    }

    private static class MouseGestures {

        public void makePressable(Node node) {
            node.setOnMousePressed(mousePressEventHandler);
        }

        EventHandler<MouseEvent> mousePressEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                StackPane stack = (StackPane) t.getSource();
                Text name = (Text) stack.getChildren().get(1);
                Text abbrevName = (Text) stack.getChildren().get(2);
                //String name = matchBoroughToAbbrev(abbrevName.getText());
                System.out.println(name.getText());
                listingBorough pane = new listingBorough(name.getText());
                test(pane);
            }
        };
    }

    static public void test (listingBorough pane) {
        ApplicationWindow.popUpList(pane, pane.getName());
}
}
