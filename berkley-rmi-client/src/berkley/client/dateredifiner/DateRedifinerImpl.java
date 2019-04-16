package berkley.client.dateredifiner;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import berkley.client.time.LocalTimeResolver;
import berkley.server.rmi.DateRedifiner;

@SuppressWarnings("serial")
public class DateRedifinerImpl extends UnicastRemoteObject implements DateRedifiner {

	private LocalTimeResolver localTimeResolver;

	public DateRedifinerImpl() throws RemoteException {
		localTimeResolver = LocalTimeResolver.getInstance();
	}

	@Override
	public Long getCurrentLocaDateDifference(long millis) {
		long currentTimeMills = localTimeResolver.getCurrentLocalDateTime().getTime();
		return currentTimeMills - millis;
	}

	@Override
	public void redefineLocalDate(long millsReceived) {
		long currentTimeMills = localTimeResolver.getCurrentLocalDateTime().getTime();
		localTimeResolver.setCurrentLocalDateTime(millsReceived + currentTimeMills);
	}
}
