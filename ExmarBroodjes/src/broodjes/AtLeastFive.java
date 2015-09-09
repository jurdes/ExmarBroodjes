package broodjes;
import java.util.List;


public class AtLeastFive implements HistoryLineValidator {

    @Override
    public boolean isNeeded(List<HistoryLine> lines, int lineNr) {
        return (lineNr>=lines.size()-5);
    }

}
