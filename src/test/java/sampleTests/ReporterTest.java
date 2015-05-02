package sampleTests;

import org.junit.Test;

import cognizant.nayan.reporting.Reporter;

public class ReporterTest {

	@Test
	public void test() {
		Reporter r = new Reporter("Hello","Hi", "Yo","Play");
		r.createReportFolder();
		r.createWorkbook();
		r.createSheet();
		r.developReport();
		r.writeWorkbook();
		
	}

}
