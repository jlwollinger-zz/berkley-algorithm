package berkley.server;

import java.util.Arrays;

import berkley.server.server.Server;

public class Main {

	public static void main(String[] args) {
		Server server = new Server();
		if(args.length >= 1) {
			server.start(Arrays.asList(args));
		}else {
			server.start(Arrays.asList("localhost"));
		}
	}

}
