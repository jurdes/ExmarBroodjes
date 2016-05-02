package broodjes;

import java.util.List;

public class NoHistoryValidation implements HistoryValidator {

	@Override
	public boolean validate(List<HistoryLine> lines) {
		return true;
	}

}
