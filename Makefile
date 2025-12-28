CONTAINER_ENGINE := $(shell if command -v podman >/dev/null 2>&1; then echo podman; else echo docker; fi)
BUILD_COMMIT :=$(shell git rev-parse --short HEAD)

.PHONY: prep clean dev format check-updates build run build-native run-native container-build container-run container-stop container-logs container-destroy help

prep:
	@ln -sf $(CURDIR)/.hooks/pre-commit.sh .git/hooks/pre-commit
	@echo "Hook installed";

clean:
	@./mvnw --quiet --batch-mode clean;
	@echo "Cleaned build artifacts";

dev:
	@./mvnw clean quarkus:dev

format:
	@./mvnw spotless:apply

check-updates:
	@./mvnw versions:display-property-updates

build:
	@./mvnw clean verify

run:
	@java -jar target/quoteweaver-*-runner.jar

build-native:
	@./mvnw clean verify -Dnative

run-native:
	@./target/quoteweaver-*-runner

container-build:
	@BUILD_COMMIT=$(BUILD_COMMIT) $(CONTAINER_ENGINE) compose build

container-run:
	@BUILD_COMMIT=$(BUILD_COMMIT) $(CONTAINER_ENGINE) compose up --detach

container-stop:
	@BUILD_COMMIT=$(BUILD_COMMIT) $(CONTAINER_ENGINE) compose down

container-logs:
	@BUILD_COMMIT=$(BUILD_COMMIT) $(CONTAINER_ENGINE) compose logs --follow

container-destroy:
	@BUILD_COMMIT=$(BUILD_COMMIT) $(CONTAINER_ENGINE) compose down --volumes --rmi local

help:
	@echo "Available targets:"
	@echo "  prep              - Install git hooks"
	@echo "  clean             - Clean build artifacts"
	@echo "  dev               - Start project in development mode"
	@echo "  format            - Auto format all the java files"
	@echo "  check-updates     - Check for dependency updates in the pom.xml"
	@echo "  build             - Build project's JAR locally"
	@echo "  run               - Run project locally"
	@echo "  build-native      - Build project's native executable locally"
	@echo "  run-native        - Run project native executable locally"
	@echo "  container-build   - Build project in containers and create container images"
	@echo "  container-run     - Run project containers"
	@echo "  container-stop    - Stop project containers"
	@echo "  container-logs    - Show project container logs"
	@echo "  container-destroy - Stop and delete project containers, networks, volumes, and images"
	@echo "  help              - Show this help message"
