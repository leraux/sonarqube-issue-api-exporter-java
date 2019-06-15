package in.flyspark.sonarqube.exporter.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WSEndpoints {
	private static final Logger log = LogManager.getLogger(WSEndpoints.class);

	public static final String getIssue(final String url) {
		String response = WSHandler.makeServiceCall(url);
		log.info("Server Response : " + response);
		return response;
	}
}
