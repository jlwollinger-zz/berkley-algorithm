package berkley.client.time;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LocalTimeResolver {
	
	private static LocalTimeResolver instance;
	private long timeInMills;
	
	private LocalTimeResolver() {
	}

	public static LocalTimeResolver getInstance() {
		if (instance == null) {
			instance = new LocalTimeResolver();
		}
		return instance;
	}

	public Date getCurrentLocalDateTime() {
		return new Date(timeInMills);
	}
	
	public long getCurrentLocalDateTimeInMills() {
		return timeInMills;
	}

	public void setCurrentLocalDateTime(long newMillisDate) {
		Date oldDate = new Date(timeInMills);
		Date newDate = new Date(newMillisDate);
		
		System.out.println(String.format("New date incoming: old date: %s, new date: %s", oldDate.toString(), newDate.toString()));
		timeInMills = newMillisDate;
	}

	public long getCurrentLocalDateTimeInMillsAndIncOneSecond() {
		return timeInMills += TimeUnit.SECONDS.toMillis(1);
	}
}
