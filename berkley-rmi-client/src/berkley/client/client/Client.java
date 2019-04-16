package berkley.client.client;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import berkley.client.time.Util;
import berkley.client.time.LocalTimeResolver;

public class Client {

	private static final boolean terminate = false;

	public void start() throws RemoteException {
		Date randomDate = Util.generateRandomDate(Timestamp.valueOf("2018-01-01 00:00:00").getTime());
		LocalTimeResolver.getInstance().setCurrentLocalDateTime(randomDate.getTime());
		while (!terminate) {
			try {
				Thread.sleep(1000);
				System.out.println(new Date(LocalTimeResolver.getInstance().getCurrentLocalDateTimeInMillsAndIncSecond()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
