package cognizant.nayan.commons;

/**
 * @author Krishnanand
 *
 */
public  class OSType{
	private String mWinType, mMacType,mLinuxType;
	boolean mIsWin, mIsMac, mIsLinux;
	
	public boolean isOSWinType(){
		mWinType = System.getProperty("os.name").toLowerCase();
		if(mWinType.contains("win")){
			return mIsWin = true;
		}else{
			return mIsWin = false;
		}	
	}
	
	public boolean isOSMacType(){
		mMacType = System.getProperty("os.name").toLowerCase();
		if(mMacType.contains("mac")){
			return mIsMac = true;
		}else{
			return mIsMac = false;
		}	
	}
	
	public boolean isOSLinuxType(){
		mLinuxType = System.getProperty("os.name").toLowerCase();
		if(mLinuxType.contains("linux")){
			return mIsLinux = true;
		}else{
			return mIsLinux = false;
		}	
	}

}
