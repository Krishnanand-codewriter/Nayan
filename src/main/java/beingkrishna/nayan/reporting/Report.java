package cognizant.nayan.reporting;

/**
 * @author Krishnanand
 *
 */
public interface Report {

	void createWorkbook();
	
	void createSheet();
	
	void writeWorkbook();

	void createReportFolder();
}
