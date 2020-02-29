package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {
		
		long t0 = System.nanoTime();
		
		// ArrayList<TextProcessor> tps = new ArrayList<TextProcessor>();
		// tps.add(new SingleWordCounter("nils"));
		// tps.add(new SingleWordCounter("norge"));
		// tps.add(new MultiWordCounter(REGIONS));

		Scanner s = new Scanner(new File("nilsholg.txt"));
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning
		
		// Undantagsord
		Scanner scan = new Scanner(new File("undantagsord.txt"));
		Set<String> stopwords = new HashSet<String>(); //Vilket set som funkar
		while (scan.hasNext()) {
			stopwords.add(scan.next().toLowerCase());
		}
		
		TextProcessor gwc = new GeneralWordCounter(stopwords);

		while (s.hasNext()) {
			String word = s.next().toLowerCase();

			gwc.process(word);
		}

		s.close();
		scan.close();
		
		gwc.report();
		long t1 = System.nanoTime();
		System.out.println("tid: " + (t1 - t0) / 1000000.0 + " ms");
	}
}