import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private ArrayList<String> favouriteID;
    private ArrayList<AirbnbListing> favouriteListings;

    public FavouriteDataLoader()
    {
        favouriteID = new ArrayList<>();
        loadFavourites();
        favouriteListings = new ArrayList<>();
    }

    /**
     * Reads favourites.csv and loads every favourited entry into an ArrayList
     * @return A filled ArrayList
     */
    public ArrayList<String> loadFavourites()
    {
        try{
            URL url = getClass().getResource("favourites.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                favouriteID.add(line[0]);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        return favouriteID;
    }

    /**
     * @return An ArrayList of IDs that are favourites of the user
     */
    public ArrayList<AirbnbListing> getFavourites(ArrayList<AirbnbListing> listings) {
        favouriteListings.clear();
        for (AirbnbListing listing : listings) {
            for (String id : favouriteID) {
                if (listing.getId().equals(id)) {
                    favouriteListings.add(listing);
                }
            }
        }
        return favouriteListings;
    }

    /**
     * Writes the new favourited listing into favourite.csv
     * @param listingID The ID of the listing to be added
     * @throws IOException Unable to write into csv
     */
    public void newFavourite(String listingID) throws IOException
    {
        favouriteID.add(listingID);

        try (CSVWriter favouriteWriter = new CSVWriter(
                new FileWriter("src/favourites.csv"),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        ) {
            // Write the labels into favourite.csv
            favouriteWriter.writeNext(favouritesCSVheader);
            // Write the existing favourite id back into favourite.csv
            for (String id : favouriteID) {
                String[] line = new String[1];
                line[0] = id;
                favouriteWriter.writeNext(line);
            }
        }
    }

    /**
     * Removes the favourite listing from favourite.csv
     * @param listingID The ID of the listing to be removed
     * @throws IOException Unable to write into csv
     */
    public void removeFavourite(String listingID) throws IOException
    {
        String[] line = new String[1];

        favouriteID.remove(listingID);

        try (CSVWriter favouriteWriter = new CSVWriter(
                new FileWriter("src/favourites.csv"),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        ) {
            // Write the labels into favourite.csv
            favouriteWriter.writeNext(favouritesCSVheader);
            // Write the existing favourite id back into favourite.csv
            for (String id : favouriteID) {
                line[0] = id;
                // Write only if id does not match
                if (!id.equals(listingID)) {
                    favouriteWriter.writeNext(line);
                }
            }
        }
    }
}
