## Statistics Provider

# Building application:
```bash
mvn clean install
```

Running application:
```bash
mvn spring-boot:run
```

## Example service invocation

### Development profile (no security)

http://localhost:9000/statistics

### Production profile
 
Can be switched in [application.yml](./src/main/resources/application.yml):

http://user:[password]@localhost:9000/statistics

Here password is printed to console during start-up, like:
```
Using default security password: 7bd67ce6-217a-40d5-bb55-700d2cdbf456
```
