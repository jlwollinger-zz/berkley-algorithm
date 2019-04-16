package berkley.server.rmi;

public interface DateRedifiner {

	public Long getCurrentLocaDateDifference(long millis);
	
	public void redefineLocalDate(long millis);
	
}
