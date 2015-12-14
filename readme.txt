mvn package docker:build
docker run --net="host" -p 8082:8081 -t my/pal