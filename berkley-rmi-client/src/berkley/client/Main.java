package berkley.client;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

import berkley.client.client.Client;

public class Main {

	public static void main(String[] args) throws RemoteException, UnknownHostException {
		int port = 6999;
		if(args.length == 1) {
			port = Integer.parseInt(args[0]);
		}else {
		}
		
		Client client = new Client(port);
		client.start();

	}

}
