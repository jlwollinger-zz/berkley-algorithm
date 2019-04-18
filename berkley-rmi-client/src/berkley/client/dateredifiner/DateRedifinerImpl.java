package berkley.client.dateredifiner;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import berkley.client.time.LocalTimeResolver;

@SuppressWarnings("serial")
public class DateRedifinerImpl extends UnicastRemoteObject implements DateRedifiner {

	private LocalTimeResolver localTimeResolver;
	private String address;
	private int port;
	

	public DateRedifinerImpl(String address, int port) throws RemoteException {
		this.address = address;
		this.port = port;
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

	@Override
	public String getAddress() {
		return address + ":" + port;
	}
}
