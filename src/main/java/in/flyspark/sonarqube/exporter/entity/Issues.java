package in.flyspark.sonarqube.exporter.entity;

import java.util.List;

public class Issues {
	private String severity;

	private String updateDate;

	private String line;

	private String author;

	private String rule;

	private String project;

	private String effort;

	private String message;

	private String creationDate;

	private String type;

	private String[] tags;

	private String component;

	private String organization;

	private String debt;

	private String key;

	private String hash;

	private String status;

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getEffort() {
		return effort;
	}

	public void setEffort(String effort) {
		this.effort = effort;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDebt() {
		return debt;
	}

	public void setDebt(String debt) {
		this.debt = debt;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Issues [severity = " + severity + ", updateDate = " + updateDate + ", line = " + line + ", author = " + author + ", rule = " + rule + ", project = " + project + ", effort = " + effort + ", message = " + message + ", creationDate = " + creationDate + ", type = " + type + ", tags = " + tags + ", component = " + component + ", organization = " + organization + ", debt = " + debt
				+ ", key = " + key + ", hash = " + hash + ", status = " + status + "]";
	}

	public static class Resp {
		public int total;
		public List<Issues> issues;
	}
}
