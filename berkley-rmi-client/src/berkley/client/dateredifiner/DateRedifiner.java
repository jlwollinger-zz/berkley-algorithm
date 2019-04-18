package berkley.client.dateredifiner;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DateRedifiner extends Remote {

	public Long getCurrentLocaDateDifference(long millis) throws RemoteException;
	
	public void redefineLocalDate(long millis) throws RemoteException;
	
	public String getAddress() throws RemoteException;
	
}
