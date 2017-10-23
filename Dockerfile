FROM openjdk:alpine

COPY /src/ /var/src/

WORKDIR /var/src/

RUN ["javac", "Main.java"]

ENTRYPOINT ["java", "Main"]