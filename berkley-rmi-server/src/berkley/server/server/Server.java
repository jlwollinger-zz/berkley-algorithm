package berkley.server.server;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import berkley.server.dateredifiner.LocalDateRedifinerImpl;
import berkley.server.rmi.DateRedifiner;

public class Server {

	private static boolean terminated;
	//GOAL: 818
	private Date currentServerDate = new Date();
	//TODO: Alterar algoritmo de cálculo para fazer conforme anotado no e-mail
	
	public void start(List<String> addresses) {
		List<DateRedifiner> remotesDateRedifiners = getRemotDateRedifners(addresses);
		Map<DateRedifiner, Long> remoteDifferencesToMaster = getRemoteDatesDifference(remotesDateRedifiners);
		computeMyLocalDateTime(remoteDifferencesToMaster);

		long mean = getAvarageDifference(remoteDifferencesToMaster);
		Map<DateRedifiner, Long> patches = getTimePatches(remoteDifferencesToMaster, mean);
		sendTimeToAdjust(patches);
	}

	private void sendTimeToAdjust(Map<DateRedifiner, Long> patches) {
		patches.forEach((dateRedifiner, timeToAdjust) -> {
			dateRedifiner.redefineLocalDate(timeToAdjust);
		});

	}

	private Map<DateRedifiner, Long> getTimePatches(Map<DateRedifiner, Long> remoteDifferencesToMaster,
			long average) {
		Map<DateRedifiner, Long> differenceMap = new HashMap<>();
		remoteDifferencesToMaster.forEach((dateRedifiner, difference) -> {
			long finalDiff = (difference * - 1) + average;
			differenceMap.put(dateRedifiner, finalDiff);
		});

		return differenceMap;
	}

	private void computeMyLocalDateTime(Map<DateRedifiner, Long> remoteDifferencesToMaster) {
		DateRedifiner localDateRedifiner = new LocalDateRedifinerImpl();
		remoteDifferencesToMaster.put(localDateRedifiner, localDateRedifiner.getCurrentLocaDateDifference(new Date().getTime()));
	}

	private Long getAvarageDifference(Map<DateRedifiner, Long> remoteDifferencesToMaster) {
		return (long) remoteDifferencesToMaster.values().stream().mapToLong(i -> i).sum() / remoteDifferencesToMaster.size() + 1;
		
//		return (long) remoteDifferencesToMaster.values() //
//				.stream() //
//				.mapToLong(i -> i) //
//				.average().getAsDouble();
	}

	private Map<DateRedifiner, Long> getRemoteDatesDifference(List<DateRedifiner> remotesDateRedifiners) {
		return remotesDateRedifiners //
				.stream() //
				.collect(Collectors.toMap(Function.identity(),
						(dateRedifiner) -> dateRedifiner.getCurrentLocaDateDifference(currentServerDate.getTime())));
	}

	private List<DateRedifiner> getRemotDateRedifners(List<String> addresses) {
		return Arrays.asList(getRemoteDateRedfiner("s"));
//		
//		return addresses //
//				.stream() //
//				.map(address -> getRemoteDateRedfiner(address)) //
//				.collect(Collectors.toList());
	}

	public DateRedifiner getRemoteDateRedfiner(String address) {
		return new DateRedifiner() {
			Date date = new Date(154748000489l);;
			long millisSent;
			
			@Override
			public void redefineLocalDate(long millis) {
				System.out.println(new Date(millis + millisSent));
	}
			
			@Override
			public Long getCurrentLocaDateDifference(long millis) {
				System.out.println("my last time: " + date);
				millisSent = date.getTime();
				return millisSent - millis;
			}
			
		};
//		Registry registry = null;
//		DateRedifiner dateRedifiner = null;
//		try {
//			registry = LocateRegistry.getRegistry(address);
//			dateRedifiner = (DateRedifiner) registry.lookup("DateRedifiner");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			e.printStackTrace();
//		}
//		return dateRedifiner;
//	}
	}
	
}
