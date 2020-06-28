package in.flyspark.sonarqube.exporter.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.exception.ServiceUnavailableException;
import in.flyspark.sonarqube.exporter.rest.WSEndpoints;
import in.flyspark.sonarqube.exporter.util.Utils;

public class ReportService {

	private static final int PAGE_SIZE = 500;

	public static List<Issues> report(final String sonarQubeURL, final String loginToken, final String projectKey, final String projectName)
			throws ServiceUnavailableException {
		List<Issues> issues = new ArrayList<Issues>();
		Issues.Resp respObject = fetchIssues(sonarQubeURL, projectKey, loginToken, 1);
		if (respObject != null) {
			if (respObject.issues != null) {
				issues.addAll(respObject.issues);
				int total = respObject.total;
				if (total > PAGE_SIZE) {
					int max = total / PAGE_SIZE + 2;
					for (int i = 2; i < max; ++i) {
						respObject = fetchIssues(sonarQubeURL, projectKey, loginToken, i);
						if (respObject != null) {
							issues.addAll(respObject.issues);
						}
					}
				}
			}
		}
		return issues;
	}

	private static Issues.Resp fetchIssues(final String sonarQubeURL, final String projectKey, final String loginToken, final int page)
			throws ServiceUnavailableException {
		String url = getURL(sonarQubeURL, projectKey, loginToken, page);
		return JSON.parseObject(WSEndpoints.getIssue(url), Issues.Resp.class);
	}

	private static String getURL(final String sonarQubeURL, final String projectKey, final String loginToken, final int page) {
		return Utils.makeValidURL(sonarQubeURL + "/api/issues/search?projects=" + projectKey + "&statuses=OPEN&pageSize=-1&pageIndex=" + page);
	}
}
