package cognizant.nayan.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.Beta;

import cognizant.nayan.commons.Bool;
import cognizant.nayan.commons.Commons;
import cognizant.nayan.commons.NayanProperties;
import cognizant.nayan.commons.RunType;
import cognizant.nayan.serialized.ImagesState;
import cognizant.nayan.serialized.ReadObject;
import cognizant.nayan.statuscode.StatusCode;
import cognizant.nayan.statuscode.StatusCodeValue;
import cognizant.nayan.statuscode.StatusCodes;

/*
 * To Generate Report in EXCEL sheets
 */

/**
 * @author 416474
 *
 */

public class Reporter implements Report {
	private static String sFoldername,sWorkbookname, sSheetname, sFilename;
	private FileInputStream mIn;
	private FileOutputStream mOut;
	private HSSFWorkbook mWorkbook;
	private HSSFSheet mSheet;
	private CreationHelper mCreationHelper;
	private static HSSFCellStyle mHeaderStyle, mBaseNameStyle, mBaseUrlStyle, mCurrentNameStyle,
	mCurrentUrlStyle, mCompatibleStyle, mIncompatibleImgUrlStyle, mRemarkStyle;
	private String[] mColumnNames = Constants.HEADER;
	private ImagesState mBaseObj, mCurrentObj;
	private static NayanProperties sNayanProp = new NayanProperties();
	private static final String ROOTRESULTSFOLDERNAME = sNayanProp.getResultsFolderName();
	private static final String SEPERATOR = File.separator;
	private static final String IMAGE_COMPARISON_VERSION = sNayanProp.getIncompatibleVersion();

	public Reporter(String paramFoldername,String paramWorkbookname, String paramSheetname, String paramFilename) {
		Reporter.sFoldername = paramFoldername;
		Reporter.sWorkbookname = paramWorkbookname;
		Reporter.sSheetname = paramSheetname;
		Reporter.sFilename = paramFilename;
	}

	@Override
	public void createReportFolder() {
		if(!isDirectoryPresent()){
			createDirectory();
		}
	}

	@Override
	public void createWorkbook() {
		try{
			if(new File(Paths.FILE_PATH).isFile()){
				mIn = new FileInputStream(Paths.FILE_PATH);
				mWorkbook = new HSSFWorkbook(mIn);
			}else{
				mWorkbook = new HSSFWorkbook();
			}		
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void createSheet() {
		mSheet = mWorkbook.getSheet(sSheetname);
		if(mSheet==null){
			try{
				mSheet = mWorkbook.createSheet(sSheetname);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void developReport(){
		createHeaderStyle()
		.createCurrentNameStyle()
		.createCurrentUrlStyle()
		.createImcompatibleImgUrlStyle()
		.createRemarksStyle();
		createHeader();
		createCurrent()
		.createCompatibility()
		.createIncompatibleUrls()
		.createRemarks();
		fillRemainingCells();
	}

	@Override
	public void writeWorkbook() {
		try {
			mOut = new FileOutputStream(new File(Paths.FILE_PATH));
			mWorkbook.write(mOut);
			mOut.close();
		} catch (Exception e) {

		} 
	}

	private boolean isDirectoryPresent() {
		try{
			File file = new File(Paths.DIR_PATH);
			if(file.isDirectory()){
				return true;
			}else
				return false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;

	}

	private void createDirectory() {
		try{
			new File(Paths.DIR_PATH).mkdirs();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void createHeader() {
		Row row = null;
		Cell cell = null;
		int count = 0;
		try {
			row = mSheet.createRow(Constants.HEADER_ROW);
			for(String columnName : mColumnNames){
				cell = row.createCell(count);
				cell.setCellValue(columnName);
				cell.setCellStyle(mHeaderStyle);
				mSheet.autoSizeColumn(count);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Beta
	private Reporter createBase() {
		try{
			mBaseObj = deserializeObjects(RunType.BASE);
			Map<String, String> urlsMap = new HashMap<>();
			urlsMap = mBaseObj.getUrlsMap();
			Set<String> urlSet = urlsMap.keySet();
			Iterator<String> keyIterator = urlSet.iterator();
			int count =1;
			while(keyIterator.hasNext()) {
				String url = keyIterator.next();
				String name = urlsMap.get(url);
				writeValues(Constants.BASE_URL, url, mBaseUrlStyle,count);
				writeToARow(url, Constants.BASE_NAME, name, mBaseNameStyle);
				count++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return this;
	}

	private Reporter createCurrent(){
		try{
			mCurrentObj = deserializeObjects(RunType.CURRENT);
			Map<String, String> urlsMap = new HashMap<>();
			urlsMap = mCurrentObj.getUrlsMap();
			Set<String> urlSet = urlsMap.keySet();
			Iterator<String> keyIterator = urlSet.iterator();
			int count =1;
			while(keyIterator.hasNext()) {
				String url = keyIterator.next();
				String name = urlsMap.get(url);
				writeValuesAsLink(Constants.CURRENT_URL, url, mCurrentUrlStyle,count);
				writeToARow(url, Constants.CURRENT_NAME, name, mCurrentNameStyle);
				count++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return this;
	}

	private Reporter createCompatibility() {
		try{
			Map<String, Bool> compatibilitymap = Commons.getFilesCompatibility();
			Set<String> compatibilitySet = compatibilitymap.keySet();
			Iterator<String> compatibilityItr = compatibilitySet.iterator();
			while(compatibilityItr.hasNext()) {
				String fileName = compatibilityItr.next();
				Bool boolVal = compatibilitymap.get(fileName);
				createCompatibleStyle(boolVal);
				String val = boolVal.toString();
				writeToARow(fileName, Constants.COMPATIBLE, val, mCompatibleStyle);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return this;
	}

	private Reporter createIncompatibleUrls() {
		try{
			Map<String, String> incompatibleUrlMap = Commons.getIncompatibleUrls();
			Set<String> incompatibleUrlSet = incompatibleUrlMap.keySet();
			Iterator<String> incompatibleUrlItr = incompatibleUrlSet.iterator();
			while(incompatibleUrlItr.hasNext()) {
				String fileName = incompatibleUrlItr.next();
				String url = incompatibleUrlMap.get(fileName);
				writeToARowAsLinkForIncompatibleUrl(fileName, Constants.INCOMPATIBLE_IMG_URL, url, mIncompatibleImgUrlStyle);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return this;
	}

	private Reporter createRemarks() {
		Map<String, Integer> remarksMap = Commons.getStatusCodes();
		Set<String> remarksSet = remarksMap.keySet();
		Iterator<String> remarksItr = remarksSet.iterator();
		while(remarksItr.hasNext()) {
			String fileName = remarksItr.next();
			int statusCode = remarksMap.get(fileName);
			String remark = getRemark(statusCode);
			writeToARow(fileName, Constants.REMARKS, remark, mRemarkStyle);
		}
		return this;
	}

	private StatusCode getStatusCode(int statusCode) {
		if(statusCode == StatusCodes.BASE_IMAGE_FOUND) {
			return StatusCode.BASE_IMAGE_FOUND;
		} else if(statusCode == StatusCodes.BASE_IMAGE_NOT_FOUND) {
			return StatusCode.BASE_IMAGE_NOT_FOUND;
		} else if(statusCode == StatusCodes.CURRENT_IMAGE_FOUND) {
			return StatusCode.CURRENT_IMAGE_FOUND;
		} else if(statusCode == StatusCodes.CURRENT_IMAGE_NOT_FOUND) {
			return StatusCode.CURRENT_IMAGE_NOT_FOUND;
		} else if(statusCode == StatusCodes.BASE_URL_FOUND) {
			return StatusCode.BASE_URL_FOUND;
		} else if(statusCode == StatusCodes.BASE_URL_NOT_FOUND) {
			return StatusCode.BASE_URL_NOT_FOUND;
		} else if(statusCode == StatusCodes.CURRENT_URL_FOUND) {
			return StatusCode.CURRENT_URL_FOUND;
		} else if(statusCode == StatusCodes.BASE_CURRENT_IMAGES_NOT_FOUND) {
			return StatusCode.BASE_CURRENT_IMAGES_NOT_FOUND;
		} else if(statusCode == StatusCodes.EXTREMELY_INCOMPATIBLE_IMAGES) {
			return StatusCode.EXTREMELY_INCOMPATIBLE_IMAGES;
		} else if(statusCode == StatusCodes.MATCHED_IMAGES) {
			return StatusCode.MATCHED_IMAGES;
		} else if(statusCode == StatusCodes.MISMATCHED_IMAGES) {
			return StatusCode.MISMATCHED_IMAGES;
		} else {
			return null;
		}
	}      

	private String getRemark(int statusCode) {
		StatusCode statusCodes = getStatusCode(statusCode);
		switch (statusCodes) {
		case BASE_IMAGE_FOUND:
			return StatusCodeValue.BASE_IMAGE_FOUND;
		case BASE_IMAGE_NOT_FOUND:
			return StatusCodeValue.BASE_IMAGE_NOT_FOUND;
		case CURRENT_IMAGE_FOUND:
			return StatusCodeValue.CURRENT_IMAGE_FOUND;
		case CURRENT_IMAGE_NOT_FOUND:
			return StatusCodeValue.CURRENT_IMAGE_NOT_FOUND;
		case BASE_URL_FOUND:
			return StatusCodeValue.BASE_URL_FOUND;
		case BASE_URL_NOT_FOUND:
			return StatusCodeValue.BASE_URL_NOT_FOUND;
		case CURRENT_URL_FOUND:
			return StatusCodeValue.CURRENT_URL_FOUND;
		case CURRENT_URL_NOT_FOUND:
			return StatusCodeValue.CURRENT_URL_NOT_FOUND;
		case EXTREMELY_INCOMPATIBLE_IMAGES:
			return StatusCodeValue.EXTREMELY_INCOMPATIBLE_IMAGES;
		case MATCHED_IMAGES:
			return StatusCodeValue.MATCHED_IMAGES;
		case MISMATCHED_IMAGES:
			return StatusCodeValue.MISMATCHED_IMAGES;
		case BASE_CURRENT_IMAGES_NOT_FOUND:
			return StatusCodeValue.BASE_CURRENT_IMAGES_NOT_FOUND;
		default:
			return StatusCodeValue.ERROR;
		}
	}

	private ImagesState deserializeObjects(RunType type) {
		ImagesState obj = null;
		ReadObject<ImagesState> readObj = new ReadObject<>(type);
		obj = readObj.readObjectState(sFilename);
		return obj;
	}

	private Reporter createHeaderStyle() {
		mHeaderStyle = createStyle(HSSFColor.GOLD.index);
		Font font = mWorkbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		mHeaderStyle.setFont(font);
		return this;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private Reporter createBaseNameStyle() {
		mBaseNameStyle = createStyle(HSSFColor.LIGHT_YELLOW.index);
		return this;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private Reporter createBaseUrlStyle() {
		mBaseUrlStyle = createStyle(HSSFColor.LIGHT_YELLOW.index);
		return this;
	}

	private Reporter createCurrentNameStyle() {
		mCurrentNameStyle = createStyle(HSSFColor.LIGHT_YELLOW.index);
		return this;
	}

	private Reporter createCurrentUrlStyle() {
		mCurrentUrlStyle = createStyle(HSSFColor.LIGHT_YELLOW.index);
		return this;
	}

	private Reporter createCompatibleStyle(Bool bool) {
		switch (bool) {
		case YES:
			mCompatibleStyle = createStyle(HSSFColor.GREEN.index);
			break;
		case NO: case EXTREMELY_INCOMPATIBLE:
			mCompatibleStyle = createStyle(HSSFColor.RED.index);
			break;
		default:
			mCompatibleStyle = createStyle(HSSFColor.GREEN.index);
			break;
		}
		return this;
	}

	private Reporter createImcompatibleImgUrlStyle() {
		mIncompatibleImgUrlStyle = createStyle(HSSFColor.LIGHT_YELLOW.index);
		return this;
	}

	private Reporter createRemarksStyle() {
		mRemarkStyle = createStyle(HSSFColor.LIGHT_YELLOW.index);
		return this;
	}

	private HSSFCellStyle createStyle(short color) {
		HSSFCellStyle cellStyle = null;
		cellStyle = mWorkbook.createCellStyle();
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		short borderColor = IndexedColors.BLACK.getIndex();
		short borderLineThickness = CellStyle.BORDER_THIN;
		cellStyle.setBorderBottom(borderLineThickness);
		cellStyle.setBottomBorderColor(borderColor);
		cellStyle.setBorderLeft(borderLineThickness);
		cellStyle.setLeftBorderColor(borderColor);
		cellStyle.setBorderRight(borderLineThickness);
		cellStyle.setRightBorderColor(borderColor);
		cellStyle.setBorderTop(borderLineThickness);
		cellStyle.setTopBorderColor(borderColor);
		return cellStyle;
	}

	private void writeValues(String cellName, String cellValue, CellStyle cellStyle,int count) {
		int rowIndex = getRowNum(cellName)+count;
		int cellIndex = getColNum(cellName);
		Row row = null;
		try{
			if(rowIndex!=0) {
				row = mSheet.getRow(rowIndex);
				if(row!=null) {
					writeToCell(row, cellIndex, cellValue,cellStyle);
				}else {
					row = mSheet.createRow(rowIndex);
					row.setHeight((short)0x349);
					writeToCell(row, cellIndex, cellValue,cellStyle);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void writeValuesAsLink(String cellName, String cellValue, CellStyle cellStyle,int count) {
		int rowIndex = getRowNum(cellName)+count;
		int cellIndex = getColNum(cellName);
		Row row = null;
		try{
			if(rowIndex!=0) {
				row = mSheet.getRow(rowIndex);
				if(row!=null) {
					writeToCellAsLink(row, cellIndex, cellValue,cellStyle);
				}else {
					row = mSheet.createRow(rowIndex);
					row.setHeight((short)0x349);
					writeToCellAsLink(row, cellIndex, cellValue,cellStyle);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void writeToARow(String rowId, String colId, String cellValue, CellStyle cellStyle) {
		String val = format(rowId);
		int rowIndex = getRowNum(val);
		int cellIndex = getColNum(colId);
		Row row = null;
		try{
			if(rowIndex!=0) {
				row = mSheet.getRow(rowIndex);
				if(row!=null) {
					writeToCell(row, cellIndex, cellValue,cellStyle);
				}else {
					row = mSheet.createRow(rowIndex);
					row.setHeight((short)0x349);
					writeToCell(row, cellIndex, cellValue, cellStyle);
				}	
			}
		}catch(Exception e){
			e.printStackTrace();		
		}
	}

	@SuppressWarnings("unused")
	private void writeToARowAsLink(String rowId, String colId, String cellValue, CellStyle cellStyle) {
		String val = format(rowId);
		int rowIndex = getRowNum(val);
		int cellIndex = getColNum(colId);
		Row row = null;
		try{
			if(rowIndex!=0) {
				row = mSheet.getRow(rowIndex);
				if(row!=null) {
					writeToCellAsLink(row, cellIndex, cellValue,cellStyle);
				}else {
					row = mSheet.createRow(rowIndex);
					row.setHeight((short)0x349);
					writeToCell(row, cellIndex, cellValue, cellStyle);
				}	
			}
		}catch(Exception e){
			e.printStackTrace();		
		
		}
	}
	
	private void writeToARowAsLinkForIncompatibleUrl(String rowId, String colId, String cellValue, CellStyle cellStyle) {
		String val = format(rowId);
		int rowIndex = getRowNum(val);
		int cellIndex = getColNum(colId);
		Row row = null;
		try{
			if(rowIndex!=0) {
				row = mSheet.getRow(rowIndex);
				if(row!=null) {
					writeToCellAsLinkForIncompatibleUrl(row, cellIndex, cellValue,cellStyle);
				}else {
					row = mSheet.createRow(rowIndex);
					row.setHeight((short)0x349);
					writeToCellAsLinkForIncompatibleUrl(row, cellIndex, cellValue, cellStyle);
				}	
			}
		}catch(Exception e){
			e.printStackTrace();		
		}
	}

	private void writeToCell(Row row, int cellIndex, String cellValue, CellStyle cellStyle) {
		Cell cell = null;
		try {
			cell = row.createCell(cellIndex);
			cell.setCellValue(cellValue);
			cell.setCellStyle(cellStyle);
			mSheet.autoSizeColumn(cellIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeToCellAsLink(Row row, int cellIndex, String cellValue, CellStyle cellStyle) {
		Cell cell = null;
		try {
			Font hlink_font = mWorkbook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			cellStyle.setFont(hlink_font);
			cell = row.createCell(cellIndex);
			cell.setCellValue(cellValue);
			mCreationHelper = mWorkbook.getCreationHelper();
			Hyperlink link1 = mCreationHelper.createHyperlink(Hyperlink.LINK_URL);
			link1.setAddress(cellValue);
			cell.setHyperlink(link1);
			cell.setCellStyle(cellStyle);
			mSheet.autoSizeColumn(cellIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeToCellAsLinkForIncompatibleUrl(Row row, int cellIndex, String cellValue, CellStyle cellStyle) {
		Cell cell = null;
		try {
			Font hlink_font = mWorkbook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			cellStyle.setFont(hlink_font);
			cell = row.createCell(cellIndex);
			cell.setCellValue(Constants.VIEW_INCOMPATIBLE_IMAGE);
			mCreationHelper = mWorkbook.getCreationHelper();
			Hyperlink link1 = mCreationHelper.createHyperlink(Hyperlink.LINK_URL);
			link1.setAddress(cellValue);
			cell.setHyperlink(link1);
			cell.setCellStyle(cellStyle);
			mSheet.autoSizeColumn(cellIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillRemainingCells() {
		Row row = null;
		Cell cell = null;
		for(int i=1; i<=mSheet.getLastRowNum();i++) {
			row = mSheet.getRow(i);
			for(int j=0; j<Constants.ROW_LENGTH; j++) {
				try{
					cell = row.getCell(j);
					if(cell==null) {
						cell = row.createCell(j);
						cell.setCellStyle(mCurrentNameStyle);
					}else {
						if(cell.getCellType()== Cell.CELL_TYPE_BLANK) {
							cell.setCellStyle(mCurrentNameStyle);
						}
					}
				} catch(Exception e) {
					cell = row.createCell(j);
					cell.setCellStyle(mCurrentNameStyle);
				}
			}
		}
	}

	private int getRowNum(String cellName){
		int currentRowNum =0;
		for(Row row : mSheet){
			for(Cell cell: row){
				try{
					String cellVal = cell.getStringCellValue();
					if(cellVal.equalsIgnoreCase(cellName)){
						currentRowNum = cell.getRowIndex();
					}
				}catch(Exception e){

				}

			}
		}

		return currentRowNum;

	}

	private String format(String name) {
		String formatedName = null;
		if(name.contains("_") || name.contains("Firefox")) {
			formatedName = name.replace("_", " ").replace("Firefox", "").trim();
			return formatedName;
		}else {
			return name;
		}
	}

	private int getColNum(String cellName){
		int currentColNum =0;
		for(Row row : mSheet){
			for(Cell cell: row){
				try{
					String cellVal = cell.getStringCellValue();
					if(cellVal.equalsIgnoreCase(cellName)){
						currentColNum = cell.getColumnIndex();
					}
				}catch(Exception e){

				}

			}
		}
		return currentColNum;

	}
	
	private static class Paths {	
		private static final String FILE_EXTENSION = ".xls";
		private static final String RESOURCES_PATH  = "src"+SEPERATOR+"main"+SEPERATOR+"resources";
		private static final String FILE_PATH = RESOURCES_PATH+SEPERATOR+ROOTRESULTSFOLDERNAME+SEPERATOR
				+sFoldername+SEPERATOR+sWorkbookname+IMAGE_COMPARISON_VERSION+FILE_EXTENSION;
		private static final String DIR_PATH = RESOURCES_PATH+SEPERATOR+ROOTRESULTSFOLDERNAME+SEPERATOR
				+sFoldername;
	}

	private static class Constants {
		private static final int HEADER_ROW = 0;
		private static final String BASE_NAME = "Base_Name";
		private static final String BASE_URL = "Base_Url";
		private static final String CURRENT_NAME = "Current_Name";
		private static final String CURRENT_URL = "Current_Url";
		private static final String COMPATIBLE = "Compatible";
		private static final String INCOMPATIBLE_IMG_URL = "Incompatible_Img_Url";
		private static final String REMARKS = "Remarks";
		private static final String[] HEADER = {CURRENT_URL,CURRENT_NAME, COMPATIBLE, INCOMPATIBLE_IMG_URL, REMARKS};
		private static final int ROW_LENGTH = HEADER.length;
		private static final String VIEW_INCOMPATIBLE_IMAGE = "Click here to view incompatible image";


	}


}
