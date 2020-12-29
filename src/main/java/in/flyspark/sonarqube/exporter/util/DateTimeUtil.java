package in.flyspark.sonarqube.exporter.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeUtil {

	private DateTimeUtil() {
	}

	public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";

	public static String getCurrentDateTime(String format) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
		return simpleDateFormat.format(calendar.getTime());
	}

}
