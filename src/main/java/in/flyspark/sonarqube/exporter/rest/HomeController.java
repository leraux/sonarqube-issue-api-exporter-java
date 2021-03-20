package in.flyspark.sonarqube.exporter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.entity.Report;
import in.flyspark.sonarqube.exporter.entity.Sonar;
import in.flyspark.sonarqube.exporter.service.ExcelService;
import in.flyspark.sonarqube.exporter.service.ReportFilterService;
import in.flyspark.sonarqube.exporter.util.AppUtils;

@Controller
public class HomeController {

	@Autowired
	private SonarRestParser restMaker;

	@Autowired
	private ReportFilterService reportFilterService;

	@Autowired
	private ExcelService excelService;

	@GetMapping("/")
	public String home(Sonar sonar) {
		sonar.setSonarQubeURL("http://127.0.0.1:9000/");
		sonar.setToken("a49e3bae818987aaba02f434f6d008454a4c9610");
		sonar.setProjectKey("test-app");
		sonar.setProjectName("test-app");
		return "home";
	}

	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<ByteArrayResource> report(Sonar sonar) throws Exception {

		String fileNameTimestamp = AppUtils.getCurrentDateTime(AppUtils.FILE_FORMAT);
		String fileName = AppUtils.getFileName(sonar.getProjectName(), fileNameTimestamp);

		List<Issues> issues = restMaker.getIssues(sonar.getSonarQubeURL(), sonar.getToken(), sonar.getProjectKey());

		reportFilterService.processFilter(issues);

		Report report = new Report();
		report.setProjectKey(sonar.getProjectKey());
		report.setProjectName(sonar.getProjectName());
		report.setExportedTimestamp(AppUtils.getCurrentDateTime(AppUtils.REPORT_DATE_FORMAT));
		report.setFileName(fileName);
		report.setSeverity(reportFilterService.getSeverity());
		report.setType(reportFilterService.getType());
		report.setIssueDetails(issues);

		ByteArrayResource resource = new ByteArrayResource(excelService.exportExcel(report));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report.getFileName() + ".xlsx" + "\"").contentLength(resource.contentLength()).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
	}
}
