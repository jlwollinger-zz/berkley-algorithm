package berkley.server.time;

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
		instance.timeInMills = new Date().getTime();
		return instance;
	}

	public Date getCurrentLocalDateTime() {
		return new Date(timeInMills);
	}

	public void setCurrentLocalDateTime(long mills) {
		this.timeInMills = mills;
	}
	
	public long getCurrentLocalDateTimeInMills() {
		return timeInMills;
	}

	public long getCurrentLocalDateTimeInMillsAndIncOneSecond() {
		return timeInMills += TimeUnit.SECONDS.toMillis(1);
	}


}
