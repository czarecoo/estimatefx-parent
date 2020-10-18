<h3>You can change default server port (8080) to any starting server:</h3>
java -jar estimatefx-server-1.0.jar --server.port=4040
<h3>You can change default client server address (http://localhost:8080) by editing following property in application.properties:</h3>
backend.url=https://estimatefx-server.herokuapp.com
<h3>You can set client to use proxy by adding following properties in application.properties:</h3>
proxy.enabled=true<br>
proxy.host=46.238.230.4<br>
proxy.port=8080<br>