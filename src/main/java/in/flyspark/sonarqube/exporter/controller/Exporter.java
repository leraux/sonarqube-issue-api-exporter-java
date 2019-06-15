package in.flyspark.sonarqube.exporter.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import in.flyspark.sonarqube.exporter.service.ReportService;

/*
  
  @author FlySpark
  
  Run from command line using maven
  
  Format:
  Path_To_Root_Dir>mvn -q clean compile exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.controller.Exporter" -Dexec.args="1.Host 2.Token 3.ProjectKey 4.ProjectName(Name of your choice) 5.Version(Ex - v.1.0 , v1.0.0.0 , freetext)"
  
  Example:
  Path_To_Root_Dir>mvn -q clean compile exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.controller.Exporter" -Dexec.args="http://localhost:9000/ da5ad9e667faf116e42a8aadfb33d0b64f45bd99 JavaScriptKey JS_Project_Report v1.0.0.0"
 
*/
public class Exporter {
	private static final Logger log = LogManager.getLogger(Exporter.class);

	public static void main(String[] args) {

		try {
			if (args.length < 5) {
				log.info("\nInput Sequence : 1.Host 2.Token 3.ProjectKey 4.ProjectName(Name of your choice) 5.Version(Ex - v.1.0 , v1.0.0.0 , freetext)");
				return;
			}

			ReportService.report(args[0], args[1], args[2], args[3], args[4]);
		} catch (Exception ex) {
			log.error("Main : ", ex);
		}

	}
}
