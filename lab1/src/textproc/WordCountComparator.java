package textproc;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class WordCountComparator implements Comparator<Map.Entry<String, Integer>> {

	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		int diff = o1.getValue() - o2.getValue();
		
		if (diff == 0) {
			return o2.getKey().compareToIgnoreCase(o1.getKey());
		}
		return diff;
	}

}
