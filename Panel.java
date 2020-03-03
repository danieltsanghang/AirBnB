/**
 * Abstract class Panel - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
import java.util.Iterator;
import java.util.HashMap;
import javafx.scene.layout.*;

public abstract class Panel extends Pane
{
    private Filter filter;
    private HashMap<String, Borough> boroughList;
    
    public Panel () {
        filter = new Filter();
    }

    public Pane getPanel(int minPrice, int maxPrice){
        return null;
    }

    protected void loadBoroughs() {
        boroughList = new HashMap<>();
        boroughList.put("Barking and Dagenham", new Borough ("Barking and Dagenham","BARK",1246,365,162));
        boroughList.put("Barnet", new Borough ("Barnet","BARN",675,96,213));
        boroughList.put("Bexley", new Borough ("Bexley","BEXL",1322,514,199));
        boroughList.put("Brent", new Borough ("Brent","BREN",627,313,139));
        boroughList.put("Bromley", new Borough ("Bromley","BROM",1129,698,349));
        boroughList.put("Camden", new Borough ("Camden","CAMD",782,350,102));
        boroughList.put("City of London", new Borough ("City of London","CITY",951,448,59));
        boroughList.put("Croydon", new Borough ("Croydon","CROY",898,742,213));
        boroughList.put("Ealing" ,new Borough ("Ealing","EALI",498,431,177));
        boroughList.put("Enfield", new Borough ("Enfield","ENFI",900,27,218));
        boroughList.put("Greenwich", new Borough ("Greenwich","GWCH",1126,491,170));
        boroughList.put("Hackney", new Borough ("Hackney","HACK",984,359,89));
        boroughList.put("Hammersmith and Fulham", new Borough ("Hammersmith and Fulham","HAMM",670,526,129));
        boroughList.put("Haringey", new Borough ("Haringey","HRGY",886,260,111));
        boroughList.put("Harrow", new Borough ("Harrow","HRRW",439,136,218));
        boroughList.put("Havering", new Borough ("Havering","HAVE",1361,136,304));
        boroughList.put("Hillingdon", new Borough ("Hillingdon","HILL",255,309,250));
        boroughList.put("Hounslow", new Borough ("Hounslow","HOUN",375,581,158));
        boroughList.put("Islington", new Borough ("Islington","ISLI",898,389,63));
        boroughList.put("Kensington and Chelsea", new Borough ("Kensington and Chelsea","KENS",756,452,81));
        boroughList.put("Kingston upon Thames", new Borough ("Kingston upon Thames","KING",609,810,126));
        boroughList.put("Lambeth", new Borough ("Lambeth","LAMB",904,623,110));
        boroughList.put("Lewisham", new Borough ("Lewisham","LEWS",1033,623,113));
        boroughList.put("Merton", new Borough ("Merton","MERT",714,679,165));
        boroughList.put("Newham", new Borough ("Newham","NEWH",1101,365,127));
        boroughList.put("Redbridge", new Borough ("Redbridge","REDB",1131,155,219));
        boroughList.put("Richmond upon Thames", new Borough ("Richmond upon Thames","RICH",534,629,173));
        boroughList.put("Southwark", new Borough ("Southwark","STHW",955,515,99));
        boroughList.put("Sutton", new Borough ("Sutton","SUTT",738,858,152));
        boroughList.put("Tower Hamlets", new Borough ("Tower Hamlets","TOWH",1040,460,80));
        boroughList.put("Waltham Forest", new Borough ("Waltham Forest","WALT",1033,256,93));
        boroughList.put("Wandsworth", new Borough ("Wandsworth","WAND",803,585,97));
        boroughList.put("Westminster", new Borough ("Westminster","WSTM",842,484,97));

    }

    protected Borough matchBoroughs(String name) {
        if (boroughList.get(name) != null) {
            return boroughList.get(name);
        }
        return null;
    }

    protected String matchBoroughToAbbrev(String abbrev) {
        Iterator<Borough> boroughListIT = boroughList.values().iterator();

        boolean notFound = true;
        while(boroughListIT.hasNext() && notFound) {
            Borough current = boroughListIT.next();
            if (current.getAbbrevName().equals(abbrev)) {
                return current.getName();
            }
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
