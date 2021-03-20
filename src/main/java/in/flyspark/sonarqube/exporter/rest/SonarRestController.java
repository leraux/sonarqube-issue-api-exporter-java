package in.flyspark.sonarqube.exporter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.flyspark.sonarqube.exporter.entity.Issues;

@RestController
@RequestMapping
public class SonarRestController {

	@Autowired
	private SonarRestParser restMaker;

	@RequestMapping("/sonar")
	List<Issues> sonar(@RequestParam String sonarQubeURL, @RequestParam String token, @RequestParam String projectKey, @RequestParam String projectName) throws Exception {
		return restMaker.getIssues(sonarQubeURL, token, projectKey);
	}


}
