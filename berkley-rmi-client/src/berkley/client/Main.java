package berkley.client;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

import berkley.client.client.Client;

public class Main {

	public static void main(String[] args) throws RemoteException, UnknownHostException {
		Client client = new Client();
		client.start();
	}

}
