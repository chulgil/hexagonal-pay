.PHONY: run
run:
	@echo "Running... docker-compose down"
	@docker-compose down
	@echo "Running... gradlew docker"
	@./gradlew docker
	@echo "Running... docker-compose up"
	@docker-compose up -d



