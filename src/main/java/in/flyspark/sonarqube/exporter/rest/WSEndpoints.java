package in.flyspark.sonarqube.exporter.rest;

import in.flyspark.sonarqube.exporter.exception.ServiceUnavailableException;

public class WSEndpoints {
	public static final String getIssue(final String url) throws ServiceUnavailableException {
		return WSHandler.makeServiceCall(url);
	}
}
