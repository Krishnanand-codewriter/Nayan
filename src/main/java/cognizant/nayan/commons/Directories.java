package cognizant.nayan.commons;

import java.io.File;


public class Directories {

	String baseDirectory, currentDirectory, incompatibleDirectory;
	String baseResourceDir, currentResourceDir, incompatibleResourceDir;
	String baseDirectoryPath, currentDirectoryPath, incompatibleDirectoryPath;
	String baseVersion, currentVersion, incompatibleVersion;
	String curDir = System.getProperty("user.dir");
	OSType ostype = new OSType();
	Version version = new Version();

	public Directories() {
		createBaseDirectory();
		createCurrentDirectory();
		createIncompatibleDirectory();
	}

	public void createBaseDirectory(){
		baseVersion = version.getBaseVersion();
		baseDirectory = String.format("Scenario_Directory_V"+baseVersion);
		baseResourceDir = String.format(curDir+"/src/main/resources/base_Images/");
		if(ostype.isOSLinuxType()){
			if(!isDirectoryPresent(baseResourceDir+baseDirectory)){
				new File(curDir+"/src/main/resources/base_Images/"+baseDirectory).mkdir();
				baseDirectoryPath = baseResourceDir+baseDirectory;
			}else{
				baseDirectoryPath = baseResourceDir+baseDirectory;
			}
		}else if(ostype.isOSWinType()){
			if(!isDirectoryPresent(baseResourceDir+baseDirectory)){
				new File(curDir+"\\src\\main\\resources\\base_Images\\"+baseDirectory).mkdir();
				baseDirectoryPath = baseResourceDir+baseDirectory;
			}else{
				baseDirectoryPath = baseResourceDir+baseDirectory;
			}
		}

	}
	
	public void createBaseScenarioDirectory(String scenario){
		baseVersion = version.getBaseVersion();
		baseDirectory = String.format("Scenario_Directory_V"+baseVersion);
		baseResourceDir = String.format(curDir+"/src/main/resources/base_Images/");
		if(ostype.isOSLinuxType()){
			if(!isDirectoryPresent(baseResourceDir+baseDirectory+"/"+scenario)){
				new File(curDir+"/src/main/resources/base_Images/"+baseDirectory+"/"+scenario).mkdir();
				baseDirectoryPath = baseResourceDir+baseDirectory;
			}else{
				baseDirectoryPath = baseResourceDir+baseDirectory;
			}
		}else if(ostype.isOSWinType()){
			if(!isDirectoryPresent(baseResourceDir+baseDirectory+"\\"+scenario)){
				new File(curDir+"\\src\\main\\resources\\base_Images\\"+baseDirectory+"\\"+scenario).mkdir();
				baseDirectoryPath = baseResourceDir+baseDirectory;
			}else{
				baseDirectoryPath = baseResourceDir+baseDirectory;
			}
		}
	}

	public void createCurrentDirectory(){
		currentVersion = version.getCurrentVersion();
		currentDirectory = String.format("Scenario_Directory_V"+currentVersion);
		currentResourceDir = String.format(curDir+"/src/main/resources/current_Images/");
		if(ostype.isOSLinuxType()){
			if(!isDirectoryPresent(currentResourceDir+currentDirectory)){
				new File(curDir+"/src/main/resources/current_Images/"+currentDirectory).mkdir();
				currentDirectoryPath = currentResourceDir+currentDirectory;
			}else{
				currentDirectoryPath = currentResourceDir+currentDirectory;
			}
		}else if(ostype.isOSWinType()){
			if(!isDirectoryPresent(currentResourceDir+currentDirectory)){
				new File(curDir+"\\src\\main\\resources\\current_Images\\"+currentDirectory).mkdir();
				currentDirectoryPath = currentResourceDir+currentDirectory;
			}else{
				currentDirectoryPath = currentResourceDir+currentDirectory;
			}
		}

	}
	
	public void createCurrentScenarioDirectory(String scenario){
		currentVersion = version.getCurrentVersion();
		currentDirectory = String.format("Scenario_Directory_V"+currentVersion);
		currentResourceDir = String.format(curDir+"/src/main/resources/current_Images/");
		if(ostype.isOSLinuxType()){
			if(!isDirectoryPresent(currentResourceDir+currentDirectory+"/"+scenario)){
				new File(curDir+"/src/main/resources/current_Images/"+currentDirectory+"/"+scenario).mkdir();
				currentDirectoryPath = currentResourceDir+currentDirectory;
			}else{
				currentDirectoryPath = currentResourceDir+currentDirectory;
			}
		}else if(ostype.isOSWinType()){
			if(!isDirectoryPresent(currentResourceDir+currentDirectory+"\\"+scenario)){
				new File(curDir+"\\src\\main\\resources\\current_Images\\"+currentDirectory+"\\"+scenario).mkdir();
				currentDirectoryPath = currentResourceDir+currentDirectory;
			}else{
				currentDirectoryPath = currentResourceDir+currentDirectory;
			}
		}

	}

	public void createIncompatibleDirectory(){
		incompatibleVersion = version.getIncompatibleVersion();
		incompatibleDirectory = String.format("Scenario_Directory_V"+incompatibleVersion);
		incompatibleResourceDir = String.format(curDir+"/src/main/resources/incompatible_Images/");
		if(ostype.isOSLinuxType()){
			if(!isDirectoryPresent(incompatibleResourceDir+incompatibleDirectory)){
				new File(curDir+"/src/main/resources/incompatible_Images/"+currentDirectory).mkdir();
				incompatibleDirectoryPath = incompatibleResourceDir+incompatibleDirectory;
			}else{
				incompatibleDirectoryPath = incompatibleResourceDir+incompatibleDirectory;
			}
		}else if(ostype.isOSWinType()){
			if(!isDirectoryPresent(currentResourceDir+currentDirectory)){
				new File(curDir+"\\src\\main\\resources\\incompatible_Images\\"+currentDirectory).mkdir();
				incompatibleDirectoryPath = incompatibleResourceDir+incompatibleDirectory;
			}else{
				incompatibleDirectoryPath = incompatibleResourceDir+incompatibleDirectory;
			}
		}	

	}
	
	public void createIncompatibleScenarioDirectory(String scenario){
		incompatibleVersion = version.getIncompatibleVersion();
		incompatibleDirectory = String.format("Scenario_Directory_V"+incompatibleVersion);
		incompatibleResourceDir = String.format(curDir+"/src/main/resources/incompatible_Images/");
		if(ostype.isOSLinuxType()){
			if(!isDirectoryPresent(incompatibleResourceDir+incompatibleDirectory+"/"+scenario)){
				new File(curDir+"/src/main/resources/incompatible_Images/"+currentDirectory+"/"+scenario).mkdir();
				incompatibleDirectoryPath = incompatibleResourceDir+incompatibleDirectory;
			}else{
				incompatibleDirectoryPath = incompatibleResourceDir+incompatibleDirectory;
			}
		}else if(ostype.isOSWinType()){
			if(!isDirectoryPresent(currentResourceDir+currentDirectory+"\\"+scenario)){
				new File(curDir+"\\src\\main\\resources\\incompatible_Images\\"+currentDirectory+"\\"+scenario).mkdir();
				incompatibleDirectoryPath = incompatibleResourceDir+incompatibleDirectory;
			}else{
				incompatibleDirectoryPath = incompatibleResourceDir+incompatibleDirectory;
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

		return baseDirectoryPath;
	}

	public String getCurrentDirectoryPath(){

		return currentDirectoryPath;
	}

	public String getIncompatibleDirectoryPath(){

		return incompatibleDirectoryPath;
	}
}
