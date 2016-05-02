package broodjes;
import java.util.Date;


public class HistoryLine implements Comparable<HistoryLine> {
    
    private final Date date;
    private final Currency value;
	private final String fromUser;
	private final String toUser;
	private final String itemName;

    public HistoryLine(String fromUser, String toUser, Date date, String itemName, Currency value) {
        this.fromUser = fromUser;
		this.toUser = toUser;
		this.date = date;
		this.itemName = itemName;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public Currency getValue() {
        return value;
    }
    
    public String getFromUser() {
		return fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public String getItemName() {
		return itemName;
	}
	
	public boolean isSelf() {
		return fromUser.equals(toUser);
	}
	
	public int compareTo(HistoryLine o2) {
        return getDate().compareTo(o2.getDate());
    }
    
	public String toString() {
        return date + ": "+fromUser+"->"+toUser+" "+itemName+" ("+value+")";
    }
    
}
