package in.flyspark.sonarqube.exporter.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

import in.flyspark.sonarqube.exporter.entity.Issues;
import in.flyspark.sonarqube.exporter.util.AppUtils;

@Service
public class SonarRestParser {

	private int PAGE_SIZE = 500;

	@Autowired
	private RestTemplate restTemplate;

	public List<Issues> getIssues(String sonarQubeURL, String token, String projectKey) throws Exception {
		List<Issues> issues = new ArrayList<>();
		Issues.Resp respObject = fetchAndParseIssues(sonarQubeURL, token, projectKey, 1);
		if (respObject != null && respObject.issues != null) {
			issues.addAll(respObject.issues);
			int total = respObject.total;
			if (total > PAGE_SIZE) {
				int max = total / PAGE_SIZE + 2;
				for (int i = 2; i < max; ++i) {
					respObject = fetchAndParseIssues(sonarQubeURL, token, projectKey, i);
					if (respObject != null) {
						issues.addAll(respObject.issues);
					}
				}
			}
		}
		return issues;
	}

	private Issues.Resp fetchAndParseIssues(String sonarQubeURL, String token, String projectKey, int page) throws Exception {
		String url = makeURL(sonarQubeURL, projectKey, token, page);
		return JSON.parseObject(getIssue(url, token), Issues.Resp.class);
	}

	private String makeURL(String sonarQubeURL, String projectKey, String token, int page) {
		return AppUtils.makeValidURL(sonarQubeURL + "/api/issues/search?projects=" + projectKey + "&statuses=OPEN&pageSize=-1&pageIndex=" + page);
	}

	public String getIssue(String url, String token) throws Exception {

		String basic = token + ":" + "";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.encodeBase64String(basic.getBytes()));

		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		return response.getBody();
	}
}
