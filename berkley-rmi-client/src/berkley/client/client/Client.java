package berkley.client.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import berkley.client.time.Util;
import berkley.client.dateredifiner.DateRedifiner;
import berkley.client.dateredifiner.DateRedifinerImpl;
import berkley.client.time.LocalTimeResolver;

public class Client {

	private static final boolean terminate = false;

	private Registry registry;
	
	private int port;

	public Client(int port) throws RemoteException {
		this.port = port;
		registry = LocateRegistry.createRegistry(this.port);
	}

	public void start() throws RemoteException, UnknownHostException {
		setLocalDate();
		registerDateRedifiner();
		while (!terminate) {
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(1));
				printLocalTime();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private void registerDateRedifiner() throws AccessException, RemoteException, UnknownHostException {
		DateRedifiner dateRedifiner = new DateRedifinerImpl(InetAddress.getLocalHost().getHostAddress(), this.port);
		registry.rebind("DateRedifiner", dateRedifiner);
	}

	private void printLocalTime() {
		System.out.println(new Date(LocalTimeResolver.getInstance().getCurrentLocalDateTimeInMillsAndIncOneSecond()));
	}

	private void setLocalDate() {
		Date randomDate = Util.generateRandomDate(Timestamp.valueOf("2018-01-01 00:00:00").getTime());
		LocalTimeResolver.getInstance().setCurrentLocalDateTime(randomDate.getTime());
	}

}
