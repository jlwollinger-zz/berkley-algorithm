package berkley.client.dateredifiner;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import berkley.server.rmi.DateRedifiner;
import berkley.server.time.LocalTimeResolver;

@SuppressWarnings("serial")
public class DateRedifinerImpl extends UnicastRemoteObject implements DateRedifiner {

	private LocalTimeResolver localTimeResolver;

	public DateRedifinerImpl() throws RemoteException {
		localTimeResolver = LocalTimeResolver.getInstance();
	}

	@Override
	public Long getCurrentLocaDateDifference(long millis) {
		long instant = localTimeResolver.getCurrentLocalDateTime().getTime();
		return instant - millis;
	}

	@Override
	public void redefineLocalDate(long minutes) {
		long currentTime = localTimeResolver.getCurrentLocalDateTime().getTime();
		Date newDate = new Date(currentTime - minutes);
		localTimeResolver.setCurrentLocalDateTime(newDate);
	}

}
