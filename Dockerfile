#maven
FROM openjdk
COPY target/*.jar /app.jar
CMD java -jar /app.jar