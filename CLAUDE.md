# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Android payment app built with Kotlin. Multi-module project using Gradle Kotlin DSL with version catalog (`gradle/libs.versions.toml`).

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

This project demonstrates Android IPC (inter-process communication) techniques using a payment service scenario.

### Modules

- **`:app`** (Server App, `com.example.mypayment`) — Hosts `PaymentService` and `MainActivity`
- **`:client`** (Client App, `com.example.mypayment.client`) — Separate app that binds to the server's service via AIDL

### AIDL IPC

- AIDL interface: `app/src/main/aidl/com/example/mypayment/IPaymentService.aidl`
- Both modules must have an identical copy of the `.aidl` file with the same package
- `PaymentService` implements `IPaymentService.Stub()` (server side)
- `ClientActivity` uses `IPaymentService.Stub.asInterface()` to get a Proxy (client side)
- `buildFeatures { aidl = true }` is required in both module's `build.gradle.kts`

### Testing the cross-process flow

1. Install both APKs: `:app` (server) and `:client`
2. Launch the server app first, then the client app
3. Operations in the client app (pay/topUp) affect the server's PaymentService state

### Key files

- `app/src/main/java/com/example/mypayment/PaymentService.kt` — AIDL Stub implementation
- `app/src/main/java/com/example/mypayment/MainActivity.kt` — Server-side UI
- `client/src/main/java/com/example/mypayment/client/ClientActivity.kt` — Cross-process client
- `gradle/libs.versions.toml` — Dependency versions
