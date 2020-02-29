package textproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.List;

public class GeneralWordCounter implements TextProcessor {
	private Map<String, Integer> wordMap = new TreeMap<String, Integer>();
	private Set<String> stopwords;
	
	public GeneralWordCounter(Set<String> stopwords) {
		this.stopwords = stopwords;
	}

	public void process(String w) {
		if (!stopwords.contains(w)) {
		//if (!checkWord(w)) {
			if (wordMap.containsKey(w)) {
				wordMap.replace(w, wordMap.get(w) + 1);
			} else {
				wordMap.put(w, 1);
			}
		}
	}
	
	// FÖR LABB 3
	public Set<Entry<String, Integer>> getWords() {
		return wordMap.entrySet();
	}
	
	private boolean checkWord(String w) {
		boolean stop = false;
		for (String s : stopwords) {
			if (w.equals(s)) {
				stop = true;
			}
		}
		return stop;
	}

	public void report() {
		Set<Map.Entry<String, Integer>> wordSet = wordMap.entrySet();
		List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
		wordList.sort(new WordCountComparator());
		Collections.reverse(wordList); // Så det blir Störst först
		
		for (int i = 0; i < 100; i++) {
			System.out.println(wordList.get(i));
		}
	}
}
