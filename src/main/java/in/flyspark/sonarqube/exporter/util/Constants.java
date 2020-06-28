package in.flyspark.sonarqube.exporter.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

	public static final String SUMMARY_SHEET = "Summary";
	public static final String REPORT_SHEET = "Report";

	public static final String getFileName(String projectName) {
		SimpleDateFormat fileNameDate = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
		return projectName + "_SonarQubeReport_" + fileNameDate.format(new Date());
	}

}
