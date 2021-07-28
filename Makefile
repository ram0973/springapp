SHELL := /bin/bash

help:
	make -h

run-dev:
	docker build . -f ./docker/postgresql/Dockerfile -t psqldb:latest
	docker run --network host -e POSTGRES_PASSWORD=postgres psqldb:latest #
	mvn spring-boot:run

run-prod:
	mvn package
	docker-compose -f docker-compose.yml
