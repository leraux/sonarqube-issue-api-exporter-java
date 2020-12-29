package in.flyspark.sonarqube.exporter.entity;

import java.util.List;
import java.util.Map;

public class Report {

	private String projectKey;
	private String projectName;
	private String exportedTimestamp;
	private String fileName;
	private Map<String, Long> severity;
	private Map<String, Long> type;
	private List<Issues> issueDetails;

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getExportedTimestamp() {
		return exportedTimestamp;
	}

	public void setExportedTimestamp(String exportedTimestamp) {
		this.exportedTimestamp = exportedTimestamp;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, Long> getSeverity() {
		return severity;
	}

	public void setSeverity(Map<String, Long> severity) {
		this.severity = severity;
	}

	public Map<String, Long> getType() {
		return type;
	}

	public void setType(Map<String, Long> type) {
		this.type = type;
	}

	public List<Issues> getIssueDetails() {
		return issueDetails;
	}

	public void setIssueDetails(List<Issues> issueDetails) {
		this.issueDetails = issueDetails;
	}

	@Override
	public String toString() {
		return "Report [projectKey=" + projectKey + ", projectName=" + projectName + ", exportedTimestamp=" + exportedTimestamp + ", fileName=" + fileName + ", severity=" + severity + ", type=" + type + ", issueDetails=" + issueDetails + "]";
	}

}
