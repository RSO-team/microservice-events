FROM adoptopenjdk:11-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/basketball-events-api-1.0.0.jar /app

EXPOSE 8080

CMD ["java", "-jar", "basketball-events-api-1.0.0.jar"]
#ENTRYPOINT ["java", "-jar", "basketball-events-api-1.0.0.jar"]
#CMD java -jar basketball-events-api-1.0.0.jar