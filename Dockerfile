FROM openjdk:8
WORKDIR /usr/src/api-investimentos/
COPY Api-Investimentos.jar .
EXPOSE 8080
CMD ["java", "-jar", "Api-Investimentos.jar"]
