package berkley.server.time;

import java.util.Date;

public class LocalTimeResolver {
	
	private static LocalTimeResolver instance;
	private Date date = new Date();

	private LocalTimeResolver() {
	}

	public static LocalTimeResolver getInstance() {
		if (instance == null) {
			instance = new LocalTimeResolver();
		}
		return instance;
	}

	public Date getCurrentLocalDateTime() {
		return date;
	}

	public void setCurrentLocalDateTime(Date newDate) {
		this.date = newDate;
	}
	
	

}
