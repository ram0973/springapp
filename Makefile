SHELL := /bin/bash

.PHONY: help
help:
	make -h

.PHONY: up-dev
up-dev:
	docker-compose up

.PHONY: down-dev
down-dev:
	docker-compose down

.PHONY: up-dev-full
up-dev-full:
	docker-compose -f docker-compose-dev-full.yml up

.PHONY: down-dev-full
down-dev-full:
	docker-compose -f docker-compose-dev-full.yml down


.PHONY: up-dev-without-compose
up-dev-without-compose:
	docker build . -f ./docker/postgresql/Dockerfile -t psqldb:latest
	docker run -d --network host -e POSTGRES_DATABASE=springapp -e POSTGRES_USER=springapp -e POSTGRES_PASSWORD=springapp psqldb:latest #
	mvn spring-boot:run

.PHONY: up-prod-with-compose
up-prod-with-compose:
	mvn package
	docker-compose -f docker-compose.yml up

.PHONY: docker-remove-images
docker-remove-images:
	docker rm -f $(docker ps -a -q)
