package in.flyspark.sonarqube.exporter.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.util.AppUtils;

public class ReportFilterService {

	private ReportFilterService() {
	}

	private static Map<String, Long> severity;
	private static Map<String, Long> type;

	public static Map<String, Long> getSeverity() {
		return severity;
	}

	public static Map<String, Long> getType() {
		return type;
	}

	public static void processFilter(List<Issues> issues) {
		Stream<Issues> filterSeverity = issues.stream().filter(issue -> (!AppUtils.ignore(issue.getLine())));
		Map<String, Long> mapSeverity = new TreeMap<>(filterSeverity.collect(Collectors.groupingBy(Issues::getSeverity, Collectors.counting())));

		filterSeverity = issues.stream().filter(issue -> (!AppUtils.ignore(issue.getLine())));
		Map<String, Long> mapType = new TreeMap<>(filterSeverity.collect(Collectors.groupingBy(Issues::getType, Collectors.counting())));

		Map<String, Long> mapSeverityOrdered = new LinkedHashMap<>(mapSeverity);
		Map<String, Long> mapTypeOrdered = new LinkedHashMap<>(mapType);

		mapSeverityOrdered.put("Total", mapSeverity.values().stream().mapToLong(Long::longValue).sum());
		mapTypeOrdered.put("Total", mapType.values().stream().mapToLong(Long::longValue).sum());

		severity = mapSeverityOrdered;
		type = mapTypeOrdered;
	}

}
