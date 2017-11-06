Steps :

1) Pull the code and build it using mvn clean install.

2) Go to project directory structure and run jar file from command prompt as java -jar target/SpringBootRest-0.0.1-SNAPSHOT.jar. This will start spring boot application on port 1032 mentioned in application.properties file.

3) Open postman and hit URL http://localhost:1032/assignment with Get http method.

4) Open postman and hit URL http://localhost:1032/ingest with Post http method and provide some plain text in body. In Response we will see image url which you can copy and paste in browser to download images.

5) This project considers C://server folder as server directory where image will be stored at server side and end user can open same image using url shown in response.