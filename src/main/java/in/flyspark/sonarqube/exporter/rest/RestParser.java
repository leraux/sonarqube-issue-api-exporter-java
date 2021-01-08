package in.flyspark.sonarqube.exporter.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.exception.ServiceUnavailableException;
import in.flyspark.sonarqube.exporter.util.AppUtils;

public class RestParser {
	private static final Logger logger = LoggerFactory.getLogger(RestParser.class.getSimpleName());

	private RestParser() {
	}

	private static final int PAGE_SIZE = 500;

	// SonarQube Community Edition's Issue API Limit
	private static final int SONAR_ISSUE_API_LIMIT = 10_000;

	public static List<Issues> createIssueList(final String sonarQubeURL, final String token, final String projectKey) throws ServiceUnavailableException {
		List<Issues> issues = new ArrayList<>();
		Issues.Resp respObject = fetchAndParseIssues(sonarQubeURL, projectKey, token, 1);
		if (respObject != null && respObject.issues != null) {
			issues.addAll(respObject.issues);
			int total = respObject.total;
			if (total > PAGE_SIZE) {
				int max = total / PAGE_SIZE + 2;
				for (int i = 2; i < max; ++i) {
					respObject = fetchAndParseIssues(sonarQubeURL, projectKey, token, i);
					if (respObject != null) {
						issues.addAll(respObject.issues);
					}
					if (issues.size() >= SONAR_ISSUE_API_LIMIT) {
						logger.error("Issue API Limit of {} has been exceeded", SONAR_ISSUE_API_LIMIT);
						return issues;
					}
				}
			}
		}
		return issues;
	}

	private static Issues.Resp fetchAndParseIssues(final String sonarQubeURL, final String projectKey, final String token, final int page) throws ServiceUnavailableException {
		String url = makeURL(sonarQubeURL, projectKey, token, page);
		return JSON.parseObject(RestServices.getIssue(url), Issues.Resp.class);
	}

	// token is unused and kept for future purpose
	private static String makeURL(final String sonarQubeURL, final String projectKey, final String token, final int page) {
		return AppUtils.makeValidURL(sonarQubeURL + "/api/issues/search?projects=" + projectKey + "&statuses=OPEN&pageSize=-1&pageIndex=" + page);
	}
}
