package in.flyspark.sonarqube.exporter.util;

public class Utils {
	public static boolean isNullEmpty(String obj) {
		return obj == null || obj.trim().length() == 0;
	}
}
