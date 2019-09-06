docker build -t dacjsf/bd ./postgres
docker run -p 5433:5432 --name dacjsf-bd -d dacjsf/bd

mvn clean package
docker build -t app .
docker run -p 8082:8080 --name jsfApp --link bd:host-banco app
docker logs -f app

