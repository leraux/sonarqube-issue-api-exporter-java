# SonarQubeExporter
Command line tool for SonarQube Results

Steps To Run & Export The Excel:

1. Run SonarQube instance on your machine i.e. StartSonar.bat
2. Open command prompt and mount root folder of Exporter
3. Run following maven command
    mvn -q clean compile exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.controller.Exporter" -Dexec.args="http://localhost:9000/ da5ad9e667faf116e42a8aadfb33d0b64f45bd99 JavaScriptKey JS_Project_Report v1.0.0.0"

    Here -Dexec.args are:
    1. Host/ip running SonarQube instance (Ex. http://localhost:9000/)
    2. Token generated in SonarQube admin panel (Ex. da5ad9e667faf116e42a8aadfb33d0b64f45bd99, visit http://localhost:9000/account/security/)
    3. Project Key of targeted project (Ex. JavaScriptKey, visit http://localhost:9000/admin/projects_management)
    4. Project Name (Ex. - JS_Project_Report, This name is of your choice, used for name of excel file and inside excel sheets) 
    5. Version of excel file (Ex. v.1.0, v1.0.0.0, vLatestVersion, FreeText, v1.0.0.0_16_06_2019, etc)
    
Replace -Dexec.args arguments with your respective values.

4. Output excel file looks something like this - JS_Project_Report_SonarQubeReport_16_Jun_2019_01_32_v1.0.0.0.xlsx
5. Excel file will open AUTOMATICALLY


Format of Maven Command:
  
  Path_To_Root_Dir>mvn -q clean compile exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.controller.Exporter" -Dexec.args="1.Host 2.Token 3.ProjectKey 4.ProjectName(Name of your choice) 5.Version(Ex - v.1.0 , v1.0.0.0 , freetext)"
  
Example:

  Path_To_Root_Dir>mvn -q clean compile exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.controller.Exporter" -Dexec.args="http://localhost:9000/ da5ad9e667faf116e42a8aadfb33d0b64f45bd99 JavaScriptKey JS_Project_Report v1.0.0.0"
