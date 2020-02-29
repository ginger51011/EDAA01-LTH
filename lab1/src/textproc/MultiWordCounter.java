package textproc;
import java.util.HashMap;
import java.util.Map;

public class MultiWordCounter implements TextProcessor{
	Map<String, Integer> wordMap = new HashMap<String, Integer>();
	
	public MultiWordCounter (String[] landskap) {
		for (String s : landskap) {
			wordMap.put(s, 0);
		}
	}
	
	public void process(String w) {
		if (wordMap.containsKey(w)) {
			wordMap.replace(w, wordMap.get(w) + 1);
		}
	}
	
	public void report() {
		for (String w : wordMap.keySet()) {
			System.out.println(w + ": " + wordMap.get(w));
		}
	}

}
