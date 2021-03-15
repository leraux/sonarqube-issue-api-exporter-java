# SonarQube Issue API Exporter In Angular

Angular based web portal to export data of SonarQube Issue API for public projects. 

## How to use

Due to CORS, this project won't work on any browser. Follow below steps to disable CORS in a Google's Chrome Web Browser as well as export the report.

- Right click on Chrome Launcher and open properties
- Under `Shortcut` tab find `Target`
- Append following property inside `Target`
    - --disable-web-security --user-data-dir="C:/ChromeDevSession"
- Finally, it will look like below:
    - "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" --disable-web-security --user-data-dir="C:/ChromeDevSession"
- Press `Ok` and launch Chrome 
- Open `http://localhost:4200/` and feed SonarQube project details
- Click on export to generate the excel report


## Angular Version
This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 9.1.7.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.
