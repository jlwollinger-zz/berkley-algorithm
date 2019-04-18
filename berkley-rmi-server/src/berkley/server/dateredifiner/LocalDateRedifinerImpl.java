package berkley.server.dateredifiner;

import java.util.Date;

import berkley.client.dateredifiner.DateRedifiner;
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
		LocalTimeResolver.getInstance().setCurrentLocalDateTime(millis + millsSent);
		System.out.println(String.format("New date incoming: old date: %s, new date: %s \n", oldDate.toString(), new Date(millis + millsSent).toString()));
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
