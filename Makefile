CONTAINER_ENGINE := $(shell if command -v podman &>/dev/null; then echo podman; else echo docker; fi)

.PHONY: prep clean dev format build run container-build container-run container-stop container-logs container-destroy help

prep:
	@ln -sf $(CURDIR)/.hooks/pre-commit .git/hooks/pre-commit
	@echo "Hook installed";

clean:
	@./mvnw --quiet clean;
	@echo "Cleaned build artifacts";

dev:
	@./mvnw clean quarkus:dev

format:
	@./mvnw spotless:apply

build:
	@./mvnw --batch-mode clean verify

run:
	@java -jar target/quoteweaver-*-runner.jar

container-build:
	@$(CONTAINER_ENGINE) compose build

container-run:
	@$(CONTAINER_ENGINE) compose up --detach

container-stop:
	@$(CONTAINER_ENGINE) compose down

container-logs:
	@$(CONTAINER_ENGINE) compose logs --follow

container-destroy:
	@$(CONTAINER_ENGINE) compose down --volumes --rmi local

help:
	@echo "Available targets:"
	@echo "  prep              - Install git hooks"
	@echo "  clean             - Clean build artifacts"
	@echo "  dev               - Start project in development mode"
	@echo "  format            - Auto format all the java files"
	@echo "  build             - Build project's JAR locally"
	@echo "  run               - Run project locally"
	@echo "  container-build   - Build project in containers and create container images"
	@echo "  container-run     - Run project containers"
	@echo "  container-stop    - Stop project containers"
	@echo "  container-logs    - Show project container logs"
	@echo "  container-destroy - Stop and delete project containers, networks, volumes, and images"
	@echo "  help              - Show this help message"
