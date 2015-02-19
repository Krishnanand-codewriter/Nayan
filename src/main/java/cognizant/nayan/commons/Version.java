package cognizant.nayan.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class Version {
	float baseVersion,currentVersion,incompatibleVersion;
	Properties prop = new Properties();
	FileInputStream fin;
	FileOutputStream fout;

	public Version() {
		try {
			fin = new FileInputStream("Nayan.properties");
			prop.load(fin);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBaseVersion(String baseVersion){
		try {
			fout = new FileOutputStream("Nayan.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prop.setProperty("Nayan_Base_Version", baseVersion);
		try {
			prop.store(fout, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getBaseVersion(){

		return prop.getProperty("Nayan_Base_Version");

	}

	public void setCurrentVersion(String currentVersion){
		try {
			fout = new FileOutputStream("Nayan.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prop.setProperty("Nayan_Base_Version", currentVersion);
		try {
			prop.store(fout, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCurrentVersion(){

		return prop.getProperty("Nayan_Current_Version");
	}

	public void setIncompatibleVersion(String incompatibleVersion){
		try {
			fin.close();
			fout = new FileOutputStream("Nayan.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prop.setProperty("Nayan_Incompatible_Version", incompatibleVersion);
		try {
			prop.store(fout, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setScenarioName(String scenarioName, int scenarioNum){
		try {
			fout = new FileOutputStream("Nayan.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prop.setProperty("Scenario"+scenarioNum, scenarioName);
		try {
			prop.store(fout, null);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getRunType(){
		
		return prop.getProperty("ImageType");
	}
	
	public String getScenarioName(String scenario){
		
		return prop.getProperty(scenario);
	}

	public String getIncompatibleVersion(){

		return prop.getProperty("Nayan_Incompatible_Version");
	}
}
