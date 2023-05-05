FROM openjdk:17
ADD build/libs/QuizApi.jar QuizApi.jar
ENTRYPOINT ["java", "-jar", "/QuizApi.jar"]