package berkley.server.dateredifiner;

import java.util.Date;

import berkley.server.rmi.DateRedifiner;
import berkley.server.time.LocalTimeResolver;

public class LocalDateRedifinerImpl implements DateRedifiner {

	LocalTimeResolver localTimeResolver;
	private long millsSent;
	
	public LocalDateRedifinerImpl() {
		localTimeResolver = LocalTimeResolver.getInstance();
	}
	
	@Override
	public Long getCurrentLocaDateDifference(long millis) {
		long instant = localTimeResolver.getCurrentLocalDateTime().getTime();
		millsSent = instant;
		return instant - millis;
	}

	@Override
	public void redefineLocalDate(long millis) {
		Date oldDate = localTimeResolver.getCurrentLocalDateTime();
		Date newDate = new Date(millis + millsSent);
		LocalTimeResolver.getInstance().setCurrentLocalDateTime(newDate);
		System.out.println(String.format("New date incoming: old date: %s, new date: %s", oldDate.toString(), newDate.toString()));
	}
	
}
