import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class FavouriteDataLoader
{
    // The first row (labels) in favourite.csv
    private static final String[] favouritesCSVheader = {"Listing ID"};

    // A HashMap username and its matching list of favourite listings in pairs
    private ArrayList<String> favouriteID = new ArrayList<>();

    private CSVReader reader;

    public FavouriteDataLoader(){}

    /**
     * Reads favourites.csv and loads every favourited entry into an ArrayList
     * @return A filled ArrayList
     */
    public ArrayList<String> getFavourites()
    {
        favouriteID.clear();
        try{
            URL url = getClass().getResource("favourites.csv");
            reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                favouriteID.add(line[0]);
            }
            reader.close();
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        for (String id : favouriteID) {
            System.out.print(id + " ");
        }
        System.out.println("get favourite executed");
        return favouriteID;
    }

    /**
     * Writes the new favourited listing into favourite.csv
     * @param listingID The ID of the listing to be added
     * @throws IOException Unable to write into csv
     */
    public void newFavourite(String listingID)
    {
        favouriteID.add(listingID);
        updateCSV(favouriteID);
        for (String id : getFavourites()) {
            System.out.print(id + " ");
        }
        System.out.println("debug after insertion");
    }

    /**
     * Removes the favourite listing from favourite.csv
     * @param listingID The ID of the listing to be removed
     * @throws IOException Unable to write into csv
     */
    public void removeFavourite(String listingID)
    {
        favouriteID.remove(listingID);
        updateCSV(favouriteID);
        for (String id : getFavourites()) {
            System.out.print(id + " ");
        }
        System.out.println("debug after removal");
    }

    private void updateCSV(ArrayList<String> list) {

        String[] line = new String[1];

        try (CSVWriter favouriteWriter = new CSVWriter(
                new FileWriter("src/favourites.csv"),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)
        ) {
            // Write the labels into favourite.csv
            favouriteWriter.writeNext(favouritesCSVheader);
            // Write the existing favourite id back into favourite.csv
            for (String id : list) {
                line[0] = id;
                favouriteWriter.writeNext(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
