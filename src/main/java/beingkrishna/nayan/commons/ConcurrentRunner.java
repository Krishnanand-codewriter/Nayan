package cognizant.nayan.commons;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Krishnanand
 *
 */
public class ConcurrentRunner {
	private static final int BROWSERS = 5;
	String[] browsers;
	public ConcurrentRunner(String[] browsers) {
		this.browsers = browsers;
	}
	public void execute(){
		ExecutorService executor = Executors.newFixedThreadPool(BROWSERS);
 
		for (int i = 0; i < browsers.length; i++) {
 
			String browser = browsers[i];
			Runnable worker = new ParallelExecutor(browser);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
 
		}
		System.out.println("\nFinished all threads");
	}
}
