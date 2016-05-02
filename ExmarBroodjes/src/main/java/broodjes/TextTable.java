package broodjes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TextTable {

	private final int nrOfColumns;
	private final int[] maxColSizes;
	private final List<List<String>> data = new ArrayList<List<String>>();

	public TextTable(int nrOfColumns) {
		this.nrOfColumns = nrOfColumns;
		maxColSizes = new int[nrOfColumns];
	}

	public void addData(String... rowData) {
		assert rowData.length == nrOfColumns;
		List<String> row = Arrays.asList(rowData);
		data.add(row);
		for (int i = 0; i < rowData.length; i++) {
			maxColSizes[i] = maxColSizes[i] < rowData[i].length() ? rowData[i].length() : maxColSizes[i];
		}
	}

	public String toString() {
		StringBuffer result = new StringBuffer();

		for (List<String> row : data) {
			for (int colNr = 0; colNr < nrOfColumns; colNr++) {
				result.append(StringUtils.rightPad(row.get(colNr),
						maxColSizes[colNr] + 1));
			}
			result.append("\n");
		}

		return result.toString();
	}

}
