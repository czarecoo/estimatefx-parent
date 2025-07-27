# Settings

### Change default server port
You can change the default server port (80) by running the jar with the following argument:

```bash
java -jar estimatefx-server-4.0.0.jar --server.port=4040
```

### Change default client server address
Edit the following property in `application.properties`:

```properties
backend.url=https://estimatefx-server.herokuapp.com
```

### Set client to use proxy
Add these properties in `application.properties` to enable proxy usage:

```properties
proxy.enabled=true
proxy.host=46.238.230.4
proxy.port=8080
```

# Building

### Produce fat jar client

```bash
mvn package
```
The output jar will be at:  
`estimatefx-client/target/estimatefx-client-4.0.0-jar-with-dependencies.jar`

### Produce fat jar server

```bash
mvn package
```
The output jar will be at:  
`estimatefx-server/target/estimatefx-server-4.0.0.jar`

# Versions

To update the version, use:

```bash
mvn versions:set -DnewVersion=5.0.0
mvn versions:commit
```

# Swagger urls
http://localhost/

http://localhost/swagger-ui/index.html