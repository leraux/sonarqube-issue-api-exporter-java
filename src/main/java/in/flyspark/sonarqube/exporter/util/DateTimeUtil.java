package in.flyspark.sonarqube.exporter.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	public static final String getDate() {
		SimpleDateFormat simpeDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		return simpeDateFormat.format(new Date());
	}

}
