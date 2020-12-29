package in.flyspark.sonarqube.exporter.util;

public class AppUtils {

	private AppUtils() {
	}

	public static final String SUMMARY = "Summary";
	public static final String REPORT = "Report";

	public static final String ANALYSIS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String REPORT_DATE_FORMAT = "dd-MMM-yyyy hh:mm:ss a";
	public static final String FILE_FORMAT = "dd_MMM_yyyy_HH_mm_ss";

	public static final boolean ignore(String line) {
		return Utils.isBlank(line);
	}

	public static final String getFileName(String projectName, String suffix) {
		return projectName + "_SonarQube_Report_" + suffix;
	}

}
