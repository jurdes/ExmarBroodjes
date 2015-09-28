package broodjes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;


public class History {

    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private final ListMap<String, HistoryLine> historyLines = new ListMap<>();

    public History() {
    }

    public void addEvent(String user, Date date, String event, Currency value) {
        historyLines.add(user, new HistoryLine(date, event, value));
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
        	StringBuffer result = new StringBuffer();
            result.append(entry.getKey()+":\n");
            List<HistoryLine> lines = entry.getValue();
            
            if (historyValidator.validate(lines)) {
            
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
	
	            for (int lineNr=0; lineNr<lines.size(); lineNr++) {
	                HistoryLine line = lines.get(lineNr);
	                balance = balance.add(line.getValue());
	                String balancePart = line.getValue()+" = " + balance;
	                balancePart = StringUtils.rightPad(balancePart, 18);
	
	                if (lineNr>=neededLineNr) {
	                    if (lineNr==neededLineNr && lineNr>0) {
	                        result.append(lineNr+" acties verborgen\n");
	                    }
	                    result.append(DATE_FORMATTER.format(line.getDate())+": "+balancePart+" : "+line.getDescription()+"\n");
	                }
	            }
            	finalResult.append(result);
            	finalResult.append("\n\n");
            }
        }

        return finalResult.toString();
	}
	

}
