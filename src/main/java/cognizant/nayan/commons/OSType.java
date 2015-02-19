package cognizant.nayan.commons;

public  class OSType {
	String winType, macType,linuxType;
	boolean isWin, isMac, isLinux;
	
	public boolean isOSWinType(){
		winType = System.getProperty("os.name").toLowerCase();
		if(winType.contains("win")){
			return isWin = true;
		}else{
			return isWin = false;
		}	
	}
	
	public boolean isOSMacType(){
		macType = System.getProperty("os.name").toLowerCase();
		if(macType.contains("mac")){
			return isMac = true;
		}else{
			return isMac = false;
		}	
	}
	
	public boolean isOSLinuxType(){
		linuxType = System.getProperty("os.name").toLowerCase();
		if(linuxType.contains("linux")){
			return isLinux = true;
		}else{
			return isLinux = false;
		}	
	}

}
