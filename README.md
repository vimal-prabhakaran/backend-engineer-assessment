# Getting Started


## Setup

### Pre-requisities

To run the application you would require:

- [Java](https://www.azul.com/downloads/#zulu)
- [Temporal](https://docs.temporal.io/cli#install)
- [Docker](https://docs.docker.com/get-docker/)


### On macOS:

First, you need to install Java 21 or later. You can download it from [Azul](https://www.azul.com/downloads/#zulu) or
use [SDKMAN](https://sdkman.io/).

```sh
brew install --cask zulu21
```

You can install Temporal using Homebrew

```sh
brew install temporal
```

or visit [Temporal Installation](https://docs.temporal.io/cli#install) for more information.

You can install Docker using Homebrew

```sh
brew install docker
```

or visit [Docker Installation](https://docs.docker.com/get-docker/) for more information.

### Other platforms

Please check the official documentation for the installation of Java, Temporal, and Docker for your platform.

```

## Run

You are required to first start the temporal server using the following command

```sh
temporal server start-dev
```

and then run the application using the following command or using your IDE.

```sh
./gradlew bootRun
```

#### Run test cases

```sh
gradle test
```

### Other commands

#### Lint
To run lint checks, use the following command

```sh
./gradlew sonarlintMain
```

#### Code Formatting
To format the code, use the following command

```sh
./gradlew spotlessApply
```

## Guides

The following guides illustrate how to use some features concretely:

- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Temporal Quick Start](https://docs.temporal.io/docs/quick-start)
- [Temporal Java SDK Quick Guide](https://docs.temporal.io/dev-guide/java)
- [Stripe Quick Start](https://stripe.com/docs/quickstart)
- [Stripe Java SDK](https://stripe.com/docs/api/java)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

- postgres: [`postgres:latest`](https://hub.docker.com/_/postgres)

Please review the tags of the used images and set them to the same as you're running in production.
