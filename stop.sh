
docker stop dacjsf-bd
docker rm dacjsf-bd
docker rmi -f dacjsf/bd

docker stop jsfApp
docker rm jsfApp
docker rmi -f app
