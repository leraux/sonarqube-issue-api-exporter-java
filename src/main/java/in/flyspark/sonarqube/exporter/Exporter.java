package in.flyspark.sonarqube.exporter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.entity.Report;
import in.flyspark.sonarqube.exporter.rest.RestParser;
import in.flyspark.sonarqube.exporter.service.ExcelService;
import in.flyspark.sonarqube.exporter.service.ReportFilterService;
import in.flyspark.sonarqube.exporter.util.AppUtils;
import in.flyspark.sonarqube.exporter.util.DateTimeUtil;

public class Exporter {
	private static final Logger logger = LoggerFactory.getLogger(Exporter.class.getSimpleName());

	public static void main(String[] args) {

		try {
			if (args.length < 4) {
				logger.info("\nInput Sequence : SonarQubeURL SonarToken ProjectKey ProjectName");
				return;
			}

			String sonarQubeURL = args[0];
			String token = args[1];
			String projectKey = args[2];
			String projectName = args[3];

			String fileNameTimestamp = DateTimeUtil.getCurrentDateTime(AppUtils.FILE_FORMAT);
			String fileName = AppUtils.getFileName(projectName, fileNameTimestamp);

			List<Issues> issues = RestParser.createIssueList(sonarQubeURL, token, projectKey);
			ReportFilterService.processFilter(issues);
			Report report = new Report();
			report.setProjectKey(projectKey);
			report.setProjectName(projectName);
			report.setExportedTimestamp(DateTimeUtil.getCurrentDateTime(AppUtils.REPORT_DATE_FORMAT));
			report.setFileName(fileName);
			report.setSeverity(ReportFilterService.getSeverity());
			report.setType(ReportFilterService.getType());
			report.setIssueDetails(issues);

			ExcelService.exportExcel(report);
			logger.debug("Generated File - {}", fileName);
		} catch (Exception ex) {
			logger.error("Main : ", ex);
		}

	}
}
