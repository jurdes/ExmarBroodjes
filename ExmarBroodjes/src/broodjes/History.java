package broodjes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class History {

    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private final ListMap<String, HistoryLine> historyLines = new ListMap<>();

    public History() {
    }

    public void addEvent(String fromUser, String toUser, Date date, String itemName, Currency value) {
    	HistoryLine line = new HistoryLine(fromUser, toUser, date, itemName, value);
    	historyLines.add(fromUser, new HistoryLine(fromUser, toUser, date, itemName, value));
    	if (!line.isSelf()) {
    		historyLines.add(toUser, line);
    	} 
    }

    public String toString() {
    	return toString(new NoHistoryValidation());
    }

	public String toString(HistoryValidator historyValidator) {

        StringBuffer finalResult = new StringBuffer();

        List<HistoryLineValidator> validators = new ArrayList<>();
        validators.add(new AtLeastLastWeek());
        validators.add(new AtLeastFive());
        validators.add(new NoDateSplitting());

        for (Entry<String, List<HistoryLine>> entry : historyLines.entrySet()) {
            
            List<HistoryLine> lines = entry.getValue();
        	
            if (historyValidator.validate(lines)) {
            	finalResult.append(exportUser(validators, entry.getKey(), lines));
            	finalResult.append("\n\n");
            }
        }

        return finalResult.toString();
	}

	private String exportUser(List<HistoryLineValidator> validators, String user, List<HistoryLine> lines) {
		
    	StringBuffer result = new StringBuffer();
		
    	result.append(user+":\n");
    	
		TextTable historyTable = new TextTable(9);
		historyTable.addData("Datum","Prijs","","Saldo","","Koper","","Ontvanger","Item");
         
		Currency balance = Currency.ZERO;

		int neededLineNr = 0;
		for (int lineNr=lines.size()-1; lineNr>=0; lineNr--) {
		    boolean needed = false;
		    for (HistoryLineValidator validator : validators) {
		        needed = needed || validator.isNeeded(lines, lineNr);
		    }
		    if (!needed) {
		        neededLineNr = lineNr+1;
		        break;
		    }
		}
		
		Map<String, Integer> amounts = new HashMap<String,Integer>();

		for (int lineNr=0; lineNr<lines.size(); lineNr++) {
		    HistoryLine line = lines.get(lineNr);
		    
		    if (line.getItemName().indexOf("terugbetaling")<0 && line.getToUser().equals(user)) {
		    	Integer amount = amounts.get(line.getItemName());
		    	if (amount == null) { 
		    		amount = 0;
		    	}
		    	amounts.put(line.getItemName(), amount+1);
		    }
		    
		    if (line.getToUser().equals(user) && !line.isSelf()) {
		    	balance = balance.add(line.getValue().negate());
		    } else if (line.getFromUser().equals(user) && !line.isSelf()) {
		    	balance = balance.add(line.getValue());
		    } 

		    if (lineNr>=neededLineNr) {
		        if (lineNr==neededLineNr && lineNr>0) {
		            result.append(lineNr+" acties verborgen\n");
		        }
		        historyTable.addData(DATE_FORMATTER.format(line.getDate()), ""+line.getValue(), "=>", ""+balance, ":",line.getFromUser(),"=>",line.getToUser(), line.getItemName());
		    }
		}
		result.append(historyTable);
		
		result.append("\n");
		Entry<String,Integer> favorite = getMaximum(amounts.entrySet());
		result.append("Favoriet: "+favorite.getKey()+" ("+favorite.getValue()+"x)");
		result.append("\n");
		
		return result.toString();
	}
	
	
	private static Entry<String,Integer> getMaximum(Set<Entry<String, Integer>> entries) {
		Entry<String,Integer> max = null;
		for (Entry<String,Integer> entry : entries) {
			if (max == null || entry.getValue()>max.getValue()) {
				max = entry;
			}
		}
		return max;
	}

}

