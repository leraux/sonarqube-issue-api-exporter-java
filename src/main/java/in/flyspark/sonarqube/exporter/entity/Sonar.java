package in.flyspark.sonarqube.exporter.entity;

public class Sonar {

	private String sonarQubeURL;
	private String token;
	private String projectKey;
	private String projectName;

	public String getSonarQubeURL() {
		return sonarQubeURL;
	}

	public void setSonarQubeURL(String sonarQubeURL) {
		this.sonarQubeURL = sonarQubeURL;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

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

	@Override
	public String toString() {
		return "Sonar [sonarQubeURL=" + sonarQubeURL + ", token=" + token + ", projectKey=" + projectKey + ", projectName=" + projectName + "]";
	}

}
