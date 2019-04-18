package berkley.server.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import berkley.client.dateredifiner.DateRedifiner;
import berkley.server.time.LocalTimeResolver;

public class Server {

	private static boolean terminated;
	private LocalTimeResolver localTimeResolver = LocalTimeResolver.getInstance();
	
	public void start(List<String> addresses) throws IOException, InterruptedException {
		while (!terminated) {
			byte i = 0;
			while (i++ != 5) {
				Thread.sleep(TimeUnit.SECONDS.toMillis(1));
				printLocalTime();
			}
			redefineDates(addresses);
		}
	}

	private void redefineDates(List<String> addresses) throws InterruptedException {
		List<DateRedifiner> remotesDateRedifiners = getRemotDateRedifners(addresses);
		Map<DateRedifiner, Long> remoteDifferencesToMaster = getRemoteDatesDifference(remotesDateRedifiners);
		long average = getAvarageDifference(remoteDifferencesToMaster);

		Map<DateRedifiner, Long> patches = getTimePatches(remoteDifferencesToMaster, average);
		sendTimeToAdjust(patches);
		adjustLocalTime(average);
		printDateRedifined();
	}

	private void adjustLocalTime(long average) {
		localTimeResolver.setCurrentLocalDateTime(localTimeResolver.getCurrentLocalDateTimeInMills() + average);
	}

	private void sendTimeToAdjust(Map<DateRedifiner, Long> patches) {
		patches.forEach((dateRedifiner, timeToAdjust) -> {
			try {
				dateRedifiner.redefineLocalDate(timeToAdjust);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

	}

	private Map<DateRedifiner, Long> getTimePatches(Map<DateRedifiner, Long> remoteDifferencesToMaster, long average) {
		Map<DateRedifiner, Long> differenceMap = new HashMap<>();
		remoteDifferencesToMaster.forEach((dateRedifiner, difference) -> {
			long finalDiff = (difference * -1) + average;
			differenceMap.put(dateRedifiner, finalDiff);
		});

		return differenceMap;
	}

	private Long getAvarageDifference(Map<DateRedifiner, Long> remoteDifferencesToMaster) {
		return (long) remoteDifferencesToMaster.values().stream().mapToLong(i -> i).sum()
				/ remoteDifferencesToMaster.size() + 1;
	}

	private Map<DateRedifiner, Long> getRemoteDatesDifference(List<DateRedifiner> remotesDateRedifiners) {
		return remotesDateRedifiners //
				.stream() //
				.collect(Collectors.toMap(Function.identity(), this::getRemoteDatesDiff));
	}

	public long getRemoteDatesDiff(DateRedifiner dateRedifiner) {
		try {
			System.out.println("Getting date info from " + dateRedifiner.getAddress());
			return dateRedifiner.getCurrentLocaDateDifference(localTimeResolver.getCurrentLocalDateTimeInMills());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0l;
	}

	private List<DateRedifiner> getRemotDateRedifners(List<String> addresses) {
		return addresses //
				.stream() //
				.map(address -> getRemoteDateRedfiner(address)) //
				.collect(Collectors.toList());
	}

	public DateRedifiner getRemoteDateRedfiner(String address) {
		Registry registry;
		DateRedifiner dateRedifiner = null;
		try {
			registry = LocateRegistry.getRegistry(address, Registry.REGISTRY_PORT);

			dateRedifiner = (DateRedifiner) registry.lookup("DateRedifiner");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return dateRedifiner;
	}

	private void printDateRedifined() {
		System.out.println("----------Local date redifined----------");
	}

	private void printLocalTime() throws InterruptedException {
		System.out.println(new Date(localTimeResolver.getCurrentLocalDateTimeInMillsAndIncOneSecond()));
	}

}
