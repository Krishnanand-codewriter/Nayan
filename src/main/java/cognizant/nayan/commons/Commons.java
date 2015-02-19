package cognizant.nayan.commons;


public class Commons {
	
	public void SleepTime(long millisec){
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

}
