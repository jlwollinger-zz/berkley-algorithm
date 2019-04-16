package berkley.client.time;

import java.util.Date;

public class Util {

	public static Date generateRandomDate(long from) {
		Date date = generateRandomDate(from, new Date().getTime());
		return date;
	}

	public static Date generateRandomDate(long from, long to) {
		long millis = getRandomTimeBetweenTwoDates(from, to);
		Date randomDate = new Date(millis);
		return randomDate;
	}
	
	private static long getRandomTimeBetweenTwoDates(long beginTime, long endTime) {
	    long diff = endTime - beginTime + 1;
	    return beginTime + (long) (Math.random() * diff);
	}

}
