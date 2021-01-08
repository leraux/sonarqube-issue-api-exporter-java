# sonar-qube-exporter  
###### v1.1.0.0  
Command line tool for exporting results of SonarQube Analysis into excel

### Pre-requisite
- Before running the tool make sure that SonarQube is running 
- Apache Maven to run project from command-prompt/terminal

### Tested on
- jdk-11.0.8
- sonarqube-8.5.1.38104 Community Edition
- sonar-scanner-4.4.0.2170
 
## How to use  

### 1) Export excel using source code

- Extract project  
- Open command-prompt/terminal and mount root of project 
- Clean & package the application: mvn clean install
- Run following maven command as described below :  
    - Command Format:  
      - mvn exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.Exporter" -Dexec.args="*SonarQubeURL* *SonarToken* *ProjectKey* *ProjectName*"    
    
    - Command Example:  
      - mvn exec:java -Dexec.mainClass="in.flyspark.sonarqube.exporter.Exporter" -Dexec.args="http://localhost:9000/ da5ad9e667faf116e42a8aadfb33d0b64f45bd99 EmployeeTracking EmployeeTracking_Demo_Code" 
   
- On success, check root directory of project for generated excel file.

### 2) Export excel using executable jar

- Executable jar is available inside release directory
  - java -jar executable.jar SonarQubeURL SonarToken ProjectKey ProjectName
  - java -jar SonarQubeExporter.jar http://localhost:9000/ da5ad9e667faf116e42a8aadfb33d0b64f45bd99 EmployeeTracking EmployeeTracking_Demo_Code

