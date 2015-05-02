package cognizant.nayan.reporting;

/**
 * @author 416474
 *
 */
public interface Report {

	void createWorkbook();
	
	void createSheet();
	
	void writeWorkbook();

	void createReportFolder();
}
