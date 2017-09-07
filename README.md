# kotlin-scripting-jdk9

The fundamental problem that we are trying to solve with this repo is
to demonstrate and fix Kotlin Scripting with JDK 9.

Reproduction repository for the Kotlin DSL issues with JDK 9.
https://github.com/gradle/kotlin-dsl/issues/454

This repository is mostly cloned from the logic that can be found here:
https://github.com/bamboo/kotlin-sam-with-receiver-repro

## Demonstrating the Bug

### Enviroment

JDK 9 assigned to your `JAVA_HOME` environment variable.

## Running

You can run either in IntelliJ or via Gradle.
To run via Gradle simply run `./gradlew :compiler:run`
