package broodjes;
import java.util.List;


public interface HistoryLineValidator {

    boolean isNeeded(List<HistoryLine> lines, int lineNr);
    
}
