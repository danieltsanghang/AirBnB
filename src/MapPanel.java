
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
    public MapPanel()
    {
        super();
    }

    private Color getFillColor(double number, double max) {
        double redValue = (max - number) / max * 255;
        double blueValue = 0.2164 * redValue + 200;
        System.out.println((int) redValue + " " + 255 + " " + (int)blueValue);
        return Color.rgb((int) redValue, 255, (int)blueValue);
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        HashMap<String, Integer> boroughs = boroughToPropertyNo(minPrice, maxPrice);
        Set<String> boroughsList = boroughs.keySet();
        Iterator<String> boroughsIT = boroughsList.iterator();

        Pane root = new Pane();

        int max = Collections.max(boroughs.values());

        loadBoroughs();

        while (boroughsIT.hasNext()) {
            String boroughName = boroughsIT.next();
            Borough current = matchBoroughs(boroughName);

            Color color = getFillColor(boroughs.get(boroughName), max);

            Circle circle = new Circle(current.getRadius());
            circle.setFill(color);

            Text name = new Text(current.getAbbrevName());
            name.setFont(new Font(20));
            name.setBoundsType(TextBoundsType.VISUAL);

            StackPane stack = new StackPane();
            stack.relocate(current.getX(), current.getY());
            stack.getChildren().addAll(circle, name);

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
                Text abbrevName = (Text) stack.getChildren().get(1);
                //String name = matchBoroughToAbbrev(abbrevName.getText());
                System.out.println(abbrevName.getText());

            }
        };
    }
}
