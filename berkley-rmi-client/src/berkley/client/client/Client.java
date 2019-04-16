package berkley.client.client;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import berkley.server.time.LocalTimeResolver;
import berkley.server.util.Util;

public class Client {

	private Date localDate;

	private static final boolean terminate = false;

	public void start() throws RemoteException {
		Date randomDate = Util.generateRandomDate(Timestamp.valueOf("2018-01-01 00:00:00").getTime());
		LocalTimeResolver.getInstance().setCurrentLocalDateTime(randomDate);
		while (!terminate) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public Date getCurrentLocalDate() {
		return this.localDate;
	}

	public void setNewCurrentLocalDate(Date newLocalDate) {
		System.out.println(String.format("New date incoming: old date: %s, new date: %s", this.localDate.toString(), newLocalDate.toString()));
		this.localDate = newLocalDate;
	}

}
