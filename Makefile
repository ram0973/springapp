SHELL := /bin/bash

help:
	make -h

run-dev:
	docker build . -f ./docker/postgresql/Dockerfile -t psqldb:latest
	docker run -d --network host -e POSTGRES_DATABASE=springapp -e POSTGRES_USER=springapp -e POSTGRES_PASSWORD=springapp psqldb:latest #
	mvn spring-boot:run

run-prod:
	mvn package
	docker-compose -f docker-compose.yml

docker-remove-images:
	docker rm -f $(docker ps -a -q)
