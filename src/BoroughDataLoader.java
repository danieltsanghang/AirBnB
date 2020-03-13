import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import java.net.URISyntaxException;

public class BoroughDataLoader {
    /**
     * Reads borough-london.csv and loads every borough in the csv into an ArrayList
     * @return A Borough filled ArrayList
     */
    public ArrayList<Borough> load() {
        ArrayList<Borough> boroughs = new ArrayList<Borough>();
        try {
            URL url = getClass().getResource("boroughs-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String[] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String name = line[0];
                String abbrevName = line[1];
                int xPos = Integer.parseInt(line[2]);
                int yPos = Integer.parseInt(line[3]);
                int diameter = Integer.parseInt(line[4]);
                // Creation of new Borough based on loaded csv data
                boroughs.add(new Borough(name, abbrevName, xPos, yPos, diameter));
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        return boroughs;
    }
}
