SERVICE_NAME=app

build:
	docker-compose build

build-no-cache:
	docker-compose build --no-cache

up:
	docker-compose up

up-d: 
	docker-compose up -d

up-build:	
	docker-compose up --build

down:
	docker-compose down

logs:
	docker-compose logs -f $(SERVICE_NAME)

shell:
	docker exec -it $(SERVICE_NAME) /bin/bash


.PHONY: build build-no-cache up up-d down logs shell