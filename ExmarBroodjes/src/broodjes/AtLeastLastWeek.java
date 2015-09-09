package broodjes;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AtLeastLastWeek implements HistoryLineValidator {

	@Override
	public boolean isNeeded(List<HistoryLine> lines, int lineNr) {
		HistoryLine last = lines.get(lines.size() - 1);
		int atLeastWeek = getField(last.getDate(), Calendar.WEEK_OF_YEAR);
		int atLeastYear = getField(last.getDate(), Calendar.YEAR);
		HistoryLine line = lines.get(lineNr);
		int lastNotSelectedWeek = getField(line.getDate(),
				Calendar.WEEK_OF_YEAR);
		int lastNotSelectedYear = getField(line.getDate(), Calendar.YEAR);
		return (atLeastWeek == lastNotSelectedWeek && atLeastYear == lastNotSelectedYear);
	}

	private static int getField(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

}
