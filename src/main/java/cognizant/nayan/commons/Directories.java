package cognizant.nayan.commons;

import java.io.File;


/**
 * @author 416474
 *
 */
public class Directories {

	private String mBaseDirectory, mCurrentDirectory, mIncompatibleDirectory;
	private String mBaseResourceDir, mCurrentResourceDir, mIncompatibleResourceDir;
	private String mBaseDirectoryPath, mCurrentDirectoryPath, mIncompatibleDirectoryPath;
	private String mBaseVersion, mCurrentVersion, mIncompatibleVersion;
	private String mCurDir = System.getProperty("user.dir");
	private OSType mOstype = new OSType();
	private NayanProperties mNayanProp = new NayanProperties();

	public Directories() {
		createBaseDirectory();
		createCurrentDirectory();
		createIncompatibleDirectory();
	}

	private void createBaseDirectory(){
		mBaseVersion = mNayanProp.getBaseVersion();
		mBaseDirectory = String.format("Scenario_Directory_V"+mBaseVersion);
		mBaseResourceDir = String.format(mCurDir+"/src/main/resources/base_Images/");
		if(mOstype.isOSLinuxType()){
			if(!isDirectoryPresent(mBaseResourceDir+mBaseDirectory)){
				new File(mCurDir+"/src/main/resources/base_Images/"+mBaseDirectory).mkdirs();
				mBaseDirectoryPath = mBaseResourceDir+mBaseDirectory;
			}else{
				mBaseDirectoryPath = mBaseResourceDir+mBaseDirectory;
			}
		}else if(mOstype.isOSWinType()){
			if(!isDirectoryPresent(mBaseResourceDir+mBaseDirectory)){
				new File(mCurDir+"\\src\\main\\resources\\base_Images\\"+mBaseDirectory).mkdirs();
				mBaseDirectoryPath = mBaseResourceDir+mBaseDirectory;
			}else{
				mBaseDirectoryPath = mBaseResourceDir+mBaseDirectory;
			}
		}

	}
	
	public void createBaseScenarioDirectory(String scenario){
		mBaseVersion = mNayanProp.getBaseVersion();
		mBaseDirectory = String.format("Scenario_Directory_V"+mBaseVersion);
		mBaseResourceDir = String.format(mCurDir+"/src/main/resources/base_Images/");
		if(mOstype.isOSLinuxType()){
			if(!isDirectoryPresent(mBaseResourceDir+mBaseDirectory+"/"+scenario)){
				new File(mCurDir+"/src/main/resources/base_Images/"+mBaseDirectory+"/"+scenario).mkdirs();
				mBaseDirectoryPath = mBaseResourceDir+mBaseDirectory;
			}else{
				mBaseDirectoryPath = mBaseResourceDir+mBaseDirectory;
			}
		}else if(mOstype.isOSWinType()){
			if(!isDirectoryPresent(mBaseResourceDir+mBaseDirectory+"\\"+scenario)){
				new File(mCurDir+"\\src\\main\\resources\\base_Images\\"+mBaseDirectory+"\\"+scenario).mkdirs();
				mBaseDirectoryPath = mBaseResourceDir+mBaseDirectory;
			}else{
				mBaseDirectoryPath = mBaseResourceDir+mBaseDirectory;
			}
		}
	}

	private void createCurrentDirectory(){
		mCurrentVersion = mNayanProp.getCurrentVersion();
		mCurrentDirectory = String.format("Scenario_Directory_V"+mCurrentVersion);
		mCurrentResourceDir = String.format(mCurDir+"/src/main/resources/current_Images/");
		if(mOstype.isOSLinuxType()){
			if(!isDirectoryPresent(mCurrentResourceDir+mCurrentDirectory)){
				new File(mCurDir+"/src/main/resources/current_Images/"+mCurrentDirectory).mkdirs();
				mCurrentDirectoryPath = mCurrentResourceDir+mCurrentDirectory;
			}else{
				mCurrentDirectoryPath = mCurrentResourceDir+mCurrentDirectory;
			}
		}else if(mOstype.isOSWinType()){
			if(!isDirectoryPresent(mCurrentResourceDir+mCurrentDirectory)){
				new File(mCurDir+"\\src\\main\\resources\\current_Images\\"+mCurrentDirectory).mkdirs();
				mCurrentDirectoryPath = mCurrentResourceDir+mCurrentDirectory;
			}else{
				mCurrentDirectoryPath = mCurrentResourceDir+mCurrentDirectory;
			}
		}

	}
	
	public void createCurrentScenarioDirectory(String scenario){
		mCurrentVersion = mNayanProp.getCurrentVersion();
		mCurrentDirectory = String.format("Scenario_Directory_V"+mCurrentVersion);
		mCurrentResourceDir = String.format(mCurDir+"/src/main/resources/current_Images/");
		if(mOstype.isOSLinuxType()){
			if(!isDirectoryPresent(mCurrentResourceDir+mCurrentDirectory+"/"+scenario)){
				new File(mCurDir+"/src/main/resources/current_Images/"+mCurrentDirectory+"/"+scenario).mkdirs();
				mCurrentDirectoryPath = mCurrentResourceDir+mCurrentDirectory;
			}else{
				mCurrentDirectoryPath = mCurrentResourceDir+mCurrentDirectory;
			}
		}else if(mOstype.isOSWinType()){
			if(!isDirectoryPresent(mCurrentResourceDir+mCurrentDirectory+"\\"+scenario)){
				new File(mCurDir+"\\src\\main\\resources\\current_Images\\"+mCurrentDirectory+"\\"+scenario).mkdirs();
				mCurrentDirectoryPath = mCurrentResourceDir+mCurrentDirectory;
			}else{
				mCurrentDirectoryPath = mCurrentResourceDir+mCurrentDirectory;
			}
		}

	}

	private void createIncompatibleDirectory(){
		mIncompatibleVersion = mNayanProp.getIncompatibleVersion();
		mIncompatibleDirectory = String.format("Scenario_Directory_V"+mIncompatibleVersion);
		mIncompatibleResourceDir = String.format(mCurDir+"/src/main/resources/incompatible_Images/");
		if(mOstype.isOSLinuxType()){
			if(!isDirectoryPresent(mIncompatibleResourceDir+mIncompatibleDirectory)){
				new File(mCurDir+"/src/main/resources/incompatible_Images/"+mCurrentDirectory).mkdirs();
				mIncompatibleDirectoryPath = mIncompatibleResourceDir+mIncompatibleDirectory;
			}else{
				mIncompatibleDirectoryPath = mIncompatibleResourceDir+mIncompatibleDirectory;
			}
		}else if(mOstype.isOSWinType()){
			if(!isDirectoryPresent(mIncompatibleResourceDir+mIncompatibleDirectory)){
				new File(mCurDir+"\\src\\main\\resources\\incompatible_Images\\"+mCurrentDirectory).mkdirs();
				mIncompatibleDirectoryPath = mIncompatibleResourceDir+mIncompatibleDirectory;
			}else{
				mIncompatibleDirectoryPath = mIncompatibleResourceDir+mIncompatibleDirectory;
			}
		}	

	}
	
	public void createIncompatibleScenarioDirectory(String scenario){
		mIncompatibleVersion = mNayanProp.getIncompatibleVersion();
		mIncompatibleDirectory = String.format("Scenario_Directory_V"+mIncompatibleVersion);
		mIncompatibleResourceDir = String.format(mCurDir+"/src/main/resources/incompatible_Images/");
		if(mOstype.isOSLinuxType()){
			if(!isDirectoryPresent(mIncompatibleResourceDir+mIncompatibleDirectory+"/"+scenario)){
				new File(mCurDir+"/src/main/resources/incompatible_Images/"+mIncompatibleDirectory+"/"+scenario).mkdirs();
				mIncompatibleDirectoryPath = mIncompatibleResourceDir+mIncompatibleDirectory;
			}else{
				mIncompatibleDirectoryPath = mIncompatibleResourceDir+mIncompatibleDirectory;
			}
		}else if(mOstype.isOSWinType()){
			if(!isDirectoryPresent(mIncompatibleResourceDir+mIncompatibleDirectory+"\\"+scenario)){
				new File(mCurDir+"\\src\\main\\resources\\incompatible_Images\\"+mIncompatibleDirectory+"\\"+scenario).mkdirs();
				mIncompatibleDirectoryPath = mIncompatibleResourceDir+mIncompatibleDirectory;
			}else{
				mIncompatibleDirectoryPath = mIncompatibleResourceDir+mIncompatibleDirectory;
			}
		}	

	}

	private boolean isDirectoryPresent(String dirPath){
		try{
			File file = new File(dirPath);
			if(file.isDirectory()){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){

		}
		return false;	
	}

	public String getBaseDirectoryPath(){

		return mBaseDirectoryPath;
	}

	public String getCurrentDirectoryPath(){

		return mCurrentDirectoryPath;
	}

	public String getIncompatibleDirectoryPath(){

		return mIncompatibleDirectoryPath;
	}
}
