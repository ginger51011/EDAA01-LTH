package map;

import java.util.Random;

public class HashApplication {
	public static void main(String[] args) {
		SimpleHashMap shm = new SimpleHashMap();
		Random rand = new Random();
		int limit = rand.nextInt(65);
		for (int i = 0; i < limit; i-=-1) {
			int randKey = rand.nextInt(200) - 100;
			int randValue = rand.nextInt(200) - 100;
			shm.put(randKey, randValue);
		}
		System.out.println(shm.show());
	}
}
