package in.flyspark.sonarqube.exporter.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.rest.WSEndpoints;

public class ReportService {
	private static final Logger log = LogManager.getLogger(ReportService.class);

	private static final int PAGE_SIZE = 500;

	public static void report(final String host, final String token, final String projectKey, final String projectName, final String version) {
		try {
			List<Issues> issues = new ArrayList<Issues>();
			Issues.Resp respObject = fetchIssues(host, projectKey, token, 1);
			if (respObject != null) {
				if (respObject.issues != null) {
					issues.addAll(respObject.issues);
					int total = respObject.total;
					if (total > PAGE_SIZE) {
						int max = total / PAGE_SIZE + 2;
						for (int i = 2; i < max; ++i) {
							respObject = fetchIssues(host, projectKey, token, i);
							if (respObject != null) {
								issues.addAll(respObject.issues);
							}
						}
					}
				}
				new ExcelService().exportExcel(issues, projectName, version);
			}
		} catch (Exception ex) {
			log.error("Sorry, something wrong!", ex);
		}
	}

	private static Issues.Resp fetchIssues(final String host, final String projectKey, final String token, final int page) throws Exception {
		String url = getURL(host, projectKey, token, page);
		return JSON.parseObject(WSEndpoints.getIssue(url), Issues.Resp.class);
	}

	private static String getURL(final String host, final String projectKey, final String token, final int page) {
		return host + "/api/issues/search?projects=" + projectKey + "&statuses=OPEN&pageSize=-1&pageIndex=" + page;
	}
}
