# Settings

### Change default server port
You can change the default server port (80) by running the jar with the following argument:

```bash
java -jar estimatefx-server-1.0.jar --server.port=4040
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

### Produce fat jar client (approx 4 MB)

```bash
mvn install
```
The output jar will be at:  
`estimatefx-client/target/estimatefx-client-1.0-jar-with-dependencies.jar`

> **Note:** To run the fat jar, you need Oracle JRE or JDK 1.8.0 installed (e.g., version 1.8.0_261).

### Produce native Windows executable client (approx 205 MB, 76 MB zipped)

```bash
mvn -pl estimatefx-client jfx:native
```
The output folder will be:  
`estimatefx-client\target\jfx\native\estimatefx-client-1.0`

### Produce fat jar server (approx 20 MB)

```bash
mvn install
```
The output jar will be at:  
`estimatefx-server/target/estimatefx-server-1.0.jar`

# Versions

To update the version, use:

```bash
mvn versions:set -DnewVersion=5.0.0
mvn versions:commit
```

# Double-click to start on Windows 10

Edit Windows registry key:  
`Computer\HKEY_CLASSES_ROOT\jarfile\shell\open\command`

Set its value to:

```text
"C:\Program Files\Java\jdk1.8.0_261\bin\java.exe" -jar "%1" %*
```

# Swagger urls
http://localhost/

http://localhost/swagger-ui/index.html