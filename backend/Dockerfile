FROM maven:3.8.5-openjdk-11

COPY . /spring

WORKDIR /spring

RUN mvn -N wrapper:wrapper

CMD [ "./mvnw", "spring-boot:run" ]