/**
 * Abstract class Panel - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collections;
import javafx.scene.layout.*;

public abstract class Panel extends Pane
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
        list.put("Barking and Dagenham", new Borough ("Barking and Dagenham","BARK",1246,365,162));
        list.put("Barnet", new Borough ("Barnet","BARN",675,96,213));
        list.put("Bexley", new Borough ("Bexley","BEXL",1322,514,199));
        list.put("Brent", new Borough ("Brent","BREN",627,313,139));
        list.put("Bromley", new Borough ("Bromley","BROM",1129,698,349));
        list.put("Camden", new Borough ("Camden","CAMD",782,350,102));
        list.put("City of London", new Borough ("City of London","CITY",951,448,59));
        list.put("Croydon", new Borough ("Croydon","CROY",898,742,213));
        list.put("Ealing" ,new Borough ("Ealing","EALI",498,431,177));
        list.put("Enfield", new Borough ("Enfield","ENFI",900,27,218));
        list.put("Greenwich", new Borough ("Greenwich","GWCH",1126,491,170));
        list.put("Hackney", new Borough ("Hackney","HACK",984,359,89));
        list.put("Hammersmith and Fulham", new Borough ("Hammersmith and Fulham","HAMM",670,526,129));
        list.put("Haringey", new Borough ("Haringey","HRGY",886,260,111));
        list.put("Harrow", new Borough ("Harrow","HRRW",439,136,218));
        list.put("Havering", new Borough ("Havering","HAVE",1361,136,304));
        list.put("Hillingdon", new Borough ("Hillingdon","HILL",255,309,250));
        list.put("Hounslow", new Borough ("Hounslow","HOUN",375,581,158));
        list.put("Islington", new Borough ("Islington","ISLI",898,389,63));
        list.put("Kensington and Chelsea", new Borough ("Kensington and Chelsea","KENS",756,452,81));
        list.put("Kingston upon Thames", new Borough ("Kingston upon Thames","KING",609,810,126));
        list.put("Lambeth", new Borough ("Lambeth","LAMB",904,623,110));
        list.put("Lewisham", new Borough ("Lewisham","LEWS",1033,623,113));
        list.put("Merton", new Borough ("Merton","MERT",714,679,165));
        list.put("Newham", new Borough ("Newham","NEWH",1101,365,127));
        list.put("Redbridge", new Borough ("Redbridge","REDB",1131,155,219));
        list.put("Richmond upon Thames", new Borough ("Richmond upon Thames","RICH",534,629,173));
        list.put("Southwark", new Borough ("Southwark","STHW",955,515,99));
        list.put("Sutton", new Borough ("Sutton","SUTT",738,858,152));
        list.put("Tower Hamlets", new Borough ("Tower Hamlets","TOWH",1040,460,80));
        list.put("Waltham Forest", new Borough ("Waltham Forest","WALT",1033,256,93));
        list.put("Wandsworth", new Borough ("Wandsworth","WAND",803,585,97));
        list.put("Westminster", new Borough ("Westminster","WSTM",842,484,97));

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
            } else {
                int inc = boroughs.get(currentBorough) + 1;
                boroughs.replace(currentBorough, inc);
            }
        }
        return boroughs;
    }
}
