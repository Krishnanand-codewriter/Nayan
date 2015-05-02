package cognizant.nayan.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Krishnanand
 *
 */
public final class NayanProperties{
	float mBaseVersion,mCurrentVersion,mIncompatibleVersion;
	Properties mNayanProperties = new Properties();
	Properties mScenarioProperties = new Properties();
	FileInputStream mFinNayanProp,mFinScenarioProp;
	FileOutputStream mFout;

	public NayanProperties() {
		try {
			mFinNayanProp = new FileInputStream("Nayan.properties");
			mFinScenarioProp = new FileInputStream("Scenarios.properties");
			mNayanProperties.load(mFinNayanProp);
			mScenarioProperties.load(mFinScenarioProp);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBaseVersion(String baseVersion){
		try {
			mFout = new FileOutputStream("Nayan.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mNayanProperties.setProperty("Nayan_Base_Version", baseVersion);
		try {
			mNayanProperties.store(mFout, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getBaseVersion(){

		return mNayanProperties.getProperty("Nayan_Base_Version");

	}

	public void setCurrentVersion(String currentVersion){
		try {
			mFout = new FileOutputStream("Nayan.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mNayanProperties.setProperty("Nayan_Base_Version", currentVersion);
		try {
			mNayanProperties.store(mFout, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCurrentVersion(){

		return mNayanProperties.getProperty("Nayan_Current_Version");
	}

	public void setIncompatibleVersion(String incompatibleVersion){
		try {
			mFinNayanProp.close();
			mFout = new FileOutputStream("Nayan.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mNayanProperties.setProperty("Nayan_Incompatible_Version", incompatibleVersion);
		try {
			mNayanProperties.store(mFout, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setScenarioName(String scenarioName, int scenarioNum){
		try {
			mFout = new FileOutputStream("Scenarios.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mScenarioProperties.setProperty("Scenario"+scenarioNum, scenarioName);
		try {
			mScenarioProperties.store(mFout, null);
			mFout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getRunType(){

		return mNayanProperties.getProperty("ImageType");
	}

	public String getScenarioName(String scenario){

		return mScenarioProperties.getProperty(scenario);
	}

	@Deprecated
	public boolean compareImages(){
		if(mNayanProperties.getProperty("Image_Comparison").toUpperCase().equals("YES")){
			return true;
		}else if(mNayanProperties.getProperty("Image_Comparison").toUpperCase().equals("NO")){
			return false;
		}else{
			throw new IllegalArgumentException("Define Image_Comparison property in 'Nayan.properties' file as either Yes or No only");
		}
	}

	public String getIncompatibleVersion(){

		return mNayanProperties.getProperty("Nayan_Incompatible_Version");
	}

	public String getBaseURL(){

		return mNayanProperties.getProperty("Base_Url");
	}

	public String getCurrentURL(){

		return mNayanProperties.getProperty("Current_Url");
	}

	public boolean isJavaScriptDisabled() {
		if(mNayanProperties.getProperty("Disable_Javascript").equalsIgnoreCase("true")) {
			return false;
		}else{
			return true;
		}
	}
	
	public String getProxySetStatus() {
		return mNayanProperties.getProperty("ProxySet");
	}
	
	public String getProxyHost() {
		return mNayanProperties.getProperty("ProxyHost");
	}
	
	public String getProxyPort() {
		return mNayanProperties.getProperty("ProxyPort");
	}
	
	public String getBrandName() {
		return mNayanProperties.getProperty("BrandName");
	}
	
	public String getBrandName1() {
		return mNayanProperties.getProperty("BrandName1");
	}
	
	public String getLocale(){
		return mNayanProperties.getProperty("Locale");
	}
	
	public String getDefaultBrowser(){
		return mNayanProperties.getProperty("DefaultBrowser");
	}
	
	public String getChromeDriverPath(){
		return mNayanProperties.getProperty("ChromeDriver");
	}
	
	public String getIEDriverPath(){
		return mNayanProperties.getProperty("IEDriver");
	}
	
	public String getProxyUser(){
		return mNayanProperties.getProperty("ProxyUser");
	}
	
	public String getProxyPassword(){
		return mNayanProperties.getProperty("ProxyPassword");
	}

	public void setFilename(String filename){
		try {
			mFout = new FileOutputStream("Scenarios.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mScenarioProperties.setProperty("Filename_"+filename, filename+".ser");
		try {
			mScenarioProperties.store(mFout, null);
			mFout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getFileName(String filename) {
		return mScenarioProperties.getProperty("Filename_"+filename);
	}
	
	public String getImageFormat(){
		return mNayanProperties.getProperty("Image_Format");
	}
	
	public String getIncludeAllLinks(){
		return mNayanProperties.getProperty("Include_All_Links");
	}
	
	public String getResultsFolderName(){
		return mNayanProperties.getProperty("Foldername");
	}
	
	public String getWorkbookname() {
		return mNayanProperties.getProperty("Workbookname");
	}
	
	public String getWorksheetname() {
		return mNayanProperties.getProperty("Worksheetname");
	}
	
	public String getImageComparisonType() {
		return mNayanProperties.getProperty("Image_Comparsion_Type");
	}
}
