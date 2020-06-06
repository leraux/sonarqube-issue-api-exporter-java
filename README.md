# SonarQubeExporter  
###### v1.0.0.0  
Command line tool for exporting results of SonarQube Analysis into excel

## How to use  

- Before running the tool make sure that SonarQube is running  
- Extract project  
- Open command-prompt/terminal and mount root of project 
- Run following maven command as described below :  
    - Command Format:  
      - mvn exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.controller.Exporter" -Dexec.args="*SonarQubeURL* *SonarToken* *ProjectKey* *ProjectName* *FileVersion*"    
    
    - Command Example:  
      - mvn exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.controller.Exporter" -Dexec.args="http://localhost:9000/ da5ad9e667faf116e42a8aadfb33d0b64f45bd99 in.flyspark:EmployeeTracking EmployeeTracking v1.0" 
   
- On success, check root of project for generated excel file.  

