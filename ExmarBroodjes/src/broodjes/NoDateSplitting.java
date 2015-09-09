package broodjes;
import java.util.List;


public class NoDateSplitting implements HistoryLineValidator {

    @Override
    public boolean isNeeded(List<HistoryLine> lines, int lineNr) {
        if (lineNr<lines.size()-1) {
            if (lines.get(lineNr).getDate().equals(lines.get(lineNr+1).getDate())) {
                return true;
            }
        }
        return false;
    }

}
