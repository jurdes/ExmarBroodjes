package broodjes;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LastDayHistoryValidator implements HistoryValidator {

	private Date date;
	
	public LastDayHistoryValidator(Date date) {
		this.date = removeTime(date);
	}
	
	@Override
	public boolean validate(List<HistoryLine> lines) {
		Date maxDate = new Date(0);
		for (HistoryLine line : lines) {
			maxDate = maxDate.getTime()>line.getDate().getTime() ? maxDate : line.getDate() ;
		}
		maxDate = removeTime(maxDate);
		
		return date.getTime() == maxDate.getTime();
	}
	
    private static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
	
}
