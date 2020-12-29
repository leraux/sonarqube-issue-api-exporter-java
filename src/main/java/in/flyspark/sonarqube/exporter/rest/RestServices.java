package in.flyspark.sonarqube.exporter.rest;

import in.flyspark.sonarqube.exporter.exception.ServiceUnavailableException;

public class RestServices {

	private RestServices() {
	}

	public static final String getIssue(final String url) throws ServiceUnavailableException {
		return RequestMaker.makeServiceCall(url);
	}
}
