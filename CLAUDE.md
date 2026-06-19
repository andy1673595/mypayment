# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Android payment app (`com.example.mypayment`) built with Kotlin. Single-module project using Gradle Kotlin DSL with version catalog (`gradle/libs.versions.toml`).

- **minSdk**: 24, **targetSdk/compileSdk**: 36
- **JVM target**: 11
- **Kotlin**: 2.0.21, **AGP**: 8.13.2
- Uses AndroidX + Material Design (no Compose)

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew testDebugUnitTest --tests "com.example.mypayment.ExampleUnitTest"

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Clean build
./gradlew clean
```

## Architecture

Currently a fresh project scaffold with no Activities or application code yet. The manifest declares an `<application>` block but no components (activities, services, receivers).

- Source: `app/src/main/java/com/example/mypayment/`
- Resources: `app/src/main/res/`
- Unit tests: `app/src/test/java/com/example/mypayment/`
- Instrumented tests: `app/src/androidTest/java/com/example/mypayment/`
- Dependency versions: `gradle/libs.versions.toml`
