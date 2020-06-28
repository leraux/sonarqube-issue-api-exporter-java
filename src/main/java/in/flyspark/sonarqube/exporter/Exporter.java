package in.flyspark.sonarqube.exporter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.service.ExcelService;
import in.flyspark.sonarqube.exporter.service.ReportService;

public class Exporter {
	private static final Logger logger = LoggerFactory.getLogger(Exporter.class.getSimpleName());

	public static void main(String[] args) {

		try {
			if (args.length < 4) {
				logger.info("\nInput Sequence : Host SonarToken ProjectKey ProjectName");
				return;
			}

			String host = args[0];
			String loginToken = args[1];
			String projectKey = args[2];
			String projectName = args[3];
			List<Issues> issues = ReportService.report(host, loginToken, projectKey, projectName);
			String fileName = ExcelService.exportExcel(issues, projectName);
			logger.debug("Generated File - {}", fileName);
		} catch (Exception ex) {
			logger.error("Main : ", ex);
		}

	}
}
