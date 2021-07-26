# springboot-sample-app

Interview Question [Tiny URL](http://projects.spring.io/spring-boot/) app.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There different ways to run the application. It could be using your favourite IDE or via the command line.

```shell
- Import the project in your IDE and run the java class from com.tinyurl.app.InterviewQuestionTinyurlApplication
```

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Testing the Application

- 1 Start the application
- 2 Open any API testing tool of your choice e.g. Postman/Isomnia
- 3 Select POST as HTTP method and enter URL as - http://localhost:5000/short?url=https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json
- 4 Hit Send
- 5 The output should be generated as http://localhost:5000/long?tiny=Yw==
- 6 Copy the above output from #5 and rerun the step 3-5 