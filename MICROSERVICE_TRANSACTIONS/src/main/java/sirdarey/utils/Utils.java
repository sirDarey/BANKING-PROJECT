package sirdarey.utils;

import java.util.Random;

public class Utils {

	public long generateRandom(int length) {
		Random rand = new Random();
		return rand.nextLong((long)Math.pow(10, length), (long)Math.pow(10, length+1));
	}
}
