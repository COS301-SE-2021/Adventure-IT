# Installing & Running

## 1. Building with Maven

In the backend directory, run the following Maven command to build the application's backend:

```bash
./mvnw install -DskipTests
```

## 2. Creating the Docker image

In the same directory, run the following command to build the docker image:

```bash
docker build -t adventure-it-backend -f dockerfile .
```

## 3. Run the docker image

In the same directory, run the following command to run the docker image:

```bash
docker run -p 9001:9001 -p 9002:9002 -p 9003:9003 -p 9004:9004 -p 9005:9005 -p 9006:9006 -p 9007:9007 -p 9009:9009 -p 9010:9010 -p 9012:9012 -p 9999:9999 -p 8761:8761 adventure-it-backend:latest
```
