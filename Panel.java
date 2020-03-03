/**
 * Abstract class Panel - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
import java.util.Iterator;
import java.util.HashMap;
import javafx.scene.layout.*;

public abstract class Panel
{
    private Filter filter;
    private HashMap<String, Borough> list;
    
    public Panel () {
        filter = new Filter();
    }

    public Pane getPanel(int minPrice, int maxPrice){
        return null;
    }

    protected void loadBoroughs() {
        list = new HashMap<>();
        list.put("Enfield",new Borough("Enfield","ENFI",10,10,10));
        list.put("Westminster",new Borough("Westminster","WSTM",10,10,10));
        list.put("Hillingdon",new Borough("Hillingdon","HILL",10,10,10));
        list.put("Havering",new Borough("Havering","HAVE",10,10,10));
        list.put("Wandsworth",new Borough("Wandsworth","WAND",10,10,10));
        list.put("Lewisham",new Borough("Lewisham","LEWI",10,10,10));
        list.put("Tower Hamlets",new Borough("Tower Hamlets","TOWH",10,10,10));
        list.put("Hounslow",new Borough("Hounslow","HOUN",10,10,10));
        list.put("Redbridge",new Borough("Redbridge","REDB",10,10,10));
        list.put("Southwark",new Borough("Southwark","STHW",10,10,10));
        list.put("Camden",new Borough("Camden","CAMD",10,10,10));
        list.put("Bromley",new Borough("Bromley","BROM",10,10,10));
        list.put("Lambeth",new Borough("Lambeth","LAMB",10,10,10));
        list.put("Kensington and Chelsea",new Borough("Kensington and Chelsea","KENS",10,10,10));
        list.put("Islington",new Borough("Islington","ISLI",10,10,10));
        list.put("Barnet",new Borough("Barnet","BARN",10,10,10));
        list.put("Richmond upon Thames",new Borough("Richmond upon Thames","RICH",10,10,10));
        list.put("Kingston upon Thames",new Borough("Kingston upon Thames","KING",10,10,10));
        list.put("Harrow",new Borough("Harrow","HRRW",10,10,10));
        list.put("Sutton",new Borough("Sutton","SUTT",10,10,10));
        list.put("Haringey",new Borough("Haringey","HRGY",10,10,10));
        list.put("Brent",new Borough("Brent","BREN",10,10,10));
        list.put("Bexley",new Borough("Bexley","BEXL",10,10,10));
        list.put("Hackney",new Borough("Hackney","HACK",10,10,10));
        list.put("Greenwich",new Borough("Greenwich","GWCH",10,10,10));
        list.put("Hammersmith and Fulham",new Borough("Hammersmith and Fulham","HAMM",10,10,10));
        list.put("Waltham Forest",new Borough("Waltham Forest","WALT",10,10,10));
        list.put("Merton",new Borough("Merton","MERT",10,10,10));
        list.put("Croydon",new Borough("Croydon","CROY",10,10,10));
        list.put("Newham",new Borough("Newham","NEWH",10,10,10));
        list.put("Ealing",new Borough("Ealing","EALI",10,10,10));
        list.put("City of London",new Borough("City of London","CITY",10,10,10));
        list.put("Barking and Dagenham",new Borough("Barking and Dagenham","BARK",10,10,10));
    }

    protected Borough matchBoroughs(String name) {
        if (list.get(name) != null) {
            return list.get(name);
        }
        return null;
    }

    protected HashMap<String, Integer> boroughToPropertyNo (int minPrice, int maxPrice) {
        HashMap <String, Integer> boroughs = new HashMap<>();
        Iterator<AirbnbListing> it = filter.getInRange(minPrice, maxPrice).iterator();
        while(it.hasNext()) {
            AirbnbListing current = it.next();
            String currentBorough = current.getNeighbourhood();
            if (!boroughs.containsKey(currentBorough)) {
                boroughs.put(currentBorough, 1);
            }
            else {
                int inc = boroughs.get(currentBorough) + 1;
                boroughs.replace(currentBorough, inc);
            }
        }
        return boroughs;
    }
}
