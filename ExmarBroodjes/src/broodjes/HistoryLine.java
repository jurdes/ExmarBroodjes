package broodjes;
import java.util.Date;


public class HistoryLine implements Comparable<HistoryLine> {
    
    private final Date date;
    private final String description;
    private final Currency value;

    public HistoryLine(Date date, String description, Currency value) {
        this.date = date;
        this.description = description;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Currency getValue() {
        return value;
    }

    public int compareTo(HistoryLine o2) {
        return getDate().compareTo(o2.getDate());
    }
    
    public String toString() {
        return date + ": "+description+" ("+value+")";
    }

    
    
}
