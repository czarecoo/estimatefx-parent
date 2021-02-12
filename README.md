<h2>Settings</h2>
<h3>You can change default server port (80) to any starting server:</h3>
java -jar estimatefx-server-1.0.jar --server.port=4040
<h3>You can change default client server address (http://localhost:8080) by editing following property in application.properties:</h3>
backend.url=https://estimatefx-server.herokuapp.com
<h3>You can set client to use proxy by adding following properties in application.properties:</h3>
proxy.enabled=true<br>
proxy.host=46.238.230.4<br>
proxy.port=8080<br>

<h2>Building</h2>
<h3>To produce fat jar client (4 MB)</h3>
mvn install<br>
estimatefx-client/target/estimatefx-client-1.0-jar-with-dependencies.jar
<h3>TO RUN THE FAT JAR YOU NEED TO HAVE ORACLE JRE OR JDK 1.8.0 INSTALLED! (1.8.0_261)</h3>
<h3>To produce exe client (windows) (205 MB) (76 MB zipped)</h3>
mvn -pl estimatefx-client jfx:native<br>
estimatefx-client\target\jfx\native\estimatefx-client-1.0
<h3>To produce fat jar server (20 MB)</h3>
mvn install<br>
estimatefx-server/target/estimatefx-server-1.0.jar