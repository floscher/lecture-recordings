#Lecture recordings

This is a Java application that displays the lectures that are recorded at KIT (Karlsruhe Institute of technology) by ATIS (Abteilung technische Infrastruktur). These are normally distributed by the application available at http://mml-streamdb01.ira.uka.de

To run this application, you need to download the WSDL-file for the webservice and name it `src/main/resources/lectures.wsdl`. Then you simply need to execute
```shell
./gradlew run
```

For opening it in Eclipse, run
```shell
./gradlew build cleanEclipse eclipse
```
and then import the project in Eclipse as "Existing project into workspace".
